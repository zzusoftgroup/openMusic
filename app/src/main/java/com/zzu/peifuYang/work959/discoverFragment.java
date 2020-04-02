package com.zzu.peifuYang.work959;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class discoverFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public discoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_discover, container, false);
        recyclerView = root.findViewById(R.id.my_recycler_view);

//        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        HashMap<String,String>[] maps = new HashMap[10];
        for (int i=0;i<maps.length;i++){
            maps[i] = new HashMap<>();
            maps[i].put("song_name","燕归巢");
            maps[i].put("artist_name","许嵩");
            maps[i].put("album",String.valueOf(R.id.album));
        }
        mAdapter = new ListAdapter(maps);
        recyclerView.setAdapter(mAdapter);
        return root;
    }
}
