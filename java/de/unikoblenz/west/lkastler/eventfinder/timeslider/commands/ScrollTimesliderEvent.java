package de.unikoblenz.west.lkastler.eventfinder.timeslider.commands;

import android.util.Log;

public class ScrollTimesliderEvent extends TimesliderEvent {

	public static final String TAG = "SCROLL EVENT";
	
	final private float distance;
	final private float sliderSize;
	
	/**
	 * constructor
	 * @param distance - distance of the scroll event.
	 * @param sliderSize - full slider size.
	 */
	public ScrollTimesliderEvent(float distance, float sliderSize) {
		this.distance = distance;
		this.sliderSize = sliderSize;
		
		Log.d(TAG, "created");
	}
	
	@Override
	public String getInfo() {
		return "scroll " + " " + distance + " " + sliderSize + " " + (distance / sliderSize);
	}

	/**
	 * returns the distance on the slider.
	 * @return the distance on the slider.
	 */
	public float getDistance() {return distance;}
	
	/**
	 * returns full size of the slider in pixels.
	 * @return full size of the slider in pixels.
	 */
	public float getSliderSize() {return sliderSize;}
}
