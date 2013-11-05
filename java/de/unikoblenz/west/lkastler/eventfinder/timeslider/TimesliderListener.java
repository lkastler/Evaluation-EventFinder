package de.unikoblenz.west.lkastler.eventfinder.timeslider;

import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.TimesliderEvent;

/**
 * implemented by classes that listen on the TimesliderView.
 * @author lkastler
 *
 */
public interface TimesliderListener {

	/**
	 * triggered if the user does input identified as event on the TimesliderView identified as sender
	 * @param sender - triggering TimesliderView.
	 * @param event - triggered TimesliderEvent.
	 */
	public void notify(TimesliderView sender, TimesliderEvent event);
}
