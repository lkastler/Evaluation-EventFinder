package de.unikoblenz.west.lkastler.eventfinder.fragments;

import java.text.DateFormat;
import java.util.Calendar;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import de.unikoblenz.west.lkastler.eventfinder.MainActivity;
import de.unikoblenz.west.lkastler.eventfinder.R;

/**
 * Created by lkastler on 9/5/13.
 */
public class SearchFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

	public static final String TAG = "SEARCH";
	
	private final DateFormat df = DateFormat.getDateInstance();
	private final DateFormat tf = DateFormat.getTimeInstance();
	
	private Calendar cal  = Calendar.getInstance();;
	private TimePickerFragment timePicker;
	private DatePickerFragment datePicker;
	
	private View v;
	
	private EditText search;
	private AutoCompleteTextView location;
	private Spinner category;
	private Spinner timeFrame;
	
    public SearchFragment() {
		super();
		
		timePicker = new TimePickerFragment();
		timePicker.setCalendar(cal);
		timePicker.setListener(this);
		
		datePicker = new DatePickerFragment();
		datePicker.setCalendar(cal);
		datePicker.setListener(this);
		
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.search, container, false);

        // search field
        search = (EditText) v.findViewById(R.id.text_search);

        // location
        location = (AutoCompleteTextView) v.findViewById(R.id.text_location);
        location.setAdapter(new ArrayAdapter<String>(getActivity(),
        		android.R.layout.simple_spinner_dropdown_item, 
        		((MainActivity)getActivity()).getLocations()
        	));
        
        // date & time
        updateDate();

        Button timeButton = (Button) v.findViewById(R.id.button_time);
        timeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				timePicker.show(getFragmentManager(), FragmentCommunication.TIMEPICKER);
			}
        });
        
        Button dateButton = (Button) v.findViewById(R.id.button_date);
        dateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				datePicker.show(getFragmentManager(), FragmentCommunication.DATEPICKER);
			}
        });
        
        
        // now
        Button nowButton = (Button) v.findViewById(R.id.button_now);
        nowButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cal = Calendar.getInstance();
				
				updateDate();
			}
        });
        
        // time frame
        timeFrame = (Spinner) v.findViewById(R.id.spinner_time_frame);
        
        // category
        category = (Spinner) v.findViewById(R.id.spinner_category);
        category.setAdapter(new ArrayAdapter<String>(getActivity(),
        		android.R.layout.simple_spinner_dropdown_item, 
        		((MainActivity)getActivity()).getCategories()
        	));
        
        // search button
        Button b = (Button) v.findViewById(R.id.search_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Log.d(TAG, "click: " + search.getText());
            	
            	storeData();
            	
                switchToPresentation();
            }
        });

        return v;
    }
    
    /**
     * switches from the search fragment to the correct presentation fragment
     */
    private void switchToPresentation() {
    	Log.d(TAG, "switching presentation");

    	MainActivity act = (MainActivity)getActivity();
    	
    	act.switchPresentation();
    }
    
    private void storeData() {
    	Intent intent = getActivity().getIntent();
    	intent.putExtra(FragmentCommunication.SEARCH_PHRASE, search.getText());
    	intent.putExtra(FragmentCommunication.LOCATION, location.getText());
    	intent.putExtra(FragmentCommunication.CATEGORY, (String)category.getSelectedItem());
    	intent.putExtra(FragmentCommunication.POINTINTIME, cal);
    	intent.putExtra(FragmentCommunication.TIMEFRAME, 
    			(long)getActivity().getResources().getIntArray(R.array.time_frame_values)[timeFrame.getSelectedItemPosition()]);
    }
    
    private void updateDate() {
    	Button dateButton = (Button) v.findViewById(R.id.button_date);
        dateButton.setText(df.format(cal.getTime()));

        // time
        Button timeButton = (Button) v.findViewById(R.id.button_time);
        timeButton.setText(tf.format(cal.getTime()));
    }

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Log.d(TAG, "set time:" + hourOfDay + ":" + minute);
		
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		
		updateDate();
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Log.d(TAG, "set date:" + year + "-" + monthOfYear + "-" + dayOfMonth);
		
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, monthOfYear);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		
		updateDate();
	}
}
