package zcy.applibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import zcy.applibrary.R;

/**
 * Created by lgs on 2018/10/26.
 */
public class BannerViewPager extends FrameLayout {

    private static final int MSG_WHAT = 0;
    private int delayMillis;
    private ViewPager viewPager;

    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_CROP;

    private List<BannerItemBean> mData;
    private int itemCount;
    private LinearLayout layoutIndicators;   /*指示器容器*/
    private TextView tvTitle; /*Banner标题*/
    private boolean autoPlay;   /*播放开关*/

    private OnBannerItemClickListener bannerListener;
    private BannerImageLoader imageLoader; /*图片加载回调*/
    private BannerIndicator indicatorView; /*指示器图*/
    private boolean isShowTitle;


    public BannerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (autoPlay) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                this.sendEmptyMessageDelayed(MSG_WHAT, delayMillis);
            }
        }
    };

    private void init() {
        isShowTitle = true;
        autoPlay = true;
        itemCount = 1;
        delayMillis = 3000;
        initView();
        initListener();
        mHandler.sendEmptyMessageDelayed(MSG_WHAT, delayMillis);
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setShowTitle(position);
                for (int i = 0; i < layoutIndicators.getChildCount(); i++) {
                    if (i == position % itemCount) {
                        ((BannerIndicator) layoutIndicators.getChildAt(i)).setState(true);
                    } else {
                        ((BannerIndicator) layoutIndicators.getChildAt(i)).setState(false);
                    }
                }
            }
        });
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.layout_banner_viewpager, this);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        layoutIndicators = (LinearLayout) view.findViewById(R.id.bannerIndicators);
        tvTitle = (TextView) view.findViewById(R.id.bannerTitle);
    }

    /**
     * 设置Banner标题
     *
     * @param position Banner位置
     */
    private void setShowTitle(int position) {
        if (isShowTitle) {
            if (tvTitle.getVisibility() == GONE)
                tvTitle.setVisibility(VISIBLE);
            String title = mData.get(position % itemCount).getTitle();
            tvTitle.setText(title);
        } else if (tvTitle.getVisibility() == VISIBLE) {
            tvTitle.setVisibility(GONE);
        }
    }

    /**
     * 设置指示器
     *
     * @param dataCount
     */
    private void setIndicators(int dataCount) {
        layoutIndicators.removeAllViews();
        for (int i = 0; i < dataCount; i++) {
            if (null == indicatorView) {
                BannerIndicator indicator = new BannerIndicator(getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        getRatioDimension(getContext(), 20, false),
                        getRatioDimension(getContext(), 20, false)
                );
                layoutParams.setMargins(
                        getRatioDimension(getContext(), 10, true), 0,
                        getRatioDimension(getContext(), 10, true), 0);

                indicator.setLayoutParams(layoutParams);
                layoutIndicators.addView(indicator);
            } else {
                BannerIndicator indicator = indicatorView;
                ViewParent vp = indicator.getParent();
                if (null != vp) {
                    ViewGroup parent = (ViewGroup) vp;
                    parent.removeView(indicator);
                }
                layoutIndicators.addView(indicator);
            }
        }
        ((BannerIndicator) layoutIndicators.getChildAt(0)).setState(true);
    }

    private static int getRatioDimension(Context context, int value, boolean isWidth) {
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        float STANDARD_WIDTH = 1080F;
        float STANDARD_HEIGHT = 1920F;
        if (isWidth) {
            return (int) (value / STANDARD_WIDTH * widthPixels);
        } else {
            return (int) (value / STANDARD_HEIGHT * heightPixels);
        }
    }

    private void setOnBannerItemListener(BannerItemBean itemBean) {
        if (null != bannerListener) {
            bannerListener.onClickListener(itemBean);
        }
    }

    /**
     * Banner回调监听
     */
    public interface OnBannerItemClickListener {
        void onClickListener(BannerItemBean itemBean);
    }

    /**
     * 加载图片显示
     */
    public interface BannerImageLoader {

        void displayImg(Context context, Object imgPath, ImageView img);
    }

    public void displayImg(Context context, Object object, ImageView imageView) {
        if (null != imageLoader)
            imageLoader.displayImg(context, object, imageView);
    }

    public BannerViewPager setData(List<BannerItemBean> data, BannerImageLoader imageLoader) {
        mData = data;
        this.imageLoader = imageLoader;

        /*初始化适配器*/
        BannerAdapter bannerAdapter = new BannerAdapter();
        /*设置适配器数据*/
        //        bannerAdapter.setData(data);
        itemCount = data.size();
        /*设置适配器*/
        viewPager.setAdapter(bannerAdapter);
        viewPager.setCurrentItem(itemCount * 1000);
        setIndicators(data.size());
        setShowTitle(0);
        return this;
    }

    /**
     * 设置是否自动播放
     *
     * @param autoPlay true自动播放，false停止播放
     * @return
     */
    public BannerViewPager setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
        return this;
    }

    /**
     * 设置翻页动画
     *
     * @return
     */
    public BannerViewPager setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        viewPager.setPageTransformer(true, pageTransformer);
        return this;
    }

    public BannerViewPager setPageMargin(int marginPixels) {
        viewPager.setPageMargin(marginPixels);
        return this;
    }

    /**
     * 设置延时时间
     *
     * @param delayMillis 时间
     * @return
     */
    public BannerViewPager setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
        return this;
    }

    /**
     * 设置是否显示Banner title
     *
     * @param isShowTitle
     * @return
     */
    public BannerViewPager setShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
        setShowTitle(viewPager.getCurrentItem());
        return this;
    }

    /**
     * 设置图片显示样式
     *
     * @param scaleType 样式
     */
    public BannerViewPager setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    /**
     * 设置Banner Item点击监听
     *
     * @param listener
     * @return
     */
    public BannerViewPager setOnBannerItemClickListener(OnBannerItemClickListener listener) {
        this.bannerListener = listener;
        return this;
    }

    public BannerViewPager setIndicatorView(BannerIndicator indicator) {
        indicatorView = indicator;
        viewPager.setCurrentItem(itemCount * 1000);
        setIndicators(itemCount);
        setShowTitle(0);
        return this;
    }


    /*********ViewPager适配器************/
    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return null == mData ? 0 : Integer.MAX_VALUE >> 1;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(((ImageView) object));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //        View view = mInflater.inflate(R.layout.layout_banner_item, container, false);
            //        container.addView(view);
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(scaleType);

            BannerItemBean itemBean = mData.get(position % itemCount);

            displayImg(container.getContext(), itemBean.getPic(), imageView);
            imageView.setTag(R.id.view_tag_key , itemBean);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    BannerItemBean itemBean = (BannerItemBean) view.getTag(R.id.view_tag_key);
                    setOnBannerItemListener(itemBean);
                }
            });
            container.addView(imageView);
            return imageView;
        }
    }

    /************Banner的JavaBean**************/
    public static class BannerItemBean {

        private String link;
        private String pic;
        private String title;

        public BannerItemBean(String link, String pic, String title) {
            this.link = link;
            this.pic = pic;
            this.title = title;
        }

        public BannerItemBean() {
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


    /*************自定义Banner指示器***************/
    private class BannerIndicator extends View {

        private Paint paint;

        public BannerIndicator(Context context) {
            this(context, null);
        }

        public BannerIndicator(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public BannerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            paint = new Paint();
            paint.setAntiAlias(true);
            setState(false);
        }

        private void setState(boolean b) {
            if (b)
                paint.setColor(0xffffffff);
            else
                paint.setColor(0x88ffffff);

            this.invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int measuredWidth = this.getMeasuredWidth();
            int measuredHeight = this.getMeasuredHeight();
            canvas.translate(measuredWidth * 0.5F, measuredHeight * 0.5F);
            canvas.drawCircle(0F, 0F, measuredWidth * 0.5F, paint);
        }
    }
}
