package de.unikoblenz.west.lkastler.eventfinder.fragments;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;

public class TimePickerFragment extends DialogFragment {

	private Calendar calendar;
	private OnTimeSetListener listener;
	
	public TimePickerFragment() {
		super();
		this.setCancelable(true);
	}
	
	/**
	 * @return the calendar
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * @return the listener
	 */
	public OnTimeSetListener getListener() {
		return listener;
	}
	
	/**
	 * @param calendar the calendar to set
	 */
	void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * @param listener the listener to set
	 */
	void setListener(OnTimeSetListener listener) {
		this.listener = listener;
	}

		
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), listener, hour, minute, 
        		DateFormat.is24HourFormat(getActivity()));
    }

}
