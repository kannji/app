package de.janlucaklees.kannji.views;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import de.janlucaklees.kannji.R;

/**
 * This activity features an empty content and the default-drawer.
 * The default-drawer is the one drawer that may be accessible in every activity (at least in every that makes sense).
 * It serves as a base-activity to build other activities, that require the default-drawer, on.
 * If you want to create and activity that has the default-drawer, just make your activity extend this activity.
 * <p>
 * How it works:
 * This activity will set it's content view to be the one containing the drawer-layout.
 * It then overrides the setContentView method so that inheriting activities calling this method will end up here.
 * When an activity calls setContentView, the drawer-layout will not be replaced.
 * Instead the given id will be inflated and inserted in the drawer-layout at the appropriate point.
 */
public abstract class DrawerActivity extends AppCompatActivity {
	
	private DrawerLayout _rootLayout;
	// the content-placeholder that'll be filled with the content by the inheriting activity
	private FrameLayout _contentLayout;
	// the layout to be used as a drawer
	private ListView _drawerLayout;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		
		// Set the content of our main activity.
		// This is our main layout containing the drawer and the placeholder for the content
		// We need to call super method to not user our overridden one.
		super.setContentView( R.layout.activity_drawer );
		
		// save layouts to fields
		_rootLayout = (DrawerLayout) findViewById( R.id.main_layout );
		_contentLayout = (FrameLayout) findViewById( R.id.main_layout_content );
		_drawerLayout = (ListView) findViewById( R.id.main_layout_drawer );
		
		initialiseDrawer();
	}
	
	/**
	 * This initialises the drawer, setting the content to be displayed and the callbacks to be made.
	 */
	private void initialiseDrawer() {
		// setup the adapter to display a list of options in the drawer
		_drawerLayout.setAdapter(
				new ArrayAdapter<String>( this, R.layout.fragment_drawer_item, new String[]{"Settings"} )
		);
	}
	
	/**
	 * This method is overridden so that any calls from inheriting activities land here.
	 * The requested layoutId by the inheriting activity is then inflated and placed in the content-placeholder for the content in the drawer-layout.
	 *
	 * @param layoutId the layout to be displayed in the content-placeholder of the drawer-layout
	 */
	@Override
	public void setContentView( int layoutId ) {
		getLayoutInflater().inflate( layoutId, _contentLayout );
	}
}
