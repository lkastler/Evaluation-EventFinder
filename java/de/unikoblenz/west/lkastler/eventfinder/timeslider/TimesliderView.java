package de.unikoblenz.west.lkastler.eventfinder.timeslider;

import java.util.LinkedList;

import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModel.TimeFrame;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.FastForwardTimesliderEvent;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.LongPressTimesliderEvent;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.ScrollTimesliderEvent;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.TimeFrameChangeTimesliderEvent;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.TimesliderEvent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * 
 * @author lkastler
 */
public class TimesliderView extends View implements OnGestureListener,
		OnDoubleTapListener, TimesliderDataModelListener {

	public static final String TAG = "VIEW";
	
	/** defines the size of the upper part of the timeslider aka point in time field */
	final float pointInTimeFieldSize = 0.6f;
	
	// listeners on this TimesliderView
	LinkedList<TimesliderListener> listeners = new LinkedList<TimesliderListener>();

	// paints for the different fields
	Paint background;
	Paint lines;
	Paint tfText;
	Paint pitText;
	Paint pitplusText;
	Paint buttons;
	Paint selectedTfText;

	GestureDetectorCompat detector;

	// exact point in time
	long pit = 0;
	// point in time - slider offset
	long offset = 0;
	// current time frame id
	int currentTimeFrameId = 0;
	// available time frames
	TimeFrame[] timeFrames = null;

	public TimesliderView(Context context) {
		super(context);
		init();
	}

	public TimesliderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TimesliderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * initializes standards
	 */
	private void init() {
		background = new Paint();
		background.setColor(Color.rgb(57,75,133));

		lines = new Paint();
		lines.setColor(Color.rgb(243,173,30));

		buttons = new Paint();
		buttons.setColor(Color.WHITE);

		// point in time text
		pitText = new Paint();
		pitText.setTextAlign(Paint.Align.LEFT);
		pitText.setColor(Color.LTGRAY);
		pitText.setTextSize(50);
		
		// point in time + time frame text
		pitplusText = new Paint();
		pitplusText.setTextAlign(Paint.Align.RIGHT);
		pitplusText.setColor(Color.LTGRAY);
		pitplusText.setTextSize(50);

		// time frames text
		tfText = new Paint();
		tfText.setTextAlign(Paint.Align.CENTER);
		tfText.setColor(Color.LTGRAY);
		tfText.setTextSize(50);
		
		selectedTfText = new Paint();
		selectedTfText.setTextAlign(Paint.Align.CENTER);
		selectedTfText.setColor(Color.BLACK);
		selectedTfText.setTextSize(50);

		detector = new GestureDetectorCompat(this.getContext(), this);
		detector.setOnDoubleTapListener(this);

		Log.d(TAG, "created");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(timeFrames == null) {
			return;
		}
		
		/*
		 * some ascii art to explain the time slider design
		 * 
		 * 
		 * |==================================================|
		 * |<--hour-->|        point in time                  | } pointInTimeFieldSize
		 * |==================================================| < -- border 
		 * |               |   time frames    |               |
		 * |==================================================|
		 */
			
			
		float border = getHeight() * pointInTimeFieldSize;
		long hour = getWidth() / (timeFrames[currentTimeFrameId].getTime() / TimesliderDataModel.HOUR) ;
				
		
		// Point in time navigation element
		canvas.drawRect(getLeft(), getTop(), getRight(), border - 1, background);

		// lines
		for (int i = 0; i < getWidth(); i += (hour)) {
			// efficiency is overrated
			long x = i - (offset % hour);
			if (x < 0)
				x += hour;
			canvas.drawLine(x, this.getTop(), x, border - 1, lines);
		}
		
		canvas.drawText(TimeConverter.timeLongToString(pit), 1, pitText.getTextSize(), pitText);
		canvas.drawText(TimeConverter.timeLongToString(
				pit + timeFrames[currentTimeFrameId].getTime()),
				getWidth() - 1,
				Math.max(pitText.getTextSize() + pitplusText.getTextSize(), border - pitplusText.getTextSize()),
				pitplusText
			);
		
		// Timeframe navigational element
		canvas.drawRect(
				getLeft(), 
				border + 1, 
				getRight(),
				getBottom(),
				background);

		// frames
		for (int i = 0; i < timeFrames.length; i++) {
			canvas.drawLine(i * getWidth() / timeFrames.length, border + 1,
					i * getWidth() / timeFrames.length, getBottom(), lines);
		}

		// selected frame
		canvas.drawRect(currentTimeFrameId * getWidth() / timeFrames.length,
				border + 1,
				(currentTimeFrameId + 1) * getWidth() / timeFrames.length,
				getBottom(),
				lines);
		// text
		for (int i = 0; i < timeFrames.length; i++) {
			
			float x = (i + 0.5f) * getWidth() / timeFrames.length;
			
			canvas.drawText(
				timeFrames[i].getName(), 
				x,
				(getHeight() + border) / 2 + tfText.getTextSize() / 2,
				(currentTimeFrameId == i) ? selectedTfText : tfText);
		}

	}

	/**
	 * adds given TimesliderListener to be notified by this TimesliderView.
	 * @param l - TimesliderListener to notify.
	 */
	public void addListener(TimesliderListener l) {
		listeners.add(l);
	}

	/**
	 * notifies attached TimesliderListeners that given event was triggered. 
	 * @param event - triggered TimesliderEvent.
	 */
	private void notifyListeners(TimesliderEvent event) {
		for (TimesliderListener l : listeners) {
			l.notify(this, event);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.example.test.data.DataModelListener#notify(com.example.test.data.DataModel)
	 */
	@Override
	public void notify(TimesliderDataModel sender) {
		pit = sender.getPointInTime();
		if(getWidth() != 0) {
			offset = sender.getPointInTime() / (sender.getFrameSize() / getWidth());
		}
		timeFrames = sender.TIMEFRAMES;
		currentTimeFrameId = sender.getCurrentTimeFrameId();
		
		Log.d(TAG, "notify: " + offset + ", " + currentTimeFrameId);
		
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event) || super.onTouchEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override	
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (e1.getY() < getHeight() * pointInTimeFieldSize) {
			
			notifyListeners(new ScrollTimesliderEvent(distanceX, getWidth()));
		}
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onLongPress(android.view.MotionEvent)
	 */
	@Override
	public void onLongPress(MotionEvent e) {
		notifyListeners(new LongPressTimesliderEvent());
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnDoubleTapListener#onSingleTapConfirmed(android.view.MotionEvent)
	 */
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// for upper part: 
		if (e.getY() < this.getHeight() * pointInTimeFieldSize) {

		}
		// for lower part: 
		else {
			Log.d(TAG, "clicked: " + e.getX());
			int tf = 0;
			
			while(tf < timeFrames.length 
					&& e.getX() > (tf+1) * (getWidth() / timeFrames.length)) {
				tf++;
			}
			notifyListeners(new TimeFrameChangeTimesliderEvent(tf));
		}
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnDoubleTapListener#onDoubleTap(android.view.MotionEvent)
	 */
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		if(e.getY() < getHeight() * pointInTimeFieldSize) {
			notifyListeners(new FastForwardTimesliderEvent(e.getX() > getWidth() / 2));
		}
		
		return true;
	}

	
	// ---- UNUSED -----
	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.MotionEvent)
	 */
	@Override
	public boolean onDown(MotionEvent e) {return true;}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onShowPress(android.view.MotionEvent)
	 */
	@Override
	public void onShowPress(MotionEvent e) {}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onSingleTapUp(android.view.MotionEvent)
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {return true;}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {return false;}

	/*
	 * (non-Javadoc)
	 * @see android.view.GestureDetector.OnDoubleTapListener#onDoubleTapEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		return false;
	}
}
