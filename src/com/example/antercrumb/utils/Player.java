package com.example.antercrumb.utils;



//Name of collection is Score
public class Player {

	int reSourceID; 
	private String id;
	private String name;
	private int score;
	private int userID;

	public Player() {
	} // GenericJson classes must have a public empty constructor

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

	public String getId() {
		return id;
	}

	public int getScore() {
		return score;
	}

	public int getuID() {
		return userID;
	}

	public int getResourceId() {
		// TODO Auto-generated method stub
		return reSourceID;
	}

}
