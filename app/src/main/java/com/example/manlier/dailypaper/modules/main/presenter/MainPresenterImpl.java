package com.example.manlier.dailypaper.modules.main.presenter;

import com.example.manlier.dailypaper.R;
import com.example.manlier.dailypaper.modules.main.view.MainView;

/**
 * Created by manlier on 2017/5/10.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_news:
                mainView.switch2News();
                break;
            case R.id.navigation_item_about:
                mainView.switch2About();
                break;
            default:
                mainView.switch2News();
                break;
        }
    }
}
