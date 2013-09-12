package de.unikoblenz.west.lkastler.eventfinder.data;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

import de.unikoblenz.west.lkastler.eventfinder.events.Event;

/**
 * defines the data model for temporal spatial events.
 *
 * Created by lkastler on 9/5/13.
 */
public class DataModel {

    protected AbstractDatabase handler;

    /**
     * constructor
     * @param context - context in which this AbstractDatabase exists.
     */
    public DataModel(Context context) {
        handler = new SQLiteDatabaseHandler(context);
    }

    /**
     * returns all events in this AbstractDatabase.
     * @return all events in this AbstractDatabase.
     */
    public List<Event> getEvents() {
        return handler.getEvents();
    }

    /**
     * returns all events in this AbstractDatabase that meet the specification given by bundle.
     * @param bundle - specifying Bundle containing information.
     * @return all events in this AbstractDatabase that meet the specification given by bundle.
     */
    public List<Event> findEvents(Bundle bundle) {
        return handler.findEvents(bundle);
    }
}
