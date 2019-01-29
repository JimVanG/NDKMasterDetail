package com.jimvang.ndkmasterdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jimvang.ndkmasterdetail.data.MovieDetailItem;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment
{

    public static final String ARG_ITEM_NAME = "item_name";
    public static final String ARG_ITEM_COUNT = "item_count";


    private MovieDetailItem movieDetailItem;

    // Used to load the 'native-lib' library for use in this class.
    static
    {
        System.loadLibrary("native-lib");
    }

    public native MovieDetailItem getMovieDetailFromJNI(String movieName, int numOfMovies);

    /**
     * Required empty Fragment Constructor
     */
    public ItemDetailFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (Objects.requireNonNull(getArguments()).containsKey(ARG_ITEM_NAME))
        {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            movieDetailItem = getMovieDetailFromJNI(getArguments().getString(ARG_ITEM_NAME),
                                                    getArguments().getInt(ARG_ITEM_COUNT));

            CollapsingToolbarLayout appBarLayout = getActivity().findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(movieDetailItem.name);
            }
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (movieDetailItem != null)
        {
            TextView nameTextView = (TextView) rootView.findViewById(R.id.item_name);
            if (nameTextView != null)
            {
                nameTextView.setText(movieDetailItem.name);
            }

            ((TextView) rootView.findViewById(R.id.item_detail))
                    .setText(movieDetailItem.description);
        }

        return rootView;
    }
}
