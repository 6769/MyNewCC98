package org.cc98.mycc98.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import org.cc98.mycc98.MainApplication;
import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseImagePickActivity;
import org.cc98.mycc98.config.ForumConfig;
import org.cc98.mycc98.utility.DialogStore;
import org.cc98.mycc98.utility.ImageProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import win.pipi.api.data.NewPostInfo;
import win.pipi.api.network.CC98APIInterface;
import win.pipi.swiftemotionboard.controller.EmotionKeyboard;
import win.pipi.swiftemotionboard.fragment.Communicator;
import win.pipi.swiftemotionboard.fragment.EmotionMainFragment;

public class EditActivity extends BaseImagePickActivity implements Communicator {
    public static final String TAG = "EditActivity";
    public static final String TOPIC_ID = "TOPIC_ID";
    public static final String POST_REFFER = "POST_REFFER";
    public static final String TARGET_USER = "TARGET_USER";
    public static final String BOARD_ID = "BOARD_ID";
    @BindView(R.id.act_edit_ibtn_camera)
    ImageButton actEditIbtnCamera;
    @BindView(R.id.act_edit_ibtn_gallery)
    ImageButton actEditIbtnGallery;
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
    @BindView(R.id.act_edit_ibtn_facefelling)
    ImageButton actEditIbtnFacefelling;
    @BindView(R.id.activity_post_write_scroll_view)
    ScrollView activityPostWriteScrollView;
    @BindView(R.id.emotion_position_upper_linearlayout)
    LinearLayout emotionPositionUpperLinearlayout;

    private EditReplyType postType;
    private int bid, tid;
    private String quoted;
    private boolean isMd;
    private String uploadFormation;

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

    private Observer<List<String>> uploadObserver = new Observer<List<String>>() {
        @Override
        public void onCompleted() {
            waitingDialog.dismiss();
        }

        @Override
        public void onError(Throwable e) {
            waitingDialog.dismiss();
            mkToast(e.toString());
            loge(e, "upload Error");
        }

        @Override
        public void onNext(List<String> strings) {

            StringBuilder cacheString = new StringBuilder(editContent.getText());
            for (String i : strings) {
                if (i.isEmpty())
                    continue;
                String aInsertText = String.format(uploadFormation, i);
                cacheString.append(aInsertText);
            }
            editContent.setText(cacheString);
        }
    };

    private CC98APIInterface iface;
    private ProgressDialog waitingDialog;


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
        initEmotionKeyBoard();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        isMd = preferences.getBoolean(getString(R.string.pref_switchubb_md_key), false);
        uploadFormation = isMd ? getString(R.string.editor_upload_md_urltemplate) : getString(R.string.editor_upload_ubb_urltemplate);
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
        int contentType = isMd ? 1 : 0;


        NewPostInfo newPostInfo;
        Observable<String> call = null;

        if (inputcontent.isEmpty()) {
            mkToast(getString(R.string.editor_text_content_null));
            return;
        }


        switch (postType) {
            case REPLY:
                newPostInfo = new NewPostInfo("", inputcontent, contentType);
                call = iface.postReplyTopic(tid, newPostInfo);
                break;

            case NEWTOPIC:
                if (inputtitle.isEmpty()) {
                    mkToast(getString(R.string.editor_text_title_null));
                    return;
                }
                newPostInfo = new NewPostInfo(inputtitle, inputcontent, contentType);
                call = iface.postTopicBoard(bid, newPostInfo);
                break;
        }


