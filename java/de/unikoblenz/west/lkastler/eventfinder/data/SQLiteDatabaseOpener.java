package de.unikoblenz.west.lkastler.eventfinder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLite database opener.
 *
 * Created by lkastler on 9/5/13.
 */
public class SQLiteDatabaseOpener extends SQLiteOpenHelper {

    public static final String TAG = "SQLiteDatabaseOpener";

    public static final String DATABASE_NAME = "eventfinder.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteDatabaseOpener(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create virtual table " + SQLiteDatabaseHandler.TABLE + " using fts4 (" +
                SQLiteDatabaseHandler.ID + " integer," +
                SQLiteDatabaseHandler.TITLE + " string," +
                SQLiteDatabaseHandler.START + " integer," +
                SQLiteDatabaseHandler.END + " integer," +
                SQLiteDatabaseHandler.LAT + " integer," +
                SQLiteDatabaseHandler.LNG + " integer," +
                SQLiteDatabaseHandler.ARTISTS + " string," +
                SQLiteDatabaseHandler.VENUE + " string," +
                "primary key(" + SQLiteDatabaseHandler.ID + ")" +
                ")";
        Log.d(TAG, query);

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table " + SQLiteDatabaseHandler.TABLE + " if exists";

        Log.d(TAG, query);

        db.execSQL(query);
        onCreate(db);
    }
}
