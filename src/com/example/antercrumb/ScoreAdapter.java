package com.example.antercrumb;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.antercrumb.utils.Player;

public class ScoreAdapter extends ArrayAdapter<Player>{

	
	private Context context;
	int layoutResourceId;
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public ScoreAdapter(Context context, int textViewResourceId, ArrayList<Player> players2) {
		super(context, textViewResourceId, players2);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.players = players2;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PlayerHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new PlayerHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.icon);
            holder.name = (TextView)row.findViewById(R.id.name);
            holder.score = (TextView)row.findViewById(R.id.scorepoint);
           
            row.setTag(holder);
        }
        else
        {
            holder = (PlayerHolder)row.getTag();
        }
       
        Player player = players.get(position);
        holder.name.setText(player.getName());
        holder.score.setText(player.getScore());
        holder.imgIcon.setImageResource(player.getResourceId());
       
        return row;
    }
   
    static class PlayerHolder
    {
        ImageView imgIcon;
        TextView name;
        TextView score;
    }
}
	


