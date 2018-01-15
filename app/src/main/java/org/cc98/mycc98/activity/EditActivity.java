package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;
import org.cc98.mycc98.config.ForumConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import win.pipi.api.data.NewPostInfo;
import win.pipi.api.network.CC98APIInterface;

public class EditActivity extends BaseSwipeBackActivity {
    public static final String TOPIC_ID = "TOPIC_ID";
    public static final String POST_REFFER = "POST_REFFER";
    public static final String TARGET_USER = "TARGET_USER";
    public static final String BOARD_ID = "BOARD_ID";

    public static void startActivity(Context context) {
        startActivity(context, 0, 0, null);
    }

    public static void startActivity(Context context, int tid, String quote) {
        startActivity(context, 0, tid, quote);
    }

    public static void startActivity(Context context, int bid) {
        startActivity(context, bid, 0, null);
    }

    public static void startActivity(Context context, int bid, int tid, String quoted) {
        Intent intent = new Intent(context, EditActivity.class);
        Bundle bundle = new Bundle();
        if (quoted != null)
            bundle.putString(POST_REFFER, quoted);
        bundle.putInt(TOPIC_ID, tid);
        bundle.putInt(BOARD_ID, bid);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private REPLYTYPE postType;

    @BindView(R.id.act_edit_title_container)
     TextInputLayout titleContainer;

    @BindView(R.id.act_edit_content)
    EditText editContent;

    @BindView(R.id.act_edit_title)
    EditText editTitle;

    @BindView(R.id.act_edit_toolbar)
    Toolbar toolbar;

    private int bid, tid;
    private String quoted;
    private Observer<String> postObserver = new Observer<String>() {
        @Override
        public void onCompleted() {
            mkToast(getString(R.string.editor_reply_topic_success));
            //TODO: add a circling zone for bolck user operation,unless cancelled.
            finish();
        }

        @Override
        public void onError(Throwable e) {

            mkToast(e.toString());
        }

        @Override
        public void onNext(String s) {
            logi(s);

        }
    };
    private CC98APIInterface iface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);
        ButterKnife.bind(this);

        iface = MainApplication.getApiInterface();
        Bundle bundle = getIntent().getExtras();
        bid = bundle.getInt(BOARD_ID);
        tid = bundle.getInt(TOPIC_ID);
        quoted = bundle.getString(POST_REFFER, "");
        String title;

        if (bid > 0) {
            title = getString(R.string.editor_newtopic_title) + ForumConfig.getBoardNameViaId(bid);
            postType = REPLYTYPE.NEWTOPIC;
        } else {

            postType = REPLYTYPE.REPLY;

            titleContainer.setVisibility(View.GONE);

            editContent.setFocusable(true);
            title = getString(R.string.editor_reply_topic);
        }

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (postType == REPLYTYPE.REPLY && !quoted.isEmpty()) {
            editContent.setText(quoted);
        }

        editContent.addTextChangedListener(new EditTextWatcher());


    }


    @Override
    protected void onResume() {
        super.onResume();
        //load Config here

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_edit_go_setting:
                SettingActivity.startActivity(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.act_edit_ibtn_preview)
    protected void editContentPreview(View view) {
        String userinput = editContent.getText().toString();
        PreviewActivity.startActivity(this, userinput);

    }

    @OnClick(R.id.act_edit_ibtn_send)
    protected void sendOutPosts(View view) {
        String inputtitle = editTitle.getText().toString().trim();
        String inputcontent = editContent.getText().toString().trim();


        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isMd = preferences.getBoolean(getString(R.string.pref_switchubb_md_key),false);
        int contentType = isMd ? 1:0;


        NewPostInfo newPostInfo;
        Observable<String> call = null;
        if (postType == REPLYTYPE.NEWTOPIC) {
            if (inputcontent.isEmpty()||inputtitle.isEmpty()){
                return;
                //the better way is textchange listener;
            }
            newPostInfo = new NewPostInfo(inputtitle, inputcontent, contentType);
            call = iface.postTopicBoard(bid, newPostInfo);
        }
        if (postType == REPLYTYPE.REPLY) {
            if (inputcontent.isEmpty()){
                return;
            }
            newPostInfo = new NewPostInfo("", inputcontent, contentType);
            call = iface.postReplyTopic(tid, newPostInfo);
        }

        if (call != null)
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(postObserver);

    }



    protected class EditTextWatcher implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}

enum REPLYTYPE {
    NEWTOPIC, REPLY
}