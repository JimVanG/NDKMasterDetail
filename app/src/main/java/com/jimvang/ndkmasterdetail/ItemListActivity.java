package com.jimvang.ndkmasterdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimvang.ndkmasterdetail.data.MovieItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity
{
    private static final String TAG = ItemListActivity.class.getName();

    public static final int NUMBER_OF_ITEMS = 18;

    MovieItem[] movieItemArray;

    // Used to load the 'native-lib' library for use in this class.
    static
    {
        System.loadLibrary("native-lib");
    }

    /**
     * Native methods implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native MovieItem[] getMovieItemsFromJNI(int numOfMovies);

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (movieItemArray == null || movieItemArray.length < 1)
        {
            movieItemArray = getMovieItemsFromJNI(NUMBER_OF_ITEMS);
        }

        if (movieItemArray != null)
        {
            for (MovieItem item : movieItemArray)
            {
                Log.d(TAG, "onCreate - items from JNI: " + item);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //        fab.setOnClickListener(new View.OnClickListener()
        //        {
        //            @Override
        //            public void onClick(View view)
        //            {
        //                Snackbar.make(view, stringFromJNI(), Snackbar.LENGTH_LONG)
        //                        .setAction("Action", null).show();
        //            }
        //        });

        if (findViewById(R.id.item_detail_container) != null)
        {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView)
    {
        recyclerView
                .setAdapter(new SimpleItemRecyclerViewAdapter(this, movieItemArray, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>
    {

        private final ItemListActivity mParentActivity;
        private final MovieItem[] mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MovieItem item = (MovieItem) view.getTag();
                if (mTwoPane)
                {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_NAME, item.name);
                    arguments.putInt(ItemDetailFragment.ARG_ITEM_COUNT, NUMBER_OF_ITEMS);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                }
                else
                {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_NAME, item.name);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_COUNT, NUMBER_OF_ITEMS);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(
                ItemListActivity parent,
                MovieItem[] items,
                boolean twoPane)
        {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
        {
            holder.mIdView.setText(String.valueOf(mValues[position].lastUpdated));
            holder.mContentView.setText(mValues[position].name);

            holder.itemView.setTag(mValues[position]);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount()
        {
            return mValues.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view)
            {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
