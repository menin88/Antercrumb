package com.example.antercrumb.utils;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class PlayerEntity extends GenericJson{
	
	@Key
	private String username;
	@Key
	private int imageID;
	@Key
	private String email;
	public PlayerEntity (){}
	
	
	//serve per ogni richiesta al database, serve per collegarti
	/*scoreAppData = mKinveyClient.appData("Player", PlayerEntity.class);

	dove scoreAppData �: private AsyncAppData<PlayerEntity> scoreAppData;
    */
	
	
}