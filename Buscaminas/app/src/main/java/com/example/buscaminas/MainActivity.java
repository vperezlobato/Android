package com.example.buscaminas;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.buscaminas.ui.dashboard.DashboardFragment;
import com.example.buscaminas.ui.home.HomeFragment;
import com.example.buscaminas.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView ;
    private ViewPager viewPager;
    DashboardFragment dashboardFragment;
    HomeFragment homeFragment;
    NotificationsFragment notificationsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView  = findViewById(R.id.nav_view);

        viewPager = findViewById(R.id.viewpager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        setupViewPager(viewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean pagActual = false;

        switch (item.getItemId()) {

            case R.id.navigation_home:
                viewPager.setCurrentItem(0);
                pagActual = true;
            break;

            case R.id.navigation_dashboard:
                viewPager.setCurrentItem(1);
                pagActual = true;
            break;

            case R.id.navigation_notifications:
                viewPager.setCurrentItem(2);
                pagActual = true;
            break;
        }
        return pagActual;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        dashboardFragment=new DashboardFragment();
        notificationsFragment = new NotificationsFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(dashboardFragment);
        adapter.addFragment(notificationsFragment);
        viewPager.setAdapter(adapter);
    }
}
