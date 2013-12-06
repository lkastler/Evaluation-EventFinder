package de.unikoblenz.west.lkastler.eventfinder.events;

/**
 * abstract helper class for events
 * @author lkastler
 */
abstract public class EventHelper {

	public static EventList findNearEvents(Iterable<Event> events, double lat, double lng) {
		EventList result = new EventList();
		
		// TODO: what is near?
		for(Event ev : events) {
			if(Math.abs(ev.getLatitude() - lat) < 0.05 && Math.abs(ev.getLongitude() - lng) < 0.05) {
				result.add(ev);
			}
		}
		
		return result;
	}
}
