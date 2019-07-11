package zcy.applibrary.adapter;

import android.support.v4.app.Fragment;

/*
* FragmentPageInfo
* */
public class FPI<T> {

    String title;
    Fragment fragment;
    T t;

    public FPI(String title, Fragment fragment, T t) {
        this.title = title;
        this.fragment = fragment;
        this.t = t;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
