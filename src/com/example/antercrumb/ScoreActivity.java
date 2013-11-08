package com.example.antercrumb;

import java.util.ArrayList;

import com.example.antercrumb.utils.Player;
import com.example.antercrumb.utils.ScoreEntity;
import com.example.antercrumb.utils.Utils;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.java.Query;
import com.kinvey.java.core.KinveyClientCallback;
import com.kinvey.java.query.AbstractQuery.SortOrder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ScoreActivity extends Activity {

	private ScoreEntity scoreEntity;
	private ScoreEntity[] generalScore;

	protected Client mKinveyClient;

	private int myScore;
	private String playerName = "";
	private String ID;
	private int uID;
	private AsyncAppData<ScoreEntity> scoreAppData;

	public final String TAG = ScoreActivity.this.getClass().getSimpleName();
	
	private ListView listView;
	private SimpleAdapter adapter;
	private Button btn ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_activity);
		//start some View set
		//listView =(ListView)findViewById(R.id.listview);
		
		btn = (Button)findViewById(R.id.btnBackToMainMenu);
		mKinveyClient = new Client.Builder(this.getApplicationContext())
				.build();
		// Receive score from gameActivity then check general score
		myScore = 10; // Here I set the score point to myScore
		playerName = "default";
		ID = ""; //lo lascio gestire al server??
		uID = 10;

		// IF VALUES ARE RIGHT, THEN SAVE THE SCORE
		scoreEntity = new ScoreEntity();
		
		scoreEntity.setScore(myScore);
		scoreEntity.setName(playerName);
		scoreEntity.setuserID(uID);

		scoreAppData = mKinveyClient.appData("Score", ScoreEntity.class);
		//Only if I came to score with values then I save the score, 
		//saveScore();
		//Otherwise, skip saveScore and just call showScore
		//getScores();
		//showScore();
		

	}

	public void onClick(View v){
		switch (v.getId()){
		
		case R.id.btnBackToMainMenu:
			//Toast.makeText(this, "Back button pressed", Toast.LENGTH_SHORT).show();
			
			Intent in = new Intent(ScoreActivity.this, GameMenuActivity.class);	
			in.putExtra(Utils.COME_FROM, 1);
		//	ScoreActivity.this.startActivity(in);
			ScoreActivity.this.finish();
			break;
		default: break;
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    //Handle the back button
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	Intent in = new Intent(ScoreActivity.this, GameMenuActivity.class);	
	    	in.putExtra(Utils.COME_FROM, 1);
		//	ScoreActivity.this.startActivity(in);
			ScoreActivity.this.finish();
			//OSSERVAZIONE IMPORTNTE!
			//Se non chiamo la finish dell'Activity, questa rimane nello Stack delle Activity
			//e in questo caso mi impedisce di uscire dal gioco 
			//dal momento che ad esempio, non ho implementto un modo per
			//chiudere l'attuale Activity
			//E' buona norma quindi che con il pulsante back si ritorni all'activity
			//precedente CHIUDENDO con finish() l' Activity.
			//
	    	return true; 
	    }
	    else {
	        return super.onKeyDown(keyCode, event);
	    }

	}
	

	public void saveScore() {
		if (scoreAppData != null) {
			scoreAppData.save(scoreEntity,
					new KinveyClientCallback<ScoreEntity>() {
						public void onFailure(Throwable e) {
							Log.e(TAG, "failed to save event data", e);
						}

						@Override
						public void onSuccess(ScoreEntity r) {
							Log.d(TAG, "saved data for entity " + r.getName());
							
						}
					});
		} else {
			// can't save the score for some reasons
		}
	}

	public void getScores() {

		Query q = mKinveyClient.query();
		q.lessThanEqualTo("score", 10);

		scoreAppData.get(q, new KinveyListCallback<ScoreEntity>() {
			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "Error fetching data: " + t.getMessage());
			}

			@Override
			public void onSuccess(ScoreEntity[] se) {
				Log.d(TAG, "Score retrieved " + se[0].get("score") + " scores "+ "belong to "+ se[0].get("name"));
				generalScore = se;
				
				//NEED TO CHECK THE ScoreAdapter
				//showScore();
			}
		});
	}
	private void showScore() {
		// TODO Auto-generated method stub
		if(generalScore != null){
			Log.d(TAG, "entered in showScore");
			String id;	
			String name;
			int score;
			int userID;
			//Player player[] = new Player[];
			ArrayList<Player> players = new ArrayList<Player>();
			Player p = new Player();
			for(ScoreEntity se: generalScore){
				id = se.getId();
				name = se.getName();
				score = se.getScore();
				userID = se.getuID();
				
				p.setName(name);
				p.setScore(score);
				p.setuserID(userID);
				players.add(p);
				//here load the view structure with data retrieved
				
			}
			
			ScoreAdapter adapter = new ScoreAdapter(getApplicationContext(), R.layout.score_activity_element, players);
			
			View header = (View)getLayoutInflater().inflate(R.layout.score_activity_header, null);
			listView.addHeaderView(header);
			listView.setAdapter(adapter);
		
		}
		Log.d(TAG, "exit from showScore");
		
		
	}
	public void onResume() {
		super.onResume();

	}

	public void onPause() {
		super.onPause();
	}
}
