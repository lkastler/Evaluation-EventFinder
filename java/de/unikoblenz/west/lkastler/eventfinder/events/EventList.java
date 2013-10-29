package de.unikoblenz.west.lkastler.eventfinder.events;

import java.util.LinkedList;
import java.util.List;

/**
 * container for {@link Event}s
 * 
 * @author lkastler
 */
public class EventList extends LinkedList<Event> {

	private static final long serialVersionUID = 1L;
	
	public EventList(EventList list) {
		super(list);
	}

	public EventList(List<Event> list) {
		super(list);
	}
}
