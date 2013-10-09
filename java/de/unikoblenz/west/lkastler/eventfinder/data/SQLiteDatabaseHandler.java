package de.unikoblenz.west.lkastler.eventfinder.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import de.unikoblenz.west.lkastler.eventfinder.events.Event;

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

    protected SQLiteDatabaseOpener opener;

    public SQLiteDatabaseHandler(Context context) {
        super(context);

        opener = new SQLiteDatabaseOpener(context);
        Log.d(TAG, "created");
    }

    @Override
    public List<Event> getEvents() {
        Log.d(TAG, "get all events");

        LinkedList<Event> result = new LinkedList<Event>();

        SQLiteDatabase db = opener.getReadableDatabase();

        try {
            Cursor cur = db.rawQuery("select * from " + TABLE, null);

            while(cur.moveToNext()) {
                result.add(new Event(
                        cur.getInt(cur.getColumnIndex(ID)),
                        cur.getString(cur.getColumnIndex(TITLE)),
                        cur.getString(cur.getColumnIndex(ARTISTS)),
                        cur.getString(cur.getColumnIndex(VENUE)),
                        cur.getLong(cur.getColumnIndex(START)),
                        cur.getLong(cur.getColumnIndex(END)),
                        cur.getLong(cur.getColumnIndex(LAT)),
                        cur.getLong(cur.getColumnIndex(LNG))
                ));
            }
        }
        finally {
            db.close();
        }
        
        return result;
    }

    @Override
    public List<Event> findEvents(Bundle bundle) {
        Log.d(TAG, "get events that meet: " + bundle);

        // TODO: implement findEvents.
        throw new UnsupportedOperationException();
    }
}
