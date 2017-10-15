package org.cc98.mycc98.fragment;

import rx.Observable;

/**
 * Created by pipi6 on 2017/10/15.
 */

public interface SpecificCallFactory {
    //Observable<?> getCall();
    Observable<?> getCall(int from,int to);

}
