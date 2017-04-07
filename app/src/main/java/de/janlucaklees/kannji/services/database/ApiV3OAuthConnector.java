package de.janlucaklees.kannji.services.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import de.janlucaklees.kannji.values.GeneralValues;
import de.janlucaklees.kannji.values.Secrets;

public class ApiV3OAuthConnector implements ApiV3OAuthConnectorInterface {
	
	public static final String SHARED_PREFERENCES_AUTHENTICATION_KEY = "kannji.authentication";
	
	public static final String AUTHENTICATION_ACCESS_TOKEN_KEY = "access_token";
	public static final String AUTHENTICATION_REFRESH_TOKEN_KEY = "refresh_token";
	public static final String AUTHENTICATION_EXPIRES_IN_KEY = "expires_in";
	
	private final Context _context;
	
	private String _accessToken;
	private String _refreshToken;
	private DateTime _expirationDate;
	
	public ApiV3OAuthConnector( Context context ) {
		_context = context;
		
		loadAuthentication();
	}
	
	@Override
	public boolean hasAccessToken() {
		if ( _expirationDate.isBeforeNow() ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String getAccessTokenByUserCredentials( String username, String password ) {
		try {
			URL address = new URL( GeneralValues.API_URL + "/v3/oauth2/token/?grant_type=password&username=" + username + "&password=" + URLEncoder.encode( password, "UTF-8" ) );
			HttpURLConnection hc = (HttpURLConnection) address.openConnection();
			
			hc.setDoOutput( true );
			hc.setDoInput( true );
			hc.setUseCaches( false );
			
			// Sending side
			String authorization = Secrets.CLIENT_ID + ":" + Secrets.CLIENT_SECRET;
			byte[] data = authorization.getBytes( "UTF-8" );
			String base64 = Base64.encodeToString( data, Base64.NO_WRAP );
			hc.setRequestProperty( "Authorization", "Basic " + base64 );
			
			// retrieve data
			int x = hc.getResponseCode();
			
			// get input stream
			InputStream inputStream = new BufferedInputStream( hc.getInputStream() );
			
			// read stream
			BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
			StringBuilder result = new StringBuilder();
			String line;
			while ( ( line = reader.readLine() ) != null ) {
				result.append( line ).append( '\n' );
			}
			
			// disconnect
			hc.disconnect();
			
			JSONObject jsonResponse = new JSONObject( result.toString() );
			
			storeAuthentication( jsonResponse );
			
		} catch ( UnsupportedEncodingException e ) {
			e.printStackTrace();
		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		} catch ( JSONException e ) {
			e.printStackTrace();
		}
		
		return _accessToken;
	}
	
	private void storeAuthentication( JSONObject jsonResponse ) throws JSONException {
		_accessToken = jsonResponse.getString( AUTHENTICATION_ACCESS_TOKEN_KEY );
		_refreshToken = jsonResponse.getString( AUTHENTICATION_REFRESH_TOKEN_KEY );
		int expiresIn = jsonResponse.getInt( AUTHENTICATION_EXPIRES_IN_KEY );
		_expirationDate = DateTime.now().plusSeconds( expiresIn );
		
		SharedPreferences sharedPref = _context.getSharedPreferences( SHARED_PREFERENCES_AUTHENTICATION_KEY, Context.MODE_PRIVATE );
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.putString( AUTHENTICATION_ACCESS_TOKEN_KEY, jsonResponse.getString( AUTHENTICATION_ACCESS_TOKEN_KEY ) );
		editor.putString( AUTHENTICATION_REFRESH_TOKEN_KEY, jsonResponse.getString( AUTHENTICATION_REFRESH_TOKEN_KEY ) );
		editor.putInt( AUTHENTICATION_ACCESS_TOKEN_KEY, jsonResponse.getInt( AUTHENTICATION_EXPIRES_IN_KEY ) );
		
		editor.apply();
	}
	
	private void loadAuthentication() {
		SharedPreferences sharedPref = _context.getSharedPreferences( SHARED_PREFERENCES_AUTHENTICATION_KEY, Context.MODE_PRIVATE );
		
		_accessToken = sharedPref.getString( AUTHENTICATION_ACCESS_TOKEN_KEY, null );
		_refreshToken = sharedPref.getString( AUTHENTICATION_REFRESH_TOKEN_KEY, null );
		int expiresIn = sharedPref.getInt( AUTHENTICATION_EXPIRES_IN_KEY, 0 );
		_expirationDate = DateTime.now().plusSeconds( expiresIn );
	}
	
	@Override
	public String getAccessTokenFromSharedPreferences() {
		return null;
	}
	
	@Override
	public String hasRefreshToken() {
		return null;
	}
	
	@Override
	public String refreshAccessToken() {
		return null;
	}
	
	@Override
	public void revokeAccessToken() {
		
	}
}
