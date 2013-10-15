package de.unikoblenz.west.lkastler.eventfinder.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class DatePickerFragment extends DialogFragment {

	private Calendar calendar;
	private OnDateSetListener listener;

	public DatePickerFragment() {
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
	public OnDateSetListener getListener() {
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
	void setListener(OnDateSetListener listener) {
		this.listener = listener;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);
	}
}
