package de.unikoblenz.west.lkastler.eventfinder.data;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

import de.unikoblenz.west.lkastler.eventfinder.events.Event;

/**
 * abstraction layer for database systems.
 * Created by lkastler on 9/12/13.
 */
abstract public class AbstractDatabase {

    /**
     * simple constructor.
     * @param context - the Context in which this Database exists.
     */
    public AbstractDatabase(Context context){}

    /**
     * returns all events in this Database.
     * @return all events in this Database.
     */
    abstract public List<Event> getEvents();

    /**
     * returns all events in this Database that meet the information in given Bundle.
     * @param bundle - Bundle to specify which events should be returned.
     * @return all events in this Database that meet the information in given Bundle.
     */
    abstract public List<Event> findEvents(Bundle bundle);
}
