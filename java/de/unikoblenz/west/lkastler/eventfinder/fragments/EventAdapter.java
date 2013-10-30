package de.unikoblenz.west.lkastler.eventfinder.fragments;

import java.util.Iterator;
import java.util.List;

import de.unikoblenz.west.lkastler.eventfinder.R;
import de.unikoblenz.west.lkastler.eventfinder.events.Event;
import de.unikoblenz.west.lkastler.eventfinder.events.EventList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter implements Iterable<Event>{
	
	protected EventList list;

	public EventAdapter() {
		this.list = new EventList();
	}
	
	public void addAll(List<Event> events) {
		list.clear();
		list.addAll(events);
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Event getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		if( position >= 0
				&& list.size() > position) {
			return list.get(position).getId();
		} 
		else {
			return -1;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(parent.getContext(), R.layout.list_presentation_item, null);
		
		TextView title = (TextView)convertView.findViewById(R.id.title);
		title.setText(list.get(position).getTitle());
		
		TextView venue = (TextView)convertView.findViewById(R.id.venue);
		venue.setText(list.get(position).getVenue());
		
		TextView artist = (TextView)convertView.findViewById(R.id.artist);
		artist.setText(list.get(position).getArtists());
		
		return convertView;
	}

	@Override
	public Iterator<Event> iterator() {
		return list.iterator();
	}	
}
