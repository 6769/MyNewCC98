package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;
import org.cc98.mycc98.fragment.SearchViewTopicsFragment;

public class SearchViewActivity extends BaseSwipeBackActivity {

    public static final String BOARD_KEY = "Board";
    public static final String WORD_KEYS = "Keys";

    public static void startActivity(Context context, int boardId, String keys) {
        Intent intent = new Intent(context, SearchViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(BOARD_KEY, boardId);
        bundle.putString(WORD_KEYS, keys);

        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Resources resources = getResources();

        int boardId = bundle.getInt(BOARD_KEY, 0);
        String keys = bundle.getString(WORD_KEYS, "");
        setTitle(getString(R.string.searchview_result_title)+ keys);

        Fragment fragment = SearchViewTopicsFragment.newInstance(keys, boardId);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_search_view, fragment);
        transaction.commit();

    }

    public static class SearchViewKeywordsListener implements SearchView.OnQueryTextListener{
        protected Context context;
        protected int boardId;

        public SearchViewKeywordsListener(Context context, int boardId) {
            this.context = context;
            this.boardId = boardId;
        }

        @Override
        public boolean onQueryTextSubmit(String s) {
            if ( s.split( " ").length > 5 ){
                Toast.makeText(context,context.getString(R.string.searchview_excessive_keys),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            SearchViewActivity.startActivity(context,boardId,s);

            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    }
}
