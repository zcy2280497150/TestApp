package zcy.applibrary;
import android.os.Bundle;

import zcy.applibrary.activity.BaseActivity;
import zcy.applibrary.adapter.NSAdapter;
import zcy.applibrary.fragment.TestFragment;
import zcy.applibrary.interfaces.RListener;
import zcy.applibrary.widget.NavGroup;
import zcy.applibrary.widget.ViewPagerSlide;

public class MainActivity extends BaseActivity {

    protected NavGroup navGroup;
    protected ViewPagerSlide viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_library);
        initViews();
    }

    protected void initViews() {
        viewPager = findViewById(R.id.view_pager);
        viewPager.setSlide(false);
        viewPager.setOffscreenPageLimit(4);

        NSAdapter navAdapter = new NSAdapter();
        navAdapter.addItem(R.string.item_nav_test,R.drawable.selector_nav_icon_test,new TestFragment())
                .addItem(R.string.item_nav_test,R.drawable.selector_nav_icon_test , new TestFragment())
                .addItem(R.string.item_nav_test,R.drawable.selector_nav_icon_test , new TestFragment())
                .setupWithViewPager(viewPager,getSupportFragmentManager())
                .setOnSelectedListener(new RListener<Integer>() {
                    @Override
                    public void onResult(Integer integer) {

                    }
                });
        navGroup = findViewById(R.id.nav_group);
        navGroup.setAdapter(navAdapter);
    }

}
