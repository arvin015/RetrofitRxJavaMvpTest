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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by arvin on 2017/5/27.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<String> testList;

    public HomeAdapter(Context context, ArrayList<String> testList) {
        this.context = context;
        this.testList = testList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item, null);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeViewHolder) {
            ((HomeViewHolder) holder).bindData(testList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return testList.size();
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
}
