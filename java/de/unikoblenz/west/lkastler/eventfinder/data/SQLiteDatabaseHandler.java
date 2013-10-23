package de.unikoblenz.west.lkastler.eventfinder.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import de.unikoblenz.west.lkastler.eventfinder.fragments.FragmentCommunication;

/**
 * SQLite Database for Events.
 *
 * Created by lkastler on 9/12/13.
 */
public class SQLiteDatabaseHandler extends AbstractDatabase {

    public static String TAG = "SQLite Database";

    static final String TABLE = "events";
    static final String ID = "id";
    static final String TITLE = "title";
    static final String START = "start";
    static final String END = "end";
    static final String LAT = "latitude";
    static final String LNG = "longitude";
    static final String ARTISTS = "artists";
    static final String VENUE = "venue";
    static final String LOCATION = "location";
    static final String CATEGORY = "category";

    protected SQLiteDatabaseOpener opener;

    public SQLiteDatabaseHandler(Context context) {
        super(context);

        opener = new SQLiteDatabaseOpener(context);
        Log.d(TAG, "created");
    }

    @Override
    public List<Event> getEvents() {
        Log.d(TAG, "get all events");

        return queryDB("select * from " + TABLE, null);
    }

    @Override
    public List<Event> findEvents(Bundle bundle) {
        Log.d(TAG, "get events that meet: " + bundle);

        LinkedList<QueryElement> qe = new LinkedList<QueryElement>();
        
        LinkedList<String> queries = new LinkedList<String>();
        LinkedList<String> items = new LinkedList<String>();

        // create query items
        qe.add(evalSearchPhrase(bundle.getCharSequence(FragmentCommunication.SEARCH_PHRASE).toString()));
        qe.add(evalLocation(bundle.getCharSequence(FragmentCommunication.LOCATION).toString()));
        
        // create query list and items list
        for(QueryElement q: qe) {
        	if(q != null) {
        		queries.add(q.query);
        		items.addAll(Arrays.asList(q.items));
        	}
        }
        
        // produce query & items for db query
        String q = "select * from " + TABLE + (queries.size() > 0 ? (" where " + TextUtils.join(" AND ", queries)) : "");
        return queryDB(q, items.toArray(new String[0]));
    }
    
    private QueryElement evalSearchPhrase(String searchPhrase) {
    	if(searchPhrase == null || searchPhrase.equals("") || searchPhrase.equals(" ")) {
    		return null; 
    	}
    	
    	String[] tokens = searchPhrase.toLowerCase(Locale.getDefault()).trim().split(" ");
    	
    	return new QueryElement(
    			TABLE + " match ?",
    			new String[]{"'*" + TextUtils.join("* *", tokens) + "*'"}
    		);
    }
    
    private QueryElement evalLocation(String location) {
    	if(location == null || location.equals("") || location.equals(" ")) {
    		return null;
    	}
    	
    	return new QueryElement(
    			LOCATION + " match ?",
    			new String[] {"'" + TextUtils.join(" OR ", location.toLowerCase(Locale.getDefault()).trim().split(" ")) + "'"}
    		);
    }
    
    private List<Event> queryDB(String query, String[] items) {
        Log.d(TAG, "q=" +query + ", i=" + Arrays.toString(items));
    	
    	LinkedList<Event> result = new LinkedList<Event>();

        SQLiteDatabase db = opener.getReadableDatabase();

        try {
            Cursor cur = db.rawQuery(query, items);

            while(cur.moveToNext()) {
                result.add(new Event(
                    cur.getInt(cur.getColumnIndex(ID)),
                    cur.getString(cur.getColumnIndex(TITLE)),
                    cur.getString(cur.getColumnIndex(ARTISTS)),
                    cur.getString(cur.getColumnIndex(VENUE)),
                    cur.getLong(cur.getColumnIndex(START)),
                    cur.getLong(cur.getColumnIndex(END)),
                    cur.getLong(cur.getColumnIndex(LAT)),
                    cur.getLong(cur.getColumnIndex(LNG)),
                    cur.getString(cur.getColumnIndex(CATEGORY)),
                    cur.getString(cur.getColumnIndex(LOCATION))
                ));
            }
        }
        finally {
            db.close();
        }
        
        return result;
    }

	@Override
	public List<String> getCategories() {
		LinkedList<String> categories = new LinkedList<String>();
		categories.add("");		
		
		SQLiteDatabase db = opener.getReadableDatabase();
		
		try {
			Cursor cur = db.rawQuery("select distinct " + CATEGORY + " from " + TABLE, new String[0]);
			
			while(cur.moveToNext()) {
				categories.add(cur.getString(cur.getColumnIndex(CATEGORY)));
			}
		}
		finally {
			db.close();
		}
		
		return categories;
	}

	@Override
	public List<String> getLocations() {
		LinkedList<String> locations = new LinkedList<String>();
		
		SQLiteDatabase db = opener.getReadableDatabase();
		
		try {
			Cursor cur = db.rawQuery("select distinct " + LOCATION + " from " + TABLE, new String[0]);
			
			Log.d(TAG, "lines: " +  cur.getCount());
			
			while(cur.moveToNext()) {
				Log.d(TAG, "lines: " +  cur.getString(cur.getColumnIndex(LOCATION)));
				
				locations.add(cur.getString(cur.getColumnIndex(LOCATION)));
			}
		}
		finally {
			db.close();
		}
		
		Log.d(TAG, "locations: " +  locations.toString());
		return locations;
	}
	
	/**
	 * helper class to ease the query creation by defining sub queries
	 * @author lkastler
	 */
	private class QueryElement {
		private String query;
		private String[] items;
		
		/**
		 * constructor
		 * @param query - sub query
		 * @param items - items related to the sub query
		 */
		QueryElement(String query, String[] items) {
			this.query = query;
			this.items = items;
		}
	}
}
