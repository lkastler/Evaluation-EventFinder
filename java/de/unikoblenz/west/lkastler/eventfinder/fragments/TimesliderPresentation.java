package de.unikoblenz.west.lkastler.eventfinder.fragments;

import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.DataSetObserver;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.unikoblenz.west.lkastler.eventfinder.MainActivity;
import de.unikoblenz.west.lkastler.eventfinder.R;
import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import de.unikoblenz.west.lkastler.eventfinder.events.EventList;
import de.unikoblenz.west.lkastler.eventfinder.events.TimesliderEventModel;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModel;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModelListener;

public class TimesliderPresentation extends Fragment implements UpdatablePresentation, TimesliderDataModelListener {

	TimesliderEventModel model;
	
	public static final String TAG = "TIMESLIDER PRESENTATION";
		
	protected GoogleMap map;
	
	public TimesliderPresentation() {
		super();
		
		model = new TimesliderEventModel();
		model.addListener(this);
	}

	/* (non-Javadoc)
	 * @see com.google.android.gms.maps.MapFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.timeslider_presentation, container, false);
		
		TimesliderFragment timeslider = (TimesliderFragment) getFragmentManager().findFragmentById(R.id.timeslider_fragment);
		
		map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		if(map != null) {
		
			Log.d(TAG, "map=" + map.toString());
			
			map.setOnMarkerClickListener(new OnMarkerClickListener() {
	
				@Override
				public boolean onMarkerClick(Marker marker) {
					
					EventListDialog d = new EventListDialog();
					
					d.setEventList(findNearEvents(marker.getPosition().latitude, marker.getPosition().longitude));
					
					d.show(getFragmentManager(), FragmentCommunication.EVENT_LIST_DIALOG);
					
					return true;
				}			
			});
		}
		return v;
	}

	protected EventList findNearEvents(double lat, double lng) {
		return model.findNearestEvents(lat, lng);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		
		update();
	}
	
	public void dataChanged() {
		if(map != null) {
			Event e = null;
			
			Iterator<Event> it = model.getSelected();
						
			while(it.hasNext()) {
				 e = it.next();
				
				map.addMarker(new MarkerOptions()
					.position(new LatLng(e.getLatitude(), e.getLongitude()))
					.title(e.getTitle())
				);
			}
			
			it = model.getUnselected();
			
			while(it.hasNext()) {
				 e = it.next();
				
				map.addMarker(new MarkerOptions()
					.position(new LatLng(e.getLatitude(), e.getLongitude()))
					.title(e.getTitle())
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
				);
			}
		}
	}
	
	@Override
	public void update() {
		model.setAllEvents(loadData());
	}
	
	private List<Event> loadData() {
    	Log.d(TAG, "loading data: " + getActivity().getIntent().getExtras());
    	
        MainActivity main = (MainActivity)getActivity();
    	    	
    	return main.findEvents(getActivity().getIntent().getExtras());
    }
	
	class EventDataObserver extends DataSetObserver {

		protected TimesliderPresentation mapPresentation;
		
		public EventDataObserver(TimesliderPresentation mapPresentation) {
			super();
			this.mapPresentation = mapPresentation;
		}

		/* (non-Javadoc)
		 * @see android.database.DataSetObserver#onChanged()
		 */
		@Override
		public void onChanged() {
			mapPresentation.dataChanged();
		}
		
	}

	@Override
	public void notify(TimesliderDataModel sender) {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		
		FragmentManager mgr = getFragmentManager();
		
		FragmentTransaction transaction = mgr.beginTransaction();
		
		transaction = transaction.remove(mgr.findFragmentById(R.id.timeslider_fragment));
		transaction = transaction.remove(mgr.findFragmentById(R.id.map));
		
		transaction.commit();
	}
}
