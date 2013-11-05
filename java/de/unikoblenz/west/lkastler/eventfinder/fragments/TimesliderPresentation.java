package de.unikoblenz.west.lkastler.eventfinder.fragments;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.database.DataSetObserver;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.unikoblenz.west.lkastler.eventfinder.MainActivity;
import de.unikoblenz.west.lkastler.eventfinder.R;
import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import de.unikoblenz.west.lkastler.eventfinder.events.EventAdapter;
import de.unikoblenz.west.lkastler.eventfinder.events.EventList;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModel;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModelListener;

public class TimesliderPresentation extends Fragment implements UpdatablePresentation, TimesliderDataModelListener {

	public static final String TAG = "TIMESLIDER PRESENTATION";
	
	protected EventAdapter events = new EventAdapter();
	
	protected GoogleMap map;
	
	public TimesliderPresentation() {
		super();
		events.registerDataSetObserver(new EventDataObserver(this));
	}

	/* (non-Javadoc)
	 * @see com.google.android.gms.maps.MapFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.timeslider_presentation, container, false); 
			
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
		EventList result = new EventList();
		
		// TODO: what is near?
		for(Event ev : events) {
			if(Math.abs(ev.getLatitude() - lat) < 0.05 && Math.abs(ev.getLongitude() - lng) < 0.05) {
				result.add(ev);
			}
		}
		
		return result;
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
			for(Event e : events) {
				map.addMarker(new MarkerOptions()
					.position(new LatLng(e.getLatitude(), e.getLongitude()))
					.title(e.getTitle())
				);
			}
		}
	}
	
	@Override
	public void update() {
		events.addAll(loadData());
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
	
//	public TimesliderView getTimeslider() {
//		return timeslider;
//	}


	@Override
	public void notify(TimesliderDataModel sender) {
		// TODO: modify EventList
	}
}
