package zcy.applibrary.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import zcy.applibrary.R;
import zcy.applibrary.interfaces.RListener;

/**
 * NavSimpleAdapter
 * Created by ChengYan Zhang
 * on 2018/12/17
 */
public class NSAdapter extends NBAdapter implements RListener<Integer> {

    private List<SimpleItemData> data;
    private ViewPager viewPager;

    public NSAdapter() {
        super.setOnSelectedListener(this);
    }

    public NSAdapter addItem(@StringRes int nameId, @DrawableRes int iconDrawableRes, Fragment fragment){
        return addItem(new SimpleItemData(nameId , iconDrawableRes,fragment));
    }

    public NSAdapter addItem(SimpleItemData itemData){
        if (null != itemData){
            if (null == data) data = new ArrayList<>();
            data.add(itemData);
        }
        return this;
    }

    private FragmentManager fragmentManager;

    public NSAdapter setupWithViewPager(ViewPager viewPager , FragmentManager fragmentManager){
        this.viewPager = viewPager;
        this.fragmentManager = fragmentManager;
        return this;
    }


    private RListener<Integer> onSelectedListener;

    @Override
    public void setOnSelectedListener(RListener<Integer> onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    @Override
    public void notifyChangeData() {
        if (null != viewPager){
            List<FPI> pageInfos = new ArrayList<>();
            for (SimpleItemData itemData : data){
                pageInfos.add(new FPI<>(viewPager.getResources().getString(itemData.nameId),itemData.fragment,""));
            }
            SFPAdapter pagerAdapter = new SFPAdapter(fragmentManager, pageInfos);
            viewPager.setAdapter(pagerAdapter);
        }
        super.notifyChangeData();
    }

    public Fragment getFragment(int position){
        return data.get(position).fragment;
    }

    public NSAdapter(List<SimpleItemData> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return null == data ? 0 : data.size();
    }

    @Override
    public View getView(int position, ViewGroup viewGroup) {
        SimpleItemData simpleItemData = data.get(position);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_nav_item, viewGroup, false);
        ImageView iconIv = view.findViewById(R.id.icon_iv);
        TextView nameTv = view.findViewById(R.id.name_tv);
        if (-1 != simpleItemData.iconDrawableRes)iconIv.setImageResource(simpleItemData.iconDrawableRes);
        //有文字内容，显示，没有则隐藏
        if (-1 == simpleItemData.nameId){
            nameTv.setVisibility(View.GONE);
        }else {
            nameTv.setVisibility(View.VISIBLE);
            nameTv.setText(simpleItemData.nameId);
        }
        countViewList.put(simpleItemData.nameId , (TextView) view.findViewById(R.id.new_message_txt));
        return view;
    }

    private SparseArray<TextView> countViewList = new SparseArray<>();

    public void countChang(int id , int count){
        TextView textView = countViewList.get(id);
        if (null != textView){
            textView.setVisibility(0 == count ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onResult(Integer position) {
        if (null != viewPager){
            viewPager.setCurrentItem(position);
        }
        if (null != onSelectedListener) onSelectedListener.onResult(position);
    }

    public static class SimpleItemData{

        @StringRes int nameId;
        @DrawableRes int iconDrawableRes;
        Fragment fragment;

        public SimpleItemData(@StringRes int nameId, @DrawableRes int iconDrawableRes , Fragment fragment){
            this.nameId = nameId;
            this.iconDrawableRes = iconDrawableRes;
            this.fragment = fragment;
        }
    }

}
