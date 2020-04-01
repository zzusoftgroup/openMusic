package com.zzu.peifuYang.work959;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> fragments;
    public PageAdapter(FragmentActivity fa, ArrayList<Fragment> fragments) {
        super(fa);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }
    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
