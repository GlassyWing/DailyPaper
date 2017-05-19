package com.example.manlier.dailypaper.modules.main.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.modules.about.AboutActivity;
import com.example.manlier.dailypaper.modules.main.presenter.MainPresenter;
import com.example.manlier.dailypaper.modules.main.presenter.MainPresenterImpl;
import com.example.manlier.dailypaper.modules.main.view.MainView;
import com.example.manlier.dailypaper.modules.news.widget.NewsDetailActivity;
import com.example.manlier.dailypaper.modules.news.widget.NewsFragment;
import com.example.manlier.dailypaper.modules.providers.RetrofitService;

public class MainActivity extends AppCompatActivity implements
        MainView {

    private DrawerLayout drawerLayout;     // 抽屉布局
    private ActionBarDrawerToggle drawerToggle; // ActionBar 中的Drawer开关
    private Toolbar toolbar;               // 工具条
    private NavigationView navigationView; // 左侧导航视图

    private MainPresenter mainPresenter;   // 主视图控制器

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        initView();             // 初始化主视图
        RetrofitService.init(); // 初始化网络服务
        mainPresenter = new MainPresenterImpl(this);
        switch2News();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // 设置工具栏

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // 设置抽屉开关
        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // 设置左侧抽屉中的菜单点击事件
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            mainPresenter.switchNavigation(menuItem.getItemId());
            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            return true;
        });
    }

    @Override
    public void switch2News() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_content, new NewsFragment())
                .commit();
        toolbar.setTitle(R.string.app_name);
    }

    @Override
    public void switch2About() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                switch2About();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getContext() {
        return context;
    }
}
