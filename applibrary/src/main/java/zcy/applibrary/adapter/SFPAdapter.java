package zcy.applibrary.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alibaba.fastjson.JSON;

import java.util.List;

/*
* SimpleFragmentPagerAdapter
* */
public class SFPAdapter extends FragmentPagerAdapter {

    private List<FPI> pageInfos;

    public SFPAdapter(FragmentManager fm , List<FPI> pageInfos) {
        super(fm);
        this.pageInfos = pageInfos;
    }

    @Override
    public Fragment getItem(int i) {
        FPI pageInfo = pageInfos.get(i);
        Fragment fragment = pageInfo.getFragment();
        Object t = pageInfo.getT();
        if (null != t){
            Bundle bundle = new Bundle();
            if (t instanceof String) {
                bundle.putString("data", (String) t);
            } else if (t instanceof Integer) {
                bundle.putInt("data", (Integer) t);
            } else if (t instanceof Boolean) {
                bundle.putBoolean("data", (Boolean) t);
            } else if (t instanceof Float) {
                bundle.putFloat("data", (Float) t);
            } else if (t instanceof Long) {
                bundle.putLong("data", (Long) t);
            } else {
                bundle.putString("data", JSON.toJSONString(t));
            }
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return null == pageInfos ? 0 : pageInfos.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageInfos.get(position).getTitle();
    }

}
