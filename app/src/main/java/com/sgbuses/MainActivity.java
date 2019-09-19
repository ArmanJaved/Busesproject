package com.sgbuses;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FavouriteList obj  = new FavouriteList(getApplicationContext());
        obj.comp_list();


        final Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        final TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager(), MainActivity.this);
        adapter.addFragment(new Tab1Fragment(), "Home");
        adapter.addFragment(new Tab2Fragment(), "Map");
        adapter.addFragment(new Tab3Fragment(), "Bus");
        adapter.addFragment(new Tab4Fragment(), "Stop");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
//        highLightCurrentTab(0); // for initial selected tab view
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    mTitle.setText("SG Buses");
//                    Tab1Fragment addPhotoBottomDialogFragment =
//                            Tab1Fragment.newInstance("");
//                    addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
//                            "add_photo_dialog_fragment");

//                    final BottomSheetDialogFragment myBottomSheet = Tab1Fragment.newInstance("Modal Bottom Sheet");
//                    myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
                } else if (position == 1) {
                    mTitle.setText("SG Map");

                } else if (position == 2) {
                    mTitle.setText("SG Buses");
                } else if (position == 3) {
                    mTitle.setText("SG Stop");
                }
            }
            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position); // for tab change
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.getTabView(i));
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getSelectedTabView(position));
    }
}
