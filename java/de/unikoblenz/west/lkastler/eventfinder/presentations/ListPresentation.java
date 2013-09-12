package de.unikoblenz.west.lkastler.eventfinder.presentations;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.unikoblenz.west.lkastler.eventfinder.R;

/**
 * Created by lkastler on 9/5/13.
 */
public class ListPresentation extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_presentation, container, false);

        return v;
    }
}
