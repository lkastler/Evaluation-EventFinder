package de.unikoblenz.west.lkastler.eventfinder.events;

import android.database.DataSetObserver;
import de.unikoblenz.west.lkastler.eventfinder.fragments.MapPresentation;

/**
 * observes an event data set.
 * @author lkastler
 */
public class EventDataObserver extends DataSetObserver {

	protected MapPresentation mapPresentation;
	
	/**
	 * constructor
	 * 
	 * @param mapPresentation - presentation to update when data changes
	 */
	public EventDataObserver(MapPresentation mapPresentation) {
		super();
		this.mapPresentation = mapPresentation;
	}

	/* (non-Javadoc)
	 * @see android.database.DataSetObserver#onChanged()
	 */
	@Override
	public void onChanged() {
		mapPresentation.dataChanged();
	}

}
