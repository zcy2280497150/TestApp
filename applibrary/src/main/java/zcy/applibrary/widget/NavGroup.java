package zcy.applibrary.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import zcy.applibrary.adapter.NBAdapter;

/**
 * 导航布局
 * Created by ChengYan Zhang
 * on 2018/12/14
 */
public class NavGroup extends TabLayout {

    private NBAdapter adapter;

    public NavGroup(Context context) {
        super(context);
    }

    public NavGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(NBAdapter adapter) {
        this.adapter = adapter;
        adapter.bindNavGroup(this);
        adapter.notifyChangeData();
    }

    public void selectTab(int position){
        Tab tabAt = getTabAt(position);
        if (null != tabAt){
            Class<TabLayout> aClass = TabLayout.class;
            try {
                Method selectTab = aClass.getDeclaredMethod("selectTab",Tab.class);
                selectTab.setAccessible(true);
                selectTab.invoke(this ,tabAt);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }



}
