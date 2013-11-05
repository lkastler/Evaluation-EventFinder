package de.unikoblenz.west.lkastler.eventfinder.fragments;

import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataControl;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModel;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderListener;
import de.unikoblenz.west.lkastler.eventfinder.R;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderView;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.commands.TimesliderEvent;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimesliderFragment extends Fragment implements TimesliderListener {

	TimesliderView timeslider;
	TimesliderDataModel model;
	TimesliderDataControl control;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v =  inflater.inflate(R.layout.timeslider_fragment, container, false);
		
		timeslider =(TimesliderView)v.findViewById(R.id.TestView);
		timeslider.addListener(this);
			
		return v;
	}

	@Override
	public void notify(TimesliderView sender, TimesliderEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
