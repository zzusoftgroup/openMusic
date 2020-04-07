package com.zzu.peifuYang.work959;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import me.wcy.lrcview.LrcView;

/**
 * A simple {@link Fragment} subclass.
 */
public class playerFragment extends Fragment {
    private ViewGroup root;
    public playerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_player, container, false);
        PageActivity.init_view(root.findViewById(R.id.lyric_player));
        return root;
    }
    public LrcView getLrcView(){
        return root.findViewById(R.id.lyric_player);
    }
}
