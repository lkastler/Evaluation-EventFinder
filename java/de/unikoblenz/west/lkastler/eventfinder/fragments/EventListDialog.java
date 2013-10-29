package de.unikoblenz.west.lkastler.eventfinder.fragments;

import de.unikoblenz.west.lkastler.eventfinder.R;
import de.unikoblenz.west.lkastler.eventfinder.events.EventList;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class EventListDialog extends DialogFragment {

	protected EventList list;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.event_list_dialog, container);
		
		ListView el = (ListView) v.findViewById(R.id.event_list);
		
		EventAdapter ea = new EventAdapter();
		ea.addAll(list);
		el.setAdapter(ea);
	
		el.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				EventDialog d = new EventDialog();
				d.setEvent(list.get(position));
				d.show(getFragmentManager(), FragmentCommunication.EVENT_DIALOG);
			}
			
			
		});
		
		Button closeButton = (Button) v.findViewById(R.id.closeButton);
		closeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
			
		});
		
		return v;
	}

	public void setEventList(EventList list) {
		this.list = list;
	}
}
