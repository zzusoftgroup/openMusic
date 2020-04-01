package com.zzu.peifuYang.work959;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class PageActivity extends FragmentActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        //找到ViewPager
        mPager =  findViewById(R.id.pager);
//        添加fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new playerFragment());
        fragments.add(new discoverFragment());
        pagerAdapter = new PageAdapter(this,fragments);
        mPager.setAdapter(pagerAdapter);
    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
}
