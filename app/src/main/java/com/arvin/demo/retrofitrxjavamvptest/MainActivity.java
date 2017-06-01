package com.arvin.demo.retrofitrxjavamvptest;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arvin.demo.retrofitrxjavamvptest.Fragment.BlogFragment;
import com.arvin.demo.retrofitrxjavamvptest.Fragment.HomeFragment;
import com.arvin.demo.retrofitrxjavamvptest.Fragment.PhotoFragment;
import com.arvin.demo.retrofitrxjavamvptest.adapter.MainFragmentAdapter;
import com.arvin.demo.retrofitrxjavamvptest.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    String[] titles = new String[]{"主页", "微博", "相册"};
    List<BaseFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolBar();

        initTabView();
    }

    private void initToolBar() {
        toolBar.setTitle("李子");
        setSupportActionBar(toolBar);

        toolBar.setNavigationIcon(R.mipmap.ic_launcher);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "导航", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTabView() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new BlogFragment());
        fragments.add(new PhotoFragment());

        viewPager.setAdapter(new MainFragmentAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.search: {
                break;
            }
            case R.id.about: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
