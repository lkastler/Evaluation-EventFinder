package de.unikoblenz.west.lkastler.eventfinder.fragments;

import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import de.unikoblenz.west.lkastler.eventfinder.MainActivity;
import de.unikoblenz.west.lkastler.eventfinder.R;
import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import de.unikoblenz.west.lkastler.eventfinder.events.EventAdapter;

/**
 * Created by lkastler on 9/5/13.
 */
public class ListPresentation extends ListFragment implements UpdatablePresentation {
	
	public static final String TAG = "LIST";
	
	private EventAdapter adapter = new EventAdapter();
	
    public ListPresentation() {
		super();
		
		setListAdapter(adapter);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_presentation, container, false);
        
        Log.d(TAG, "created");
        
        return v;
    }
    
    private List<Event> loadData() {
    	Log.d(TAG, "loading data: " + getActivity().getIntent().getExtras());
    	
        MainActivity main = (MainActivity)getActivity();
    	    	
    	return main.findEvents(getActivity().getIntent().getExtras());
    }

	/* (non-Javadoc)
	 * @see android.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		
		update();
	}

	@Override
	public void update() {
		((EventAdapter)getListAdapter()).addAll(loadData());
	}

	/* (non-Javadoc)
	 * @see android.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		EventDialog d = new EventDialog();
		d.setEvent(adapter.getItem(position));
		
		d.show(getFragmentManager(), FragmentCommunication.EVENT_DIALOG);
	}
    
    
}
