package de.unikoblenz.west.lkastler.eventfinder.fragments;

import de.unikoblenz.west.lkastler.eventfinder.R;
import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EventDialog extends DialogFragment {

	protected Event event;
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.event_dialog, container);
		
		getDialog().setTitle(event.getTitle());
		
		TextView description = (TextView)v.findViewById(R.id.description);
		
		description.setText(event.toString());
		
		Button close = (Button)v.findViewById(R.id.closeButton);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
			
		});
		
		return v;
	}

	public void setEvent(Event e) {
		event = e;
	}
}