        if (call != null) {

            initDialog();
            actEditIbtnSend.setEnabled(false);
            //important in case user repeatedly click;
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(postObserver);
        }


    }

    @OnLongClick(R.id.act_edit_ibtn_gallery)
    protected boolean pickupDocFiles() {
        FilePickerBuilder.getInstance()
                .setMaxCount(1)
                .setSelectedFiles(null)
                .setActivityTheme(R.style.AppTheme)
                .pickFile(this);
        return true;
        //return true: give user a touch feedback;
    }


    private List<String> mCurrentFiles = new ArrayList<>();
    private File mCurrentFile;


    @OnClick(R.id.act_edit_ibtn_gallery)
    protected void takePhotoFromGallery() {
        //constrain to Pick One file;
        takePhoto.onPickFromGallery();
    }

    @OnClick(R.id.act_edit_ibtn_camera)
    protected void takePhotoFromCamera() {

        mCurrentFile = ImageProcess.getDCIMNewImageFile(this);

        /*try{
            boolean ret= mCurrentFile.createNewFile();
        }catch (IOException e){
            loge(e,"Take photo failed");
            return;
        }*/
        Uri current = Uri.fromFile(mCurrentFile);
        takePhoto.onPickFromCapture(current);

    }

    @Override
    public void takeSuccess(TResult result) {
        TImage ret = result.getImage();
        //never use takephoto's image compress function. Too slow.
        mCurrentFile = new File(ret.getOriginalPath());
        uploadImageFile(mCurrentFile);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        logi(msg);
        mkToast(getString(R.string.editor_photo_error));
    }

    @Override
    public void takeCancel() {
        mkToast(getString(R.string.editor_photo_select_canceled));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        EditRetCode reqCode = EditRetCode.find(requestCode);
        switch (reqCode) {
            case FILE:
                List<String> tmpList = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS);
                if (tmpList != null) {
                    mCurrentFiles.clear();
                    mCurrentFiles.addAll(tmpList);
                    mCurrentFile = new File(mCurrentFiles.get(0));
                    uploadNormalFile(mCurrentFile);
                }
                break;
            case FAIL:
                break;
        }

    }

    private void uploadNormalFile(File file) {
        if (file.length() > ForumConfig.getUploadMaxSize()) {
            mkToast(getString(R.string.editor_upload_size_overflow));
            return;
        }
        initDialog();
        createFileUpObservable(file).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadObserver);
    }

    private void uploadImageFile(File file) {
        /*if(file.length()<ForumConfig.getUploadMaxSize()){
            createFileUpObservable(file).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(uploadObserver);
        }else {



        }*/
        initDialog();
        Observable.just(file)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(new Func1<File, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(File file) {
                        try {
                            List<File> files = Luban.with(EditActivity.this)
                                    .load(file).ignoreBy(1024).get();
                            return createFileUpObservable(files.get(0));
                        } catch (Exception e) {
                            return Observable.error(e);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadObserver);


    }


    private EmotionKeyboard emotionKeyboard;

    @Override
    public void setText(String clicked) {
        StringBuilder builder=new StringBuilder(editContent.getText());
        builder.append(clicked);
        editContent.setText(builder);
    }

    private void initEmotionKeyBoard() {
        FragmentManager  manager=getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        EmotionMainFragment fragment = EmotionMainFragment.newInstance();
        fragment.setupTextEmotionBlocks(
                ImageProcess.loadEmotionsFromAssets(this,getString(R.string.emotion_group_folder)),
                this);

        transaction.replace(R.id.emotion_position, fragment);

        transaction.commit();


        emotionKeyboard = EmotionKeyboard.with(this)
                .setEmotionView(emotionPositionUpperLinearlayout)
                .bindToContent(activityPostWriteScrollView)
                .bindToEmotionButton(actEditIbtnFacefelling)
                .bindToEditText(editContent).build();

    }


    protected Observable<List<String>> createFileUpObservable(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(ForumConfig.FILEUPLOAD_HEAD,
                file.getName(), requestFile);
        return iface.uploadFile(body);
    }

    private ProgressDialog initDialog() {
        waitingDialog = DialogStore.genProcessDialog(this, getString(R.string.dialog_edit_sendpost_title),
                getString(R.string.dialog_edit_sendpost_msg));
        waitingDialog.show();
        return waitingDialog;
    }

    enum EditReplyType {
        NEWTOPIC, REPLY
    }

    enum EditRetCode {
        CAM(1001),
        GALLERY(FilePickerConst.REQUEST_CODE_PHOTO),
        FILE(FilePickerConst.REQUEST_CODE_DOC),
        FAIL(2),
        NOTFOUD(1);

        private EditRetCode(int v) {
            this.value = v;
        }

        public int value;

        public int getValue() {
            return value;
        }

        public static EditRetCode find(int value) {
            for (EditRetCode i : EditRetCode.values()) {
                if (value == i.getValue()) {
                    return i;
                }
            }
            return NOTFOUD;
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

            int size = s.length();
            if (size == 0) {
                //mkToast(getString(R.string.editor_text_content_null));
                //actEditIbtnSend.setEnabled(false);
                actEditIbtnSend.setImageResource(R.drawable.ic_more_black_36dp);

            } else if (size >= ForumConfig.getInputtextMax()) {
                mkToast(getString(R.string.editor_text_size_overflow));
                actEditIbtnSend.setEnabled(false);
                actEditIbtnSend.setImageResource(R.drawable.ic_more_black_36dp);

            } else {
                actEditIbtnSend.setEnabled(true);
                actEditIbtnSend.setImageResource(R.drawable.ic_send_black_36dp);
            }
        }
    }

}

