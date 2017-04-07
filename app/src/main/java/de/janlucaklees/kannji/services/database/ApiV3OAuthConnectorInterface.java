package de.janlucaklees.kannji.services.database;

interface ApiV3OAuthConnectorInterface {
	
	public boolean hasAccessToken();
	
	public String getAccessTokenByUserCredentials( String username, String password );
	
	String getAccessTokenFromSharedPreferences();
	
	String hasRefreshToken();
	
	public String refreshAccessToken();
	
	public void revokeAccessToken();
	
}
