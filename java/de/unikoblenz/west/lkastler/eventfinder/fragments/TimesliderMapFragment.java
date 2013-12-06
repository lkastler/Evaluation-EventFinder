package de.unikoblenz.west.lkastler.eventfinder.fragments;

import java.util.HashMap;
import java.util.Iterator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.unikoblenz.west.lkastler.eventfinder.R;
import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import de.unikoblenz.west.lkastler.eventfinder.events.TimesliderEventModel;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModel;
import de.unikoblenz.west.lkastler.eventfinder.timeslider.TimesliderDataModelListener;

public class TimesliderMapFragment extends MapFragment implements TimesliderDataModelListener {

	public static final String TAG = "TS M F";
	
	public static BitmapDescriptor MARKER_SELECTED; 
	public static BitmapDescriptor MARKER_UNSELECTED;
	
	private HashMap<Event, Marker> markers = new HashMap<Event, Marker>();
	
	/* (non-Javadoc)
	 * @see com.google.android.gms.maps.MapFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		Log.d(TAG, "created");
		
		MARKER_SELECTED = BitmapDescriptorFactory.defaultMarker();
		MARKER_UNSELECTED  = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
		
		return v;
	}
	
	@Override
	public void notify(TimesliderDataModel sender) {
		if(sender instanceof TimesliderEventModel) {
			TimesliderEventModel model = (TimesliderEventModel)sender;
				
			GoogleMap map = getMap();
			
			if(map == null) {
				Log.e(TAG, "GoogleMap object not found");
				return;
			}
				
			updateMarkers(map, model.getUpdatedSelected(), MARKER_SELECTED);
				
			updateMarkers(map, model.getUpdatedUnselected(), MARKER_UNSELECTED);
		}
		else {
			Log.w(TAG, "don't know, but sender is no TSEM");
		}
	}
	
	// TODO comment: updates map with given marker list
	private void updateMarkers(GoogleMap map, Iterator<Event> it, BitmapDescriptor icon) {
		Log.d(TAG, "updateMarkers: ");

		Event e;
		
		while(it.hasNext()) {
			 e = it.next();
			 
			 Log.d(TAG, e.toString());
			 
			if(markers.containsKey(e)) {
				Marker selected = markers.get(e);
				selected.setIcon(icon);
			}
			else {
				
				Marker m = map.addMarker(new MarkerOptions()
					.position(new LatLng(e.getLatitude(), e.getLongitude()))
					.title(e.getTitle())
					.icon(icon)
				);
				
				markers.put(e,m);
			}
		}
	}
}