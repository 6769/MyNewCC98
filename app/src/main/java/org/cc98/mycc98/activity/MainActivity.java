package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.ActivityCollector;
import org.cc98.mycc98.activity.base.BaseActivity;
import org.cc98.mycc98.adapter.MainFragmentPagerAdapter;
import org.cc98.mycc98.config.UserConfig;
import org.cc98.mycc98.fragment.BoardMapFragment;
import org.cc98.mycc98.fragment.BoardViewPostFragment;
import org.cc98.mycc98.fragment.HotTopicsFragment;
import org.cc98.mycc98.fragment.NewTopicsFragment;
import org.cc98.mycc98.service.VersionCheckService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import win.pipi.api.data.UserInfo;
import win.pipi.api.network.CC98APIInterface;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

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


    View headerView;
    TextView userNameLabel;
    TextView userIntroLabel;
    ImageView userLogo;
    private CC98APIInterface iface;






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
        iface= MainApplication.getApiInterface();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        headerView=navigationView.inflateHeaderView(R.layout.nav_header_main);
        /*
        * Directly find view in drawerlayout will result in null ptr.
        * Seems like the delay load view in naviheader;
        * http://blog.csdn.net/fxlysm/article/details/52920749
        * */
        userNameLabel=headerView.findViewById(R.id.mainact_tx_username);
        userIntroLabel=headerView.findViewById(R.id.mainact_tx_userintro);
        userLogo=headerView.findViewById(R.id.profile_image);


        tabLayout.setupWithViewPager(viewPager);
        Resources resources = getResources();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);

        List<String> main_tab_name = Arrays.asList(
                resources.getStringArray(R.array.activity_main_tab_name));
        List<Fragment> main_tab_frag = new ArrayList<>();
        String myzone_bid=preferences.getString(getString(R.string.pref_myzone_sharekey),"152");
        main_tab_frag.add(BoardViewPostFragment.newInstance(Integer.valueOf(myzone_bid)));
        main_tab_frag.add(new HotTopicsFragment());
        main_tab_frag.add(new BoardMapFragment());
        main_tab_frag.add(NewTopicsFragment.newInstance());

        viewPager.setOffscreenPageLimit(main_tab_frag.size());
        //make sure not to reload views repeatably
        viewPager.setAdapter(new MainFragmentPagerAdapter(
                getSupportFragmentManager(),
                main_tab_frag,
                main_tab_name));


        VersionCheckService.initVersionCheckerService(getApplication());
        setupUserLogo();
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

        } else if (id == R.id.nav_slideshow) {
            EditActivity.startActivity(this);

        } else if (id == R.id.nav_manage_setting) {
            SettingActivity.startActivity(this);

        } else if (id == R.id.nav_about_page) {
            AboutActivity.startActivity(this);

        } else if (id == R.id.nav_gotopic) {

            editText = new EditText(this); // possible dangerous memory leaks!!!
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
            alert.show();


        } else if(id== R.id.nav_exit_app){

            ActivityCollector.finishAll();

        }else if (id==R.id.nav_loutout){
            AlertDialog alertRelog = new AlertDialog.Builder(this).setTitle("LogOut")
                    .setMessage("Are You Sure?")
                    .setNegativeButton("Cancel",null)
                    .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LoginActivity.startActivity(MainActivity.this, LoginActivity.LoginType.RELOGIN);
                            finish();
                        }
                    }).create();
            alertRelog.show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void setupUserLogo(){
        UserInfo userInfo= UserConfig.getUserInfo();
        if (userInfo!=null ){
            userNameLabel.setText(userInfo.getName());
            //userIntroLabel.setText(userInfo.getIntroduction());
            String userpic=userInfo.getPortraitUrl();
            try{
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .dontAnimate();
                Glide.with(this)
                        .load(userpic)
                        .apply(options)
                        .into(userLogo);
            }catch (Exception e){
                loge(e,"Glide load userpic failed");
            }

        }
    }
}


