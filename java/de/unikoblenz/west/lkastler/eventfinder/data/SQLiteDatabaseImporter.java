package de.unikoblenz.west.lkastler.eventfinder.data;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import de.unikoblenz.west.lkastler.eventfinder.events.EventList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * importer for events, stored in json documents.
 * @author lkastler
 */
public class SQLiteDatabaseImporter {

	public static final String TAG = "SQLITE DATABASE IMPORT";
	
	private Context context;
	private SQLiteDatabaseOpener opener;
	
	/**
	 * constructor
	 * @param context - Context for the database
	 */
	public SQLiteDatabaseImporter(Context context) {
		this.context = context;
		opener = new SQLiteDatabaseOpener(context);
		
		Log.d(TAG, "created");
	}
	
	/**
	 * imports json encoded events to the database.
	 * @param file - json encoded file of events.
	 * @throws JsonSyntaxException
	 * @throws JsonIOException
	 * @throws IOException
	 */
	public void importEvents(String file) throws JsonSyntaxException, JsonIOException, IOException {
		Gson gson = new Gson();
		
		Log.d(TAG, "importing " + file);
		
		InputStreamReader read = new InputStreamReader(context.getAssets().open(file));

		EventList list = gson.fromJson(read, EventList.class);
		
		addEvents(list);
		
		read.close();
		
		Log.d(TAG, "done");
	}
	
	/**
	 * adds a list of Events to the SQLiteDatabase
	 * @param events
	 */
	private void addEvents(List<Event> events) {
		SQLiteDatabase db = opener.getWritableDatabase();
		db.beginTransaction();
		try {
			for(Event ev : events) {
				ContentValues vals = new ContentValues();
				
				vals.put(SQLiteDatabaseHandler.TITLE, ev.getTitle());
				vals.put(SQLiteDatabaseHandler.ARTISTS, ev.getArtists());
				vals.put(SQLiteDatabaseHandler.VENUE, ev.getVenue());
				vals.put(SQLiteDatabaseHandler.START, ev.getStart());
				vals.put(SQLiteDatabaseHandler.END, ev.getEnd());
				vals.put(SQLiteDatabaseHandler.LAT, ev.getLatitude());
				vals.put(SQLiteDatabaseHandler.LNG, ev.getLongitude());
				
				Log.d(TAG, "adding: " + vals.toString());
				
				db.insert(SQLiteDatabaseHandler.TABLE, null, vals);
			}
			
			db.setTransactionSuccessful();
		}
		finally {
			db.endTransaction();
		}
	}
}
