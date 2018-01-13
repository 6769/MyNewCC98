package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseActivity;
import org.cc98.mycc98.adapter.MainFragmentPagerAdapter;
import org.cc98.mycc98.fragment.BoardMapFragment;
import org.cc98.mycc98.fragment.BoardViewPostFragment;
import org.cc98.mycc98.fragment.HotTopicsFragment;
import org.cc98.mycc98.fragment.NewTopicsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main_viewPager)
    ViewPager viewPager;
    @BindView(R.id.activity_main_tabs)
    TabLayout tabLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;


    private AlertDialog alert;
    private AlertDialog.Builder alertbuilder;
    private EditText editText;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        tabLayout.setupWithViewPager(viewPager);
        Resources resources = getResources();
        List<String> main_tab_name = Arrays.asList(
                resources.getStringArray(R.array.activity_main_tab_name));
        List<Fragment> main_tab_frag = new ArrayList<>();
        main_tab_frag.add(BoardViewPostFragment.newInstance(152));
        main_tab_frag.add(new HotTopicsFragment());
        main_tab_frag.add(new BoardMapFragment());
        main_tab_frag.add(NewTopicsFragment.newInstance());

        viewPager.setOffscreenPageLimit(main_tab_frag.size());
        //make sure not to reload views repeatably
        viewPager.setAdapter(new MainFragmentPagerAdapter(
                getSupportFragmentManager(),
                main_tab_frag,
                main_tab_name));


        editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        alertbuilder=new AlertDialog.Builder(this);
        alert=alertbuilder.setTitle(R.string.main_activity_gotopic_title)
                .setMessage(R.string.main_activity_gotopic_msg)
                .setView(editText)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logi("gotopic Canceled");
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userInput=editText.getText().toString().trim();
                        try{
                            int topicId= Integer.valueOf(userInput);
                            PostReadActivity.startActivity(MainActivity.this,topicId);
                        }catch (Exception e){
                            loge(e,"input Error");
                        }

                    }
                }).create();


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView=(SearchView) menu.findItem(R.id.main_menu_search).getActionView();
        if (searchView != null) {
            searchView.setQueryHint(getString(R.string.searchview_global_hint));
            searchView.setOnQueryTextListener(new SearchViewActivity.SearchViewKeywordsListener(this,0));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                SettingActivity.startActivity(this);
                break;

            case R.id.main_menu_search:
                break;

            case R.id.main_menu_text:
                break;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            UserProfileActivity.startActivity(this, 517471);

        } else if (id == R.id.nav_gallery) {
            LoginActivity.startActivity(this);

        } else if (id == R.id.nav_slideshow) {
            EditActivity.startActivity(this);

        } else if (id == R.id.nav_manage_setting) {
            SettingActivity.startActivity(this);

        } else if (id == R.id.nav_about_page) {
            AboutActivity.startActivity(this);

        } else if (id == R.id.nav_gotopic) {
            alert.show();


        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


