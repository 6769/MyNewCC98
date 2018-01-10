package org.cc98.mycc98.activity;

import android.app.ActionBar;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;
import org.cc98.mycc98.fragment.BoardViewPostFragment;
import org.cc98.mycc98.fragment.HotTopicsFragment;

public class ABoardViewActivity extends BaseSwipeBackActivity {
    //list of boards's subposts
    public static final String BOARD_KEY = "Board";
    public static final String BOARD_NAME = "Boardname";

    private String PREFIX_BOARD;
    private int boardId;
    private String boardName;
    private ActionBar actionBar;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ABoardViewActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int boardId, String boardName) {
        Intent intent = new Intent(context, ABoardViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BOARD_KEY, boardId);
        bundle.putString(BOARD_NAME, boardName);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Resources resources=getResources();

        boardId = bundle.getInt(BOARD_KEY, resources.getInteger(R.integer.default_board_helppart));
        boardName = bundle.getString(BOARD_NAME, getString(R.string.default_board_name));
        PREFIX_BOARD = getString(R.string.default_board_prefix);
        setContentView(R.layout.activity_board_view);

        actionBar = getActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle(PREFIX_BOARD + boardName);

        Fragment postFragment = BoardViewPostFragment.newInstance(boardId);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_board_view, postFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                //WARNING:android.R.id   =.=
                finish();
                break;
            case R.id.menu_boardview_search:

                break;

            case R.id.menu_boardview_info:

                break;

        }


        return super.onOptionsItemSelected(item);
    }
}
