package com.arvin.demo.retrofitrxjavamvptest.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.arvin.demo.retrofitrxjavamvptest.R;
import com.arvin.demo.retrofitrxjavamvptest.bean.PictureInfo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvin on 2017/6/5.
 */

public class PictureRollView extends RelativeLayout {

    private Context context;
    private List<ImageView> dotList;
    private List<View> viewList;
    private List<PictureInfo> pictureList;
    private ViewPager picViewPager;
    private LinearLayout dotLayout;
    private ImageView selectedDotImg;

    public PictureRollView(Context context) {
        super(context, null);
    }

    public PictureRollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        initView();

    }

    private void initView() {

        picViewPager = new ViewPager(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        picViewPager.setLayoutParams(lp);

        dotLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams dotParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dotParams.addRule(CENTER_HORIZONTAL | ALIGN_PARENT_BOTTOM);
        dotLayout.setLayoutParams(dotParams);

        addView(picViewPager);
        addView(dotLayout);
    }

    public void initData(List<PictureInfo> pictureList) {
        this.pictureList = pictureList;

        viewList = new ArrayList<>();

        for (int i = 0; i < pictureList.size(); i++) {

            PictureInfo pictureInfo = pictureList.get(i);

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(CENTER_IN_PARENT);
            imageView.setLayoutParams(lp);
            viewList.add(imageView);

            Glide.with(context)
                    .load(pictureInfo.getPath())
                    .into(imageView);

            ImageView dotImg = new ImageView(context);
            if (i == 0) {
                dotImg.setImageResource(R.drawable.dot_selected);
                selectedDotImg = dotImg;
            } else {
                dotImg.setImageResource(R.drawable.dot_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                params.leftMargin = 10;
            }
            dotImg.setLayoutParams(params);
            dotList.add(dotImg);
            dotLayout.addView(dotImg);

        }

        picViewPager.setAdapter(new PicPagerAdapter());
        picViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ImageView dotImg = dotList.get(position);
                dotImg.setImageResource(R.drawable.dot_selected);
                selectedDotImg.setImageResource(R.drawable.dot_normal);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class PicPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList == null ? 0 : viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }
}
