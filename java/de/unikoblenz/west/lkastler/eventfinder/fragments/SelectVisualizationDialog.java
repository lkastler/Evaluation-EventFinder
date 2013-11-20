package de.unikoblenz.west.lkastler.eventfinder.fragments;

import de.unikoblenz.west.lkastler.eventfinder.Configuration.VISUALIZATIONS;
import de.unikoblenz.west.lkastler.eventfinder.MainActivity;
import de.unikoblenz.west.lkastler.eventfinder.R;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class SelectVisualizationDialog extends DialogFragment {

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.select_vis_dialog,  container, false);
		
		Button b = (Button) v.findViewById(R.id.select_list);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).setVisualization(VISUALIZATIONS.LIST);
				dismiss();
			}
			
		});
		
		if(((MainActivity) getActivity()).getVisualization() == VISUALIZATIONS.LIST) {
			b.setActivated(false);
		}
		else {
			b.setActivated(true);
		}
		

		b = (Button) v.findViewById(R.id.select_map);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).setVisualization(VISUALIZATIONS.MAP);
				dismiss();
			}
			
		});
		
		if(((MainActivity) getActivity()).getVisualization() == VISUALIZATIONS.MAP) {
			b.setActivated(false);
		}
		else {
			b.setActivated(true);
		}
		
		b = (Button) v.findViewById(R.id.select_timeslider);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).setVisualization(VISUALIZATIONS.TIMESLIDER);
				dismiss();
			}
			
		});
		
		if(((MainActivity) getActivity()).getVisualization() == VISUALIZATIONS.TIMESLIDER) {
			b.setActivated(false);
		}
		else {
			b.setActivated(true);
		}
		
		return v;
	}

}
