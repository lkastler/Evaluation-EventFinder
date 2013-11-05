package de.unikoblenz.west.lkastler.eventfinder.events;

import java.util.Iterator;
import java.util.List;

import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModel;

public class TimesliderEventModel extends TimesliderDataModel {
	
	protected EventList all = new EventList();
	protected EventList selected = new EventList();
	protected EventList unselected = new EventList();
	
	public void setAllEvents(List<Event> events) {
		all = new EventList(events);
		
		updateSelection();
	}

	public Iterator<Event> getSelected() {
		return selected.iterator();
	}
	
	public Iterator<Event> getUnselected() {
		return unselected.iterator();
	}
	
	public EventList findNearestEvents(double lat, double lng) {
		EventList result = new EventList();
		
		// TODO: what is near?
		for(Event ev : all) {
			if(Math.abs(ev.getLatitude() - lat) < 0.05 && Math.abs(ev.getLongitude() - lng) < 0.05) {
				result.add(ev);
			}
		}
		
		return result;
	}
	
	private void updateSelection() {
		selected.clear();
		unselected.clear();
		
		for(Event ev : all) {
			if(ev.getEnd() < getPointInTime() 
					|| ev.getStart() > getPointInTime() + getFrameSize()) {
				unselected.add(ev);
			}
			else {
				selected.add(ev);
			}
		}
		
		notifyAll();
	}

}
