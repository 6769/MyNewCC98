package org.cc98.mycc98.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsActivity;

import org.cc98.mycc98.R;

public class OpenlibsActivity extends LibsActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, OpenlibsActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        setIntent(new LibsBuilder().withLibraries("gson", "Butterknife")
                .withActivityTheme(R.style.AboutLibrariesTheme)
                .intent(this));

        super.onCreate(savedInstanceState);
    }
}
