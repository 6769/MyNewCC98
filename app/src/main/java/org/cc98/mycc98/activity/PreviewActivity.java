package org.cc98.mycc98.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yydcdut.rxmarkdown.RxMDTextView;
import com.yydcdut.rxmarkdown.RxMarkdown;
import com.yydcdut.rxmarkdown.syntax.text.TextFactory;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PreviewActivity extends BaseSwipeBackActivity {
    public static final String MARKDOWN_TEXT = "MARKDOWN_TEXT";


    @BindView(R.id.act_preview_rx_textview)
    RxMDTextView textView;

    @BindView(R.id.act_preview_toolbar)
    Toolbar toolbar;

    public static void startActivity(Context context, String content) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(MARKDOWN_TEXT, content);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String content;
        Intent intent;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        intent = getIntent();
        content = intent.getStringExtra(MARKDOWN_TEXT);
        if (content.isEmpty()) {
            Snackbar.make(textView, "No text", Snackbar.LENGTH_SHORT).show();
            return;
        }
        textView.setText(content);
        RxMarkdown.with(content, this)
                .factory(TextFactory.create())
                .intoObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        textView.setText(charSequence, TextView.BufferType.SPANNABLE);
                    }
                });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.act_preview_rx_textview)
    protected void touchExitMe(View view) {
        finish();
    }
}
