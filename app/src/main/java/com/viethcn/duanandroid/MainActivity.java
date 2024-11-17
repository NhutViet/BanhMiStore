package com.viethcn.duanandroid;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.viethcn.duanandroid.Fragments.HomePageFragment;
import com.viethcn.duanandroid.Fragments.ProfileFragment;
import com.viethcn.duanandroid.Fragments.SearchFragment;
import com.viethcn.duanandroid.Fragments.SettingFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Bottom Navigation and it's method to redirect to correct page
        bottomNav = findViewById(R.id.bottomNavBar);
        selectedOptionOnBottomNav();

        replaceFragment(new HomePageFragment());
    }

    // This one is made for bottom navigation bar to redirect to correct page
    private void selectedOptionOnBottomNav() {
        bottomNav.setOnItemSelectedListener(item -> {
            int index = item.getItemId();
            if (index == R.id.bottom_nav_search) {
                replaceFragment(new SearchFragment());
            } else if (index == R.id.bottom_nav_userIdentify) {
                replaceFragment(new ProfileFragment());
            } else if (index == R.id.bottom_nav_setting) {
                replaceFragment(new SettingFragment());
            } else if (index == R.id.bottom_nav_homePage) {
                replaceFragment(new HomePageFragment());
                return true;
            }
            return true;
        });
    }

    // This func to take the main fragment off and then replace another fragment
    private void replaceFragment(Fragment layout) {
        getSupportFragmentManager().beginTransaction().replace(R.id.homePageMain, layout).commit();
    }
}