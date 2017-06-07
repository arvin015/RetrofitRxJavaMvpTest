package com.arvin.demo.retrofitrxjavamvptest.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arvin.demo.retrofitrxjavamvptest.R;
import com.arvin.demo.retrofitrxjavamvptest.entity.LatestNews;
import com.arvin.demo.retrofitrxjavamvptest.entity.StoriesBean;
import com.arvin.demo.retrofitrxjavamvptest.entity.TopStoriesBean;
import com.arvin.demo.retrofitrxjavamvptest.widget.PictureRollView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arvin on 2017/5/27.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "HomeAdapter";

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_FOOTVIEW = 1;
    private final int VIEW_TYPE_TOP = 2;

    public static final int LOAD_MORE = 0;
    public static final int LOADING_MORE = 1;

    private int currentState = 0;

    private Context context;
    private LatestNews latestNews;

    private TopViewHolder topViewHolder;

    public HomeAdapter(Context context, LatestNews latestNews) {
        this.context = context;
        this.latestNews = latestNews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_ITEM) {
            viewHolder = new HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item, null));
        } else if (viewType == VIEW_TYPE_TOP) {
            viewHolder = new TopViewHolder(LayoutInflater.from(context).inflate(R.layout.main_topview, null));
            topViewHolder = (TopViewHolder) viewHolder;
        } else if (viewType == VIEW_TYPE_FOOTVIEW) {
            viewHolder = new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.footview, null));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (latestNews == null) {
            return;
        }

        if (holder instanceof TopViewHolder) {
            ((TopViewHolder) holder).bindData(latestNews.getTop_stories());
        } else if (holder instanceof HomeViewHolder) {
            ((HomeViewHolder) holder).bindData(latestNews.getStories().get(position));
        } else if (holder instanceof FootViewHolder) {
            ((FootViewHolder) holder).update(currentState);
        }
    }

    @Override
    public int getItemCount() {
        return latestNews == null ? 0 : latestNews.getStories().size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_TOP;
        } else if (position == getItemCount() - 1) {
            return VIEW_TYPE_FOOTVIEW;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    /**
     * 设置最新消息
     *
     * @param latestNews
     */
    public void setLatestNews(LatestNews latestNews) {
        this.latestNews = latestNews;
    }

    /**
     * 改变加载更多状态
     *
     * @param state
     */
    public void changeFootViewState(int state) {
        currentState = state;
        notifyDataSetChanged();
    }

    /**
     * 设置广告是否需要自动切换
     *
     * @param rollEnabled
     */
    public void setAutoRollEnabled(boolean rollEnabled) {
        if (topViewHolder != null) {
            topViewHolder.setAutoRollEnabled(rollEnabled);
        }
    }

    class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.topViewPager)
        PictureRollView topViewPager;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * 绑定数据
         *
         * @param topStoriesBeanList
         */
        public void bindData(List<TopStoriesBean> topStoriesBeanList) {
            topViewPager.initData(topStoriesBeanList);
        }

        /**
         * 设置广告是否需要自动切换
         *
         * @param rollEnabled
         */
        public void setAutoRollEnabled(boolean rollEnabled) {
            if (topViewPager != null) {
                if (rollEnabled) {
                    topViewPager.startAutoRoll();
                } else {
                    topViewPager.stopRoll();
                }
            }
        }
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.testText)
        TextView testText;

        private View itemView;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.itemView = itemView;

            //没明白为啥
            LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(lp);
        }

        public void bindData(final StoriesBean storiesBean) {
            testText.setText(storiesBean.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "点击了" + storiesBean.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.loadMoreText)
        TextView loadMoreText;

        public FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(lp);
        }

        public void update(int state) {
            if (state == LOAD_MORE) {
                loadMoreText.setText("加载更多...");
            } else if (state == LOADING_MORE) {
                loadMoreText.setText("正在加载更多...");
            }
        }
    }
}
