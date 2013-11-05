package de.unikoblenz.west.lkastler.eventfinder.timeslider.commands;

/**
 * created if the user triggers a fast forward event on the TimesliderView.
 * @author lkastler
 *
 */
public class FastForwardTimesliderEvent extends TimesliderEvent {

	private boolean forward = true;
	
	/**
	 * constructor
	 * @param forward - true if fast forward should be directed to the future, false if it should be direted to the past.
	 */
	public FastForwardTimesliderEvent(boolean forward) {
		this.forward = forward;
	}
	
	/**
	 * returns true if fast forward should be directed to the future, false if it should be direted to the past.
	 * @return true if fast forward should be directed to the future, false if it should be direted to the past.
	 */
	public boolean isForward() {
		return forward;
	}


	@Override
	public String getInfo() {
		return "FF(" + forward + ")";
	}

}
