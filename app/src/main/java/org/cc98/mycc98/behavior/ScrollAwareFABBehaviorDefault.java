package org.cc98.mycc98.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by pipi6 on 2017/10/20.
 * <p>
 * see:http://blog.csdn.net/a_zhon/article/details/52717415
 */

public class ScrollAwareFABBehaviorDefault extends FloatingActionButton.Behavior {


    public ScrollAwareFABBehaviorDefault(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                               final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        //logMess("dy consume:" + dyConsumed + " un:" + dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            //child.hide();

            //see :https://stackoverflow.com/questions/41153619/floating-action-button-not-visible-on-scrolling-after-updating-google-support
            child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                /**
                 * Called when a FloatingActionButton has been hidden
                 *
                 * @param fab the FloatingActionButton that was hidden.
                 */
                @Override
                public void onHidden(FloatingActionButton fab) {
                    super.onShown(fab);
                    fab.setVisibility(View.INVISIBLE);
                }
            });
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.show();
        }
    }

    private void logMess(String ms) {
        Log.i(getClass().getSimpleName(), ms);
    }
}
