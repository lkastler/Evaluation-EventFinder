package de.unikoblenz.west.lkastler.eventfinder.timeslider.commands;

/**
 * Triggered whenever the user changes the time frame with the timeslider.
 * @author lkastler
 */
public class TimeFrameChangeTimesliderEvent extends TimesliderEvent {

	int timeFrameId;
	
	public TimeFrameChangeTimesliderEvent(int zoom) {
		this.timeFrameId = zoom;
	}
	
	/**
	 * id of the selected TimeFrame
	 * @return
	 */
	public int getTimeFrameId() {
		return timeFrameId;
	}
	
	@Override
	public String getInfo() {
		return "Time Frame changed to: " + timeFrameId;
	}

}
