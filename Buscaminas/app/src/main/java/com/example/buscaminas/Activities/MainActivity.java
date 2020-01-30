package com.example.buscaminas.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.buscaminas.R;
import com.example.buscaminas.Adapter.ViewPagerAdapter;
import com.example.buscaminas.dashboard.DashboardFragment;
import com.example.buscaminas.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView ;
    private ViewPager viewPager;
    private DashboardFragment dashboardFragment;
    private NotificationsFragment notificationsFragment;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView  = findViewById(R.id.nav_view);

        mAuth = FirebaseAuth.getInstance();

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

            case R.id.navigation_Play:
                viewPager.setCurrentItem(0);
                pagActual = true;
            break;

            case R.id.navigation_Clasificacion:
                viewPager.setCurrentItem(1);
                pagActual = true;
            break;
        }
        return pagActual;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        dashboardFragment=new DashboardFragment();
        notificationsFragment = new NotificationsFragment();
        adapter.addFragment(dashboardFragment);
        adapter.addFragment(notificationsFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_nav_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean itemSeleccionado = false;
        switch(item.getItemId()){
            case R.id.cerrarSesion:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                itemSeleccionado = true;
            break;

        }
        return itemSeleccionado;
    }
}
