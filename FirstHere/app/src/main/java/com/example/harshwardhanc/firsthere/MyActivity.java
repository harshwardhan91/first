package com.example.harshwardhanc.firsthere;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MyActivity extends FragmentActivity implements CustomViewPagerFragment.FragmentScrolledInterface {
    LinearLayout mLinearLayout;
    ViewPager mViewPager;
    CustomPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mLinearLayout= (LinearLayout) findViewById(R.id.top_image);
        mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    private Fragment makeFragments(int n){
        CustomViewPagerFragment cvp = new CustomViewPagerFragment(this);
        Bundle bundle = new Bundle();
        bundle.putInt(CustomViewPagerFragment.NUMBER_LAYOUT,n);
        cvp.setArguments(bundle);
        return cvp;
    }

    @Override
    public void pageHasScrolled() {
      //  ExpandAnimation anim= new ExpandAnimation(mViewPager.getHeight(),mViewPager.getHeight()+ 30);
        Animation a= AnimationUtils.loadAnimation(this, R.anim.rolling_up);

        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                mViewPager.setTranslationY(-mLinearLayout.getHeight());
//                    mViewPager.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.increase_size));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("CustomViewPagerFragment----->","animation end");
                mLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        MyCustomAnimation c = new MyCustomAnimation(mLinearLayout,2000,1);
        int height=c.getHeight();
        mLinearLayout.startAnimation(c);

//         mLinearLayout.startAnimation(a);
      //  anim.setDuration(500);

       // mViewPager.startAnimation(anim);
//
//        mViewPager.setTranslationY(-mLinearLayout.getHeight());
//        mViewPager.setBottom(getWindowManager().getDefaultDisplay().getHeight());
    }
    private class ExpandAnimation extends Animation {
        private final int mStartHeight;
        private final int mDeltaHeight;

        public ExpandAnimation(int startHeight, int endHeight) {
            mStartHeight = startHeight;
            mDeltaHeight = endHeight - startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            android.view.ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
            lp.height = (int) (mStartHeight + mDeltaHeight * interpolatedTime);
            mViewPager.setLayoutParams(lp);

        }



        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    private class CustomPagerAdapter extends FragmentStatePagerAdapter{


        CustomPagerAdapter(FragmentManager fm){
            super(fm);
        }
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int i) {
            switch(i){
                case 0:
                        return makeFragments(1);
                case 1:
                    return makeFragments(2);
                case 2:
                    return makeFragments(3);
                default: break;
            }
            return makeFragments(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyCustomAnimation extends Animation {

        public final static int COLLAPSE = 1;
        public final static int EXPAND = 0;

        private View mView;
        private int mEndHeight;
        private int mType;
        private RelativeLayout.LayoutParams mLayoutParams;

        public MyCustomAnimation(View view, int duration, int type) {

            setDuration(duration);
            mView = view;
            mEndHeight = mView.getHeight();
            mLayoutParams = ((RelativeLayout.LayoutParams) view.getLayoutParams());
            mType = type;
            if(mType == EXPAND) {
                mLayoutParams.height = 0;
            } else {
                mLayoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            }
            view.setVisibility(View.VISIBLE);
        }

        public int getHeight(){
            return mView.getHeight();
        }

        public void setHeight(int height){
            mEndHeight = height;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {

            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                if(mType == EXPAND) {
                    mLayoutParams.height =  (int)(mEndHeight * interpolatedTime);
                } else {
                    mLayoutParams.height = (int) (mEndHeight * (1 - interpolatedTime));
                }
                mView.requestLayout();
            } else {
                if(mType == EXPAND) {
                    mLayoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    mView.requestLayout();
                }else{
                    mView.setVisibility(View.GONE);
                }
            }
        }
    }
}
