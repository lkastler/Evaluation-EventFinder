package de.unikoblenz.west.lkastler.eventfinder.presentations;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.unikoblenz.west.lkastler.eventfinder.R;

/**
 * Created by lkastler on 9/5/13.
 */
public class SearchFragment extends Fragment {

    private TextView search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search, container, false);

        search = (TextView) v.findViewById(R.id.search_text);

        Button b = (Button) v.findViewById(R.id.search_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToPresentation();
            }
        });

        return v;
    }

    /**
     * switches from the search fragment to the correct presentation  fragment
     */
    private void switchToPresentation() {
    }
}
