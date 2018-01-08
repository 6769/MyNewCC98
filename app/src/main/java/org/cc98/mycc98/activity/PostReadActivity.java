package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.cc98.mycc98.R;
import org.cc98.mycc98.activity.base.BaseActivity;

public class PostReadActivity extends BaseActivity {
    
    public static final String TOPIC_ID="TOPIC_ID";
    
    //外部实现的
    public static interface getPostInfo{
        String getPostInfo(int id,int start,int size);
        //json string provided;
    }
    
    
    //app调用看贴入口
    public static void startActivity(Context context, int topicID) {
        Intent intent = new Intent(context, PostReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TOPIC_ID, topicID);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_read);
        
        
        
    }
}
