package de.janlucaklees.kannji.services.database;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GetRequest {
	
	private final String _urlBase;
	
	private Map<String, String> _queryParameter;
	
	public GetRequest( String url ) throws IOException {
		// set URL
		_urlBase = url;
		_queryParameter = new HashMap<String, String>();
	}
	
	/**
	 * Takes the base-url, adds the set parameters to ir and gets the server-response as a JSONObject
	 *
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public JSONObject getJSON() throws IOException, JSONException {
		// init connection
		URL url = prepareUrl();
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		// retrieve data
		int x = connection.getResponseCode();
		
		// get input stream
		InputStream inputStream = new BufferedInputStream( connection.getInputStream() );
		
		// read stream
		BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
		StringBuilder result = new StringBuilder();
		String line;
		while ( ( line = reader.readLine() ) != null ) {
			result.append( line ).append( '\n' );
		}
		
		// disconnect
		connection.disconnect();
		
		return new JSONObject( result.toString() );
	}
	
	/**
	 * Prepares the url for this request.
	 * We take the _urlBase which should contain our URL. To this we append all set query-parameters like so: ?param1=val1&param2=val2...
	 * Then we create an URL object with this full url
	 *
	 * @return An URL object created with all parameters added to the url-base
	 * @throws MalformedURLException
	 */
	private URL prepareUrl() throws MalformedURLException {
		StringBuilder urlParameter = new StringBuilder( _urlBase );
		urlParameter.append( "?" );
		
		for ( Map.Entry<String, String> entry : _queryParameter.entrySet() ) {
			urlParameter.append( entry.getKey() );
			urlParameter.append( "=" );
			urlParameter.append( entry.getValue() );
			urlParameter.append( "&" );
		}
		
		// remove last '&'
		urlParameter.setLength( urlParameter.length() - 1 );
		
		return new URL( urlParameter.toString() );
	}
	
	/**
	 * Sets the paging for this request.
	 *
	 * @param pageNumber the page-number to be requested
	 * @param pageSize   the page-size
	 */
	public void setPaging( int pageNumber, int pageSize ) {
		_queryParameter.put( "paging", pageNumber + ":" + pageSize );
	}
	
	/**
	 * Sets filters for this request.
	 *
	 * @param filterName the name of the filter
	 * @param value      the value to filter by
	 */
	public void setFilter( String filterName, String value ) {
		_queryParameter.put( "filter[" + filterName + "]", value );
	}
}
