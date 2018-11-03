package com.safeselly.safeselly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CrimeAdapter extends ArrayAdapter<Crime> {

    private static final String DATE_SEPERATOR = "-";


    /**
     *
     * Constructor for a new {@link CrimeAdapter}
     *
     * @param context The context of the app
     * @param earthquakes A list of earthquakes
     */
    CrimeAdapter(@NonNull Context context, ArrayList<Crime> earthquakes) {
        super(context, 0, earthquakes);
    }


    /**
     * Returns a list item view that displays information about the earthquake
     * at the given position in the list of earthquakes
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Crime currentCrime = getItem(position);

        // Find the location text view and set its text to the crime's location
        TextView locationTextView = listItemView.findViewById(R.id.location);
        locationTextView.setText(currentCrime.getLocation());

        // Find the category text view and set its text to the crime's category
        TextView categoryTextView = listItemView.findViewById(R.id.category);
        categoryTextView.setText(currentCrime.getCategory());


        // Get the date of the crime and split it into the year and month
        String originalDate = currentCrime.getDate();
        String[] date = originalDate.split(DATE_SEPERATOR);
        // Find the month text view and set its text to the crime's month
        TextView monthTextView = listItemView.findViewById(R.id.month);
        monthTextView.setText(date[0]);
        // Find the year text view and set its text to the crime's year
        TextView yearTextView = listItemView.findViewById(R.id.year);
        yearTextView.setText(date[1]);




        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

}
