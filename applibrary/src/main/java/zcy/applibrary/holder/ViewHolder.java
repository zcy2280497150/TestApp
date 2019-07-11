package zcy.applibrary.holder;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import zcy.applibrary.utils.ImageLoaderUtils;

/**
 * Create 2019/7/1 by zcy
 * QQ:1084204954
 * WeChat:ZCYzzzz
 * Email:1084204954@qq.com
 */
public class ViewHolder extends Holder<View> {

    private SparseArray<View> viewSparseArray;

    public void clear(){
        if (null != viewSparseArray)viewSparseArray.clear();
    }

    public ViewHolder(@NonNull View view) {
        super(view);
        viewSparseArray = new SparseArray<>();
    }

    public void setOnClickListener(View.OnClickListener listener,@IdRes int... ids){
        for (int id : ids){
            findView(id).setOnClickListener(listener);
        }
    }

    public <E extends View> E findView(@IdRes int id){
        View view = viewSparseArray.get(id,null);
        if (null == view){
            viewSparseArray.put(id,view = getT().findViewById(id));
        }
        return (E) view;
    }

    public void setText(@IdRes int id , CharSequence text){
        ((TextView)findView(id)).setText(text);
    }

    public void setText(@IdRes int id ,@StringRes int resId){
        ((TextView)findView(id)).setText(resId);
    }

    public void setTextColorInt(@IdRes int id , @ColorInt int color){
        ((TextView)findView(id)).setTextColor(color);
    }

    public void setTextColorRes(@IdRes int id , @ColorRes int color){
        ((TextView)findView(id)).setTextColor(getT().getResources().getColor(color));
    }

    public void loadImage(@IdRes int id , Object obj){
        ImageLoaderUtils.loaderByObj((ImageView) findView(id),obj);
    }

    public void loadImage(@IdRes int id , Object obj, @DrawableRes int resId){
        ImageLoaderUtils.loaderByObj((ImageView) findView(id),obj,resId);
    }

}
