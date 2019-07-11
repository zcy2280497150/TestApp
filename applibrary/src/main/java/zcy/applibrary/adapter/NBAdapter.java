package zcy.applibrary.adapter;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;

import zcy.applibrary.interfaces.RListener;
import zcy.applibrary.widget.NavGroup;

/**
 * NavBaseAdapter
 * Created by ChengYan Zhang
 * on 2018/12/17
 */
public abstract class NBAdapter {

    private NavGroup navGroup;

    public abstract int getCount();

    public abstract View getView(int position, ViewGroup viewGroup);

    private RListener<Integer> onSelectedListener;

    public void setOnSelectedListener(RListener<Integer> onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public void notifyChangeData(){
        if (null == navGroup)return;
        navGroup.removeAllTabs();
        int count = getCount();
        for (int i = 0 ; i < count ; i++ ){
            TabLayout.Tab tab = navGroup.newTab();
            navGroup.addTab(tab,i);
            tab.setCustomView(getView(i,navGroup));
        }
        if (null != onSelectedListener)onSelectedListener.onResult(navGroup.getSelectedTabPosition());
    }

    public void bindNavGroup(NavGroup navGroup){
        this.navGroup = navGroup;
        navGroup.addOnTabSelectedListener(onTabSelectedListener);
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (null != onSelectedListener)onSelectedListener.onResult(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

}
