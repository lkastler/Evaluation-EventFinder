package de.unikoblenz.west.lkastler.eventfinder;

import java.util.ArrayList;
import java.util.List;

import de.unikoblenz.west.lkastler.eventfinder.Configuration.VISUALIZATIONS;
import de.unikoblenz.west.lkastler.eventfinder.data.AbstractDatabase;
import de.unikoblenz.west.lkastler.eventfinder.data.SQLiteDatabaseHandler;
import de.unikoblenz.west.lkastler.eventfinder.data.SQLiteDatabaseImporter;
import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import de.unikoblenz.west.lkastler.eventfinder.fragments.FragmentCommunication;
import de.unikoblenz.west.lkastler.eventfinder.fragments.ListPresentation;
import de.unikoblenz.west.lkastler.eventfinder.fragments.MapPresentation;
import de.unikoblenz.west.lkastler.eventfinder.fragments.SearchFragment;
import de.unikoblenz.west.lkastler.eventfinder.fragments.SelectVisualizationDialog;
import de.unikoblenz.west.lkastler.eventfinder.fragments.TimesliderPresentation;
import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
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

    private Configuration config;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private AbstractDatabase handler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "BEGIN");
        
        setContentView(R.layout.activity_main);
        
        config = new Configuration();
        
        handler = new SQLiteDatabaseHandler(getApplicationContext());
        
        fragments.add(0, new SearchFragment());
        
        fragments.add(VISUALIZATIONS.LIST.getValue(), new ListPresentation());
        
        fragments.add(VISUALIZATIONS.MAP.getValue(), new MapPresentation());
        
        fragments.add(VISUALIZATIONS.TIMESLIDER.getValue(), new TimesliderPresentation());
                
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
			case R.id.select_vis:
				Log.d(TAG, "select Visualisation");
				
				DialogFragment df = new SelectVisualizationDialog();
				df.show(getFragmentManager(), FragmentCommunication.VISUALIZATION_DIALOG);
				
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
    public void onBackPressed() {
			
		//placeFragment(fragments.get(0), false);
    	if( getFragmentManager().getBackStackEntryCount() > 0) {
    		try {
    			super.onBackPressed();
    		}
    		catch(Exception e) {
    			Log.e(TAG, "cached", e);
    		}
    		
    	}
    	else {
    		// TODO: add some backstack solution for empty backstack
    	}
    }
    
    // TODO: comment switchPresentation
    public void switchPresentation() {
    	Log.d(TAG, "switch presentation");
    	placeFragment(fragments.get(config.getVisualizationValue()), true);
    }
    
    public void placeFragment(Fragment f, boolean backStack) {
    	FragmentTransaction trans =  getFragmentManager().beginTransaction();
    	
    	if(!f.isAdded()) {
    	
	    	trans.replace(R.id.fragment_target, f);
	    	
	    	if(backStack) {
	    		trans.addToBackStack(null);
	    	}
	    	
	    	trans.commit();
    	}
    	else {
    		trans.show(f);
    	}
    	
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
    
    public List<Event> findEvents(Bundle bundle) {
        return handler.findEvents(bundle);
    }
    
    public List<String> getCategories() {
    	return handler.getCategories();
    }
    
    public List<String> getLocations() {
    	return handler.getLocations();
    }
    
    public void setVisualization(VISUALIZATIONS v) {
    	config.setVisualization(v);
    }
    
    public VISUALIZATIONS getVisualization() {
    	return config.getVisualization();
    }
}
