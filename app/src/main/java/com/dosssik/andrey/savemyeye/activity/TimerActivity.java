package com.dosssik.andrey.savemyeye.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dosssik.andrey.savemyeye.R;
import com.dosssik.andrey.savemyeye.fragment.TimerFragment;
import com.dosssik.andrey.savemyeye.wiget.RippleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_menu_info_item)
    RippleView navMenuInfoItem;
    @BindView(R.id.nav_menu_settings_item)
    RippleView navMenuSettingsItem;
    @BindView(R.id.nav_menu_rate_item)
    RippleView navMenuRateItem;
    @BindView(R.id.nav_settings_container)
    LinearLayout navSettingsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        ButterKnife.bind(this);
        setUpToolbar();
        setUpDrawer();
        setUpFragment();
    }

    private void setUpDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setUpItemListeners();
    }

    private void setUpItemListeners() {
        navMenuInfoItem.setOnRippleCompleteListener(click -> onInfoItemClick());
        navMenuSettingsItem.setOnRippleCompleteListener(click -> onSettingsItemClick());
        navMenuRateItem.setOnRippleCompleteListener(click -> onRateItemClick());
    }

    private void onInfoItemClick() {
        drawer.closeDrawer(GravityCompat.START);
    }

    private void onSettingsItemClick() {
        boolean isContainerVisible = navSettingsContainer.getVisibility() == View.VISIBLE;
        navSettingsContainer.setVisibility(isContainerVisible
                ? View.GONE
                : View.VISIBLE);
    }

    private void onRateItemClick() {

    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    private void setUpFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,
                        TimerFragment.newInstance(),
                        TimerFragment.class.getName())
                .commit();
    }
}
