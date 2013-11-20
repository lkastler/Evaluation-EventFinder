package de.unikoblenz.west.lkastler.eventfinder.events;

import java.util.Iterator;
import java.util.List;

import android.util.Log;

import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModel;

public class TimesliderEventModel extends TimesliderDataModel {
	
	protected EventList all = new EventList();
	protected EventList selected = new EventList();
	protected EventList unselected = new EventList();
	protected EventList updatedSelected = new EventList();
	protected EventList updatedUnselected = new EventList();
	
	public void setAllEvents(List<Event> events) {
		updateSelection();
	}

	public Iterator<Event> getSelected() {
		return selected.iterator();
	}
	
	public Iterator<Event> getUnselected() {
		return unselected.iterator();
	}
	
	public Iterator<Event> getUpdatedSelected() {
		return updatedSelected.iterator();
	}
	
	public Iterator<Event> getUpdatedUnselected() {
		return updatedUnselected.iterator();
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
		updatedSelected.clear();
		updatedUnselected.clear();
		
		Log.d(TAG, "pit: " + getPointInTime());
		
		for(Event ev : all) {
			if(ev.getEnd() < getPointInTime() 
					|| ev.getStart() > getPointInTime() + getFrameSize()) {
				if(!unselected.contains(ev)) {
					Log.d(TAG, "out: " + ev.getTitle());					
					
					moveSelectedToUnselected(ev);
				}
			}
			else {
				if(!selected.contains(ev)) {
					Log.d(TAG, "in : " + ev.getTitle());
				
					moveUnselectedToSelected(ev);
				}
			}
		}
		
		Log.d(TAG, "sizes: " + all.size() + ", " + updatedSelected.size() + ", " + updatedUnselected.size());
		
		notifyListeners();
	}
	
	private void moveSelectedToUnselected(Event event) {
		unselected.add(event);
		selected.remove(event);
		updatedUnselected.add(event);
	}
	
	private void moveUnselectedToSelected(Event event) {
		selected.add(event);
		unselected.remove(event);
		updatedSelected.add(event);
	}

	/* (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModel#onPointInTimeChange()
	 */
	@Override
	protected void onPointInTimeChange() {
		updateSelection();
	}

}
