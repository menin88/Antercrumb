package com.example.antercrumb.utils;

import com.google.api.client.json.GenericJson;
import com.kinvey.java.model.KinveyMetaData;

//Name of collection is Score
public class ScoreEntity extends GenericJson {

	 private String id;	
	 private String name;
	 private int score;
	 private int userID;
	 
	 private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
	 //metad data is used for: getCreatorId and getLastModifiedTime
	
	 private KinveyMetaData.AccessControlList acl; //Kinvey access control, OPTIONAL
	 
	 public ScoreEntity(){} //GenericJson classes must have a public empty constructor

	public void setScore(int myScore) {
		// TODO Auto-generated method stub
		this.score = myScore;
	}

	public void setName(String playerName) {
		// TODO Auto-generated method stub
		this.name = playerName;
	}

	public void setuserID(int uID) {
		// TODO Auto-generated method stub
		this.userID = uID;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public String getId(){
		return id;
	}
	public int getScore(){
		return score;
	}
	public int getuID(){
		return userID;
	}

}
