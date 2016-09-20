package com.somebody.zoomimageview;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.somebody.zoomimageview.view.ZoomImageView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private int[] mImages = new int[]{
            R.drawable.girl_1,
            R.drawable.girl_2,
            R.drawable.girl_3,
            R.drawable.girl_4
    };

    private ImageView[] mImageViews = new ImageView[mImages.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vp);

        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView imageView = new ZoomImageView(getApplicationContext());
                imageView.setImageResource(mImages[position]);
                container.addView(imageView);
                mImageViews[position] = imageView;

                return imageView;
            }

            @Override public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews[position]);
            }


            @Override public int getCount() {
                return mImages.length;
            }

            @Override public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
    }
}
