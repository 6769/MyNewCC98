package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseSwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends BaseSwipeBackActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, EditActivity.class);
        context.startActivity(intent);
    }


    @BindView(R.id.act_edit_content)
    EditText editContent;

    @BindView(R.id.act_edit_title)
    EditText editTitle;

    @BindView(R.id.act_edit_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    protected void onResume() {
        super.onResume();
        //load Config here

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.act_edit_ibtn_preview)
    protected void editContentPreview(View view) {
        String userinput = editContent.getText().toString();
        PreviewActivity.startActivity(this, userinput);

    }
}
