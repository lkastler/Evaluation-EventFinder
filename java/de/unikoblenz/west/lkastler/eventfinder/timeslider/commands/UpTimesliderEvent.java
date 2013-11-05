package de.unikoblenz.west.lkastler.eventfinder.timeslider.commands;


/**
 * triggered if user triggers an up event on the TimesliderView
 * @author lkastler
 *
 */
public class UpTimesliderEvent extends TimesliderEvent {

	long eventTime;
	
	/**
	 * constructor
	 * 
	 * @param eventTime - time when this event was triggered
	 */
	public UpTimesliderEvent(long eventTime) {
		this.eventTime = eventTime;
	}

	@Override
	public String getInfo() {
		return "UP " + eventTime;
	}

}
