package dev.santieinaki.fagamosbarullo.app.module.main;

import android.os.Bundle;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.module.BaseActivity;
import dev.santieinaki.fagamosbarullo.app.module.main.profile.ProfileFragment;
import dev.santieinaki.fagamosbarullo.app.module.main.search.SearchFragment;
import dev.santieinaki.fagamosbarullo.app.module.main.settings.SettingsFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpNavigationBar();
    }

    public void selectProfile() {

        mNavigation.setSelectedItemId(R.id.profile);
    }

    private void setUpNavigationBar() {

        mNavigation.setSelectedItemId(R.id.profile);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selected = null;

                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        selected = new ProfileFragment();
                        break;
                    case R.id.search:
                        selected = new SearchFragment();
                        break;
                    case R.id.settings:
                        selected = new SettingsFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected).commit();
                return true;
            }
        });
    }
}