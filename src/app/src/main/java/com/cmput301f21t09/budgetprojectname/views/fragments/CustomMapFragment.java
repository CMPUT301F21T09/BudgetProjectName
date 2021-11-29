package com.cmput301f21t09.budgetprojectname.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * This class allows us to ignore scrolling interactions when
 * dragging over the map fragment
 *
 * Source: https://stackoverflow.com/questions/30525066/how-to-set-google-map-fragment-inside-scroll-view
 */
public class CustomMapFragment extends SupportMapFragment {
    /**
     * Listner for touch events
     */
    private OnTouchListener mListener;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstance) {
        View layout = super.onCreateView(layoutInflater, viewGroup, savedInstance);
        TouchableWrapper frameLayout = new TouchableWrapper(getActivity());
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ((ViewGroup) layout).addView(frameLayout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return layout;
    }

    /**
     * Set the listener
     * @param listener The listener
     */
    public void setListener(OnTouchListener listener) {
        mListener = listener;
    }

    /**
     * Interface for on touch listener
     */
    public interface OnTouchListener {
        void onTouch();
    }

    /**
     * Class to intercept touch event
     */
    public class TouchableWrapper extends FrameLayout {
        /**
         * Constructor
         * @param context The app environment
         */
        public TouchableWrapper(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mListener.onTouch();
                    break;
                case MotionEvent.ACTION_UP:
                    mListener.onTouch();
                    break;
            }

            return super.dispatchTouchEvent(event);
        }
    }
}
