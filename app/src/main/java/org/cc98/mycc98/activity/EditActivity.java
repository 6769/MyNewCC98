package org.cc98.mycc98.activity;

import android.app.ProgressDialog;
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
import android.widget.ImageButton;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;
import org.cc98.mycc98.config.ForumConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
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
    @BindView(R.id.act_edit_ibtn_camera)
    ImageButton actEditIbtnCamera;
    @BindView(R.id.act_edit_ibtn_gallery)
    ImageButton actEditIbtnGallery;


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

    private EditReplyType postType;

    @BindView(R.id.act_edit_ibtn_send)
    ImageButton actEditIbtnSend;
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
            actEditIbtnSend.setEnabled(true);
            waitingDialog.dismiss();
            finish();
        }

        @Override
        public void onError(Throwable e) {
            actEditIbtnSend.setEnabled(true);
            waitingDialog.dismiss();
            mkToast(e.toString());
        }

        @Override
        public void onNext(String s) {
            logi(s);

        }
    };
    private CC98APIInterface iface;
    private ProgressDialog waitingDialog;

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
            postType = EditReplyType.NEWTOPIC;
        } else {

            postType = EditReplyType.REPLY;

            titleContainer.setVisibility(View.GONE);

            editContent.setFocusable(true);
            title = getString(R.string.editor_reply_topic);
        }

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (postType == EditReplyType.REPLY && !quoted.isEmpty()) {
            editContent.setText(quoted);
        }

        editContent.addTextChangedListener(new EditTextWatcher());


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
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


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isMd = preferences.getBoolean(getString(R.string.pref_switchubb_md_key), false);
        int contentType = isMd ? 1 : 0;


        NewPostInfo newPostInfo;
        Observable<String> call = null;

        if (inputcontent.isEmpty()) {
            return;
        }


        switch (postType) {
            case REPLY:
                newPostInfo = new NewPostInfo("", inputcontent, contentType);
                call = iface.postReplyTopic(tid, newPostInfo);
                break;

            case NEWTOPIC:
                if (inputtitle.isEmpty()) {
                    return;
                }
                newPostInfo = new NewPostInfo(inputtitle, inputcontent, contentType);
                call = iface.postTopicBoard(bid, newPostInfo);
                break;
            default:
                break;
        }


        if (call != null) {

            waitingDialog = genADialog(getString(R.string.dialog_edit_sendpost_title),
                    getString(R.string.dialog_edit_sendpost_msg));
            waitingDialog.show();
            actEditIbtnSend.setEnabled(false);
            //important in case user repeatedly click;
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(postObserver);
        }


    }

    @OnLongClick(R.id.act_edit_ibtn_gallery)
    protected boolean pickupDocFiles(){
        FilePickerBuilder.getInstance()
                .setMaxCount(1)
                .setSelectedFiles(null)
                .setActivityTheme(R.style.AppTheme)
                .pickFile(this);
        return true;
    }


    private List<String> mCurrentFiles =new ArrayList<>();
    private File mCurrentPhotoFile;



    @OnClick(R.id.act_edit_ibtn_gallery)
    protected void takePhotoFromGallery(){

    }

    @OnClick(R.id.act_edit_ibtn_camera)
    protected void takePhotoFromCamera(){
        /*File PHOTO_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.editor_photo_name_template), Locale.CHINA);
        mCurrentPhotoFile = new File(PHOTO_DIR, dateFormat.format(date));

        try{
            boolean ret= mCurrentPhotoFile.createNewFile();
        }catch (IOException e){
            loge(e,"Take photo failed");
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(mCurrentPhotoFile));
        startActivityForResult(intent, EditRetCode.CAM.getValue());*/

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EditRetCode[] retList = EditRetCode.values();
        EditRetCode reqCode=EditRetCode.FAIL;
        for(EditRetCode i:retList){
            if (requestCode==i.getValue()){
                reqCode=i;
                break;
            }
        }



        switch (reqCode){
            case CAM:
                logi(mCurrentPhotoFile.toString());
                break;
            case GALLERY:

                break;
            case FILE:
                mCurrentFiles.clear();
                mCurrentFiles.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                break;
            case FAIL:
                break;
        }

    }

    protected ProgressDialog genADialog(String title, String msg) {
        ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setTitle(title);
        waitingDialog.setMessage(msg);
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);

        return waitingDialog;
    }

    enum EditReplyType {
        NEWTOPIC, REPLY
    }



    enum EditRetCode {
        CAM(1001),
        GALLERY(FilePickerConst.REQUEST_CODE_PHOTO),
        FILE(FilePickerConst.REQUEST_CODE_DOC),
        FAIL(1);

        private EditRetCode(int v){
            this.value=v;
        }

        public int value;
        public int getValue(){
            return value;
        }


    }


    protected class EditTextWatcher implements TextWatcher {
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

