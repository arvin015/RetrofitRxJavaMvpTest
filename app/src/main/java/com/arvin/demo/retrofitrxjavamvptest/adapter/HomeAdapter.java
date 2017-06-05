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
import com.arvin.demo.retrofitrxjavamvptest.bean.PictureInfo;
import com.arvin.demo.retrofitrxjavamvptest.widget.PictureRollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arvin on 2017/5/27.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_FOOTVIEW = 1;
    private final int VIEW_TYPE_TOP = 2;

    public static final int LOAD_MORE = 0;
    public static final int LOADING_MORE = 1;

    private int currentState = 0;

    private Context context;
    private ArrayList<String> testList;
    private ArrayList<PictureInfo> picList;

    public HomeAdapter(Context context, ArrayList<String> testList, ArrayList<PictureInfo> picList) {
        this.context = context;
        this.testList = testList;
        this.picList = picList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_ITEM) {
            viewHolder = new HomeViewHolder(LayoutInflater.from(context).inflate(R.layout.home_item, null));
        } else if (viewType == VIEW_TYPE_TOP) {
            viewHolder = new TopViewHolder(LayoutInflater.from(context).inflate(R.layout.main_topview, null));
        } else if (viewType == VIEW_TYPE_FOOTVIEW) {
            viewHolder = new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.footview, null));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            ((TopViewHolder) holder).bindData(picList);
        } else if (holder instanceof HomeViewHolder) {
            ((HomeViewHolder) holder).bindData(testList.get(position));
        } else if (holder instanceof FootViewHolder) {
            ((FootViewHolder) holder).update(currentState);
        }
    }

    @Override
    public int getItemCount() {
        return testList.size() + 2;
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

    public void changeFootViewState(int state) {
        currentState = state;
        notifyDataSetChanged();
    }

    class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.topViewPager)
        PictureRollView topViewPager;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(List<PictureInfo> pictureInfoList) {
            topViewPager.initData(pictureInfoList);
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

        public void bindData(final String value) {
            testText.setText(value);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "点击了" + value, Toast.LENGTH_SHORT).show();
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
