package de.unikoblenz.west.lkastler.eventfinder.timeslider;

/**
 * handles changes of DataModel objects
 * @author lkastler
 *
 */
public interface TimesliderDataModelListener {

	/**
	 * call function for handling DataModel changes of the specified sender
	 * @param sender - DataModel that called the method.
	 */
	public void notify(TimesliderDataModel sender);
}
