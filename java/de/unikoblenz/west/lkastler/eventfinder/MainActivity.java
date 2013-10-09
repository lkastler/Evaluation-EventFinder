package de.unikoblenz.west.lkastler.eventfinder;

import java.util.ArrayList;
import java.util.List;

import de.unikoblenz.west.lkastler.eventfinder.data.AbstractDatabase;
import de.unikoblenz.west.lkastler.eventfinder.data.SQLiteDatabaseHandler;
import de.unikoblenz.west.lkastler.eventfinder.data.SQLiteDatabaseImporter;
import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import de.unikoblenz.west.lkastler.eventfinder.fragments.ListPresentation;
import de.unikoblenz.west.lkastler.eventfinder.fragments.SearchFragment;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * this is the main activity of the EventFinder project.
 */
public class MainActivity extends Activity {

    public static final String TAG = "MAIN";

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private AbstractDatabase handler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BEGIN");
        setContentView(R.layout.activity_main);
        
        handler = new SQLiteDatabaseHandler(getApplicationContext());
        
        fragments.add(0, new SearchFragment());
        fragments.add(1, new ListPresentation());
        
        placeFragment(fragments.get(0), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.load_data:
			importData("data.json");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
    public void onBackPressed() {
    	if( getFragmentManager().getBackStackEntryCount() > 0) {
    		super.onBackPressed();
    	}
    	else {
    		// TODO: add some backstack solution for empty backstack
    	}
    }
    
    // TODO: comment switchPresentation
    public void switchPresentation() {
    	Log.d(TAG, "switch presentation");
    	placeFragment(fragments.get(1), true);
    	
    }
    public void placeFragment(Fragment f, boolean backStack) {
    	FragmentTransaction trans =  getFragmentManager().beginTransaction();
    	
    	trans.replace(R.id.fragment_target, f);
    	
    	if(backStack) {
    		trans.addToBackStack(null);
    	}
    	
    	trans.commit();
    	
    	Log.d(TAG, "switched");
    }
 
    public void importData(String fileName) {
    	SQLiteDatabaseImporter importer = new SQLiteDatabaseImporter(getApplicationContext());
    	try {
			importer.importEvents(fileName);
			
			
		} catch (Exception e) {
			Log.e(TAG, "error during data import", e);
		}
    }
    
    /**
     * returns all events stored in the database.
     * @return all events stored in the database.
     */
    public List<Event> getEvents() {
    	return handler.getEvents();
    }
}
