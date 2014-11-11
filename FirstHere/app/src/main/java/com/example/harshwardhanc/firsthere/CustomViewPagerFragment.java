package com.example.harshwardhanc.firsthere;

/**
 * Created by harshwardhan.c on 20/08/14.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class CustomViewPagerFragment extends Fragment{

    ScrollView mScrollView;
    LinearLayout mLinearLayout;
    Context mContext;
    int n;
    public static String NUMBER_LAYOUT = "number_layout";
    FragmentScrolledInterface fsi;
    private static float pcurrentY;
    private final static float mDelta=0;

    CustomViewPagerFragment(FragmentScrolledInterface fsi){
        this.fsi=fsi;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        mScrollView = (ScrollView)view.findViewById(R.id.scroll_view);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);
        Bundle bundle = getArguments();
        n= bundle.getInt(NUMBER_LAYOUT);
        generateLayouts(n);

        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_SCROLL:
                    case MotionEvent.ACTION_MOVE:

                        Log.d("CustomViewPagerFragment----->", "event.getY()  " + event.getY() );
                        Log.d("CustomViewPagerFragment----->", "pcurrentY    " + pcurrentY );
                        Log.d("CustomViewPagerFragment----->", event.getY()-pcurrentY + "" );
                        if(event.getY()-pcurrentY>mDelta) {
                            Log.d("CustomViewPagerFragment----->", "Scrolled" );
                            fsi.pageHasScrolled();

                        }
                        pcurrentY=event.getY();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }

        });
        return view;
    }

    private void generateLayouts(int n){
        for (int i = 0; i<n; i++){
            LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(60,160));
            linearLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_launcher) );
            mLinearLayout.addView(linearLayout);
        }
    }

    public interface FragmentScrolledInterface{
        public void pageHasScrolled();
    }
}
