package de.unikoblenz.west.lkastler.eventfinder.timeslider;

import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.FastForwardTimesliderEvent;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.LongPressTimesliderEvent;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.ScrollTimesliderEvent;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.TimeFrameChangeTimesliderEvent;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.TimesliderEvent;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.UpTimesliderEvent;
import android.app.Activity;
import android.util.Log;

/**
 * handles all input from the TimesliderView and adds changes to the DataModel.
 * 
 * @author lkastler
 */
public class TimesliderDataControl implements TimesliderListener {

	public static final String TAG = "DATA CONTROL";

	TimesliderDataModel model;

	ScrollTimesliderEvent buffer;
	Activity activity;

	/**
	 * constructor
	 * @param model - model for this DataControl 
	 */
	public TimesliderDataControl(Activity activity, TimesliderDataModel model) {
		this.activity = activity;
		this.model = model;
	}

	@Override
	public void notify(TimesliderView sender, TimesliderEvent event) {
		Log.i(TAG, event.getInfo());
		
		if (event instanceof UpTimesliderEvent) {
			if (buffer != null) {
				buffer = null;
			}
		} 
		else if (event instanceof ScrollTimesliderEvent) {
			ScrollTimesliderEvent scrollEvent = (ScrollTimesliderEvent)event;
			model.setPointInTime(model.getPointInTime() + (long)(scrollEvent.getDistance() * (model.getFrameSize() / scrollEvent.getSliderSize())));
		}
		else if (event instanceof FastForwardTimesliderEvent) {
			FastForwardTimesliderEvent ff = (FastForwardTimesliderEvent) event;

			model.setPointInTime(model.getPointInTime() 
					+ (ff.isForward() ? model.getFrameSize() : -model.getFrameSize()));
		}
		else if (event instanceof TimeFrameChangeTimesliderEvent) {
			TimeFrameChangeTimesliderEvent id = (TimeFrameChangeTimesliderEvent)event; 
			model.setCurrentTimeFrame(id.getTimeFrameId());
		}
		else if (event instanceof LongPressTimesliderEvent) {
//			TimesliderDialog d = new TimesliderDialog();
//			d.show(activity.getFragmentManager(), FragmentCommunication.TIMESLIDER_DIALOG);
		}
	}

}
