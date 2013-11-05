package de.unikoblenz.west.lkastler.eventfinder.timeslider.commands;

/**
 * basic event sent by the TimesliderView to connected TimesliderListeners
 * @author lkastler
 *
 */
abstract public class TimesliderEvent {

	/**
	 * returns an information string about this event.
	 * @return an information string about this event.
	 */
	abstract public String getInfo();
}
