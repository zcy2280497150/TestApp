package app.zcy.testapp;

import zcy.applibrary.adapter.NSAdapter;
import zcy.applibrary.fragment.TestFragment;
import zcy.applibrary.interfaces.RListener;

public class MainActivity extends zcy.applibrary.MainActivity {

    @Override
    protected void initViews() {
        viewPager = findViewById(zcy.applibrary.R.id.view_pager);
        viewPager.setSlide(false);
        viewPager.setOffscreenPageLimit(4);

        NSAdapter navAdapter = new NSAdapter();
        navAdapter.addItem(zcy.applibrary.R.string.item_nav_test, zcy.applibrary.R.drawable.selector_nav_icon_test,new TestFragment())
                .addItem(zcy.applibrary.R.string.item_nav_test, zcy.applibrary.R.drawable.selector_nav_icon_test , new TestFragment())
                .addItem(zcy.applibrary.R.string.item_nav_test, zcy.applibrary.R.drawable.selector_nav_icon_test , new TestFragment())
                .addItem(zcy.applibrary.R.string.item_nav_test, zcy.applibrary.R.drawable.selector_nav_icon_test , new TestFragment())
                .addItem(zcy.applibrary.R.string.item_nav_test, zcy.applibrary.R.drawable.selector_nav_icon_test , new TestFragment())
                .setupWithViewPager(viewPager,getSupportFragmentManager())
                .setOnSelectedListener(new RListener<Integer>() {
                    @Override
                    public void onResult(Integer integer) {

                    }
                });
        navGroup = findViewById(zcy.applibrary.R.id.nav_group);
        navGroup.setAdapter(navAdapter);
    }
}
