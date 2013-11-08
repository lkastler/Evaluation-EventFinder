package de.unikoblenz.west.lkastler.eventfinder.timeslider;

import java.util.Calendar;
import java.util.LinkedList;

import android.util.Log;

/**
 * presenting the data model for this system
 * @author lkastler
 *
 */
public class TimesliderDataModel {

	public static final String TAG = "Data Model";
	
	public static final long HOUR = 3600000;

	/**
	 * helper class to define time frames
	 * @author lkastler
	 *
	 */
	public class TimeFrame {
		private final String name;
		private final long time;
		
		/**
		 * constructor 
		 * @param name - name of time frame
		 * @param time - size in milliseconds
		 */
		public TimeFrame(String name, long time) {
			super();
			this.name = name;
			this.time = time;
		}

		/**
		 * returns the name of this time frame.
		 * @return the name of this time frame.e 
		 */
		public String getName() {return name;}
		
		/**
		 * returns the size of this time frame in milliseconds.
		 * @return the size of this time frame in milliseconds.
		 */
		public long getTime() {return time;}
	}
	
	/**
	 * predefined time frames
	 */
	public final TimeFrame[] TIMEFRAMES = {
			new TimeFrame("6h", HOUR*6),
			new TimeFrame("1d", HOUR*24),
			new TimeFrame("2d", HOUR*24*2),
			new TimeFrame("1w", HOUR*24*7),
			new TimeFrame("2w", HOUR*24*7 * 2)
	};
	
	protected LinkedList<TimesliderDataModelListener> listeners = new LinkedList<TimesliderDataModelListener>();
	
	private long pointInTime = 0;
	
	private int currentId = 0;
	
	public TimesliderDataModel() {}
	
	public TimesliderDataModel(Calendar date) {
		pointInTime = date.getTimeInMillis();
	}
	
	/**
	 * add specified DataModelListener to listen on this DataModel.
	 * @param l - DataModelListener to listen on this DataModel.
	 */
	public void addListener(TimesliderDataModelListener l) {
		listeners.add(l);
		
		l.notify(this);
	}
	
	/**
	 * calls all listeners for changes.
	 */
	protected void notifyListeners() {
		for(TimesliderDataModelListener l : listeners) {
			l.notify(this);
		}
	}
	
	/**
	 * set the point in time to the given value
	 * @param newTime new point in time
	 */
	void setPointInTime(long newTime) {
		Log.i(TAG, "set pit: " + newTime);
		pointInTime = newTime;
		
		onPointInTimeChange();
		
		notifyListeners();
	}
	
	protected void onPointInTimeChange() {}
	
	/**
	 * returns the point in time.
	 * @return the point in time.
	 */
	public long getPointInTime() {return pointInTime;}	
	
	/**
	 * sets the current time frame to the given id.
	 * @param id - id of new time frame.
	 */
	void setCurrentTimeFrame(int id) {
		Log.i(TAG, "set tf: " + id);
		if(id >= 0 && id < TIMEFRAMES.length) {
			currentId = id;
			
			notifyListeners();
		}
	}

	
	/**
	 * returns the current time frame id.
	 * @return the current time frame id.
	 */
	public int getCurrentTimeFrameId() {
		return currentId;
	}
	
	/**
	 * returns the current time frame size.
	 * @return the current time frame size.
	 */
	public long getFrameSize() {
		return TIMEFRAMES[currentId].time;
	}

	/**
	 * get the time frame identified by given id. 
	 * @param id - id of time frmae
	 * @return time frame identified by given id.
	 */
	public TimeFrame getTimeFrameById(int id) {
		return TIMEFRAMES[id];
	}
}