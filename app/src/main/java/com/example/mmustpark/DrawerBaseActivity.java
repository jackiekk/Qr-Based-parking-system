package com.example.mmustpark;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity<Protected> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(this, com.example.mmustpark.ProfileActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_vehicle:
                startActivity(new Intent(this, com.example.mmustpark.AddvehicleActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_history:
                startActivity(new Intent(this, com.example.mmustpark.HistoryActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_help:
                startActivity(new Intent(this, com.example.mmustpark.HelpActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_logout:
                Intent intent = new Intent (com.example.mmustpark.DrawerBaseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(com.example.mmustpark.DrawerBaseActivity.this, "Successfully Logout",Toast.LENGTH_SHORT).show();
                break;
        }

        return false;
    }

    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}