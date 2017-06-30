package com.arvin.demo.retrofitrxjavamvptest.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.arvin.demo.retrofitrxjavamvptest.R;
import com.arvin.demo.retrofitrxjavamvptest.entity.TopStoriesBean;
import com.arvin.demo.retrofitrxjavamvptest.utils.SizeUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by arvin on 2017/6/5.
 */

public class PictureRollView extends FrameLayout {

    private String TAG = "PictureRollView";

    private Context context;
    private List<ImageView> dotList;
    private List<View> viewList;
    private List<TopStoriesBean> topStoriesBeanList;
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
        dotLayout.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams dotParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dotParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        dotLayout.setLayoutParams(dotParams);

        addView(picViewPager);
        addView(dotLayout);
    }

    public void initData(List<TopStoriesBean> topStoriesBeanList) {
        this.topStoriesBeanList = topStoriesBeanList;

        viewList = new ArrayList<>();
        dotList = new ArrayList<>();
        dotLayout.removeAllViews();

        for (int i = 0; i < topStoriesBeanList.size(); i++) {

            TopStoriesBean topStoriesBean = topStoriesBeanList.get(i);

            ImageView imageView = new ImageView(context);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            imageView.setLayoutParams(lp);
            viewList.add(imageView);

            Glide.with(context)
                    .load(topStoriesBean.getImage())
                    .centerCrop()
                    .into(imageView);

            final ImageView dotImg = new ImageView(context);
            if (i == 0) {
                dotImg.setImageResource(R.drawable.dot_selected);
                selectedDotImg = dotImg;
            } else {
                dotImg.setImageResource(R.drawable.dot_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            dotImg.setLayoutParams(params);
            dotImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    picViewPager.setCurrentItem((int) dotImg.getTag());
                }
            });
            dotImg.setTag(i);
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
                selectedDotImg = dotImg;

                if (pictureRollViewListener != null) {
                    pictureRollViewListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        picViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        stopRoll();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        startAutoRoll();
                        break;
                    }
                }
                return false;
            }
        });

        startAutoRoll();
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

    private long rollDelay = 3000;
    private Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (picViewPager != null) {
                    int currentItem = picViewPager.getCurrentItem();
                    if (currentItem == picViewPager.getChildCount()) {
                        currentItem = 0;
                    } else {
                        currentItem += 1;
                    }
                    picViewPager.setCurrentItem(currentItem);
                }
            }
        }
    };

    /**
     * 设置广告滚动间隔时间
     *
     * @param rollDelay
     */
    public void setRollDelay(long rollDelay) {
        this.rollDelay = rollDelay;
    }

    /**
     * 开始滚动
     */
    public void startAutoRoll() {
        stopRoll();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(100);
            }
        }, rollDelay, rollDelay);
    }

    /**
     * 停止滚动
     */
    public void stopRoll() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    IPictureRollView pictureRollViewListener;

    public void setPictureRollViewListener(IPictureRollView pictureRollViewListener) {
        this.pictureRollViewListener = pictureRollViewListener;
    }

    public interface IPictureRollView {
        void onPageSelected(int position);
    }
}
