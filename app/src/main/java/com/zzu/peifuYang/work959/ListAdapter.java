package com.zzu.peifuYang.work959;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewFiller> {
    private Map<String,String>[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class  ViewFiller extends RecyclerView.ViewHolder{
        public final TextView song_name;
        private final TextView artist_name;
        private final ImageView album;
        private final ImageButton more_btn;
        public ViewFiller(View v) {
            super(v);
             song_name = v.findViewById(R.id.item_song_name);
             artist_name = v.findViewById(R.id.item_artist_name);
             album = v.findViewById(R.id.item_album);
             more_btn = v.findViewById(R.id.item_more_btn);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapter(Map<String,String>[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewFiller onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tem, parent, false);
        ViewFiller vh = new ViewFiller(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewFiller holder, int position) {
        holder.song_name.setText(mDataset[position].get("song_name"));
        holder.artist_name.setText(mDataset[position].get("artist_name"));
//        holder.album.setImageResource(R.id.album_discover);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
    