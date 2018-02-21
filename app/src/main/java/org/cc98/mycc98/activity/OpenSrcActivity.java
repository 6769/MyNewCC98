package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsActivity;

import org.cc98.mycc98.R;

public class OpenSrcActivity extends LibsActivity {

    public static void startActivity(Context context){
        Intent intent= new Intent(context,OpenSrcActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setIntent(new LibsBuilder().withLibraries("activeandroid")
                .withFields(R.string.class.getFields())
                .withActivityTheme(R.style.AboutLibrariesTheme)
                .intent(this));
        super.onCreate(savedInstanceState);
    }
}
