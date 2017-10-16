package org.cc98.mycc98.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import win.pipi.api.network.CC98APIInterface;

/**
 * Created by pipi6 on 2017/8/4.
 */

public class BaseFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    protected void mkToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }
    protected void mkToastL(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
