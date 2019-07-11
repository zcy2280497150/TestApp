package zcy.applibrary.utils;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import zcy.applibrary.R;

/**
 * Created by ChengYan Zhang
 * on 2018/12/19
 */
public class ImageLoaderUtils {

    public static void loaderByObj(ImageView imageView , Object url){
        Glide.with(imageView).load(url).into(imageView);
    }

    public static void loaderByObj(ImageView imageView , Object url , RequestOptions options){
        Glide.with(imageView).setDefaultRequestOptions(options).load(url).into(imageView);
    }

    public static void loaderByObj(ImageView imageView , Object url , @DrawableRes int defaultResourcesId){
        loaderByObj(imageView,url,createOption(defaultResourcesId));
    }

    public static void loaderByObj(ImageView imageView , Object url , @DrawableRes int placeholderId , @DrawableRes int errorId , @DrawableRes int fallbackId ){
        loaderByObj(imageView,url,createOption(placeholderId, errorId, fallbackId));
    }

    public static RequestOptions createOption(){
        return createOption(R.drawable.icon_error_d9);
    }

    public static RequestOptions createOption(@DrawableRes int defaultResourcesId){
        return createOption(defaultResourcesId,defaultResourcesId,defaultResourcesId);
    }

    public static RequestOptions createOption(@DrawableRes int placeholderId , @DrawableRes int errorId , @DrawableRes int fallbackId ){
        return new RequestOptions().centerCrop()
                .placeholder(placeholderId)//占位即加载中的图片。
                .error(errorId)//错误图片.
                .fallback(fallbackId);// 当url为null的时候，判断是否设置了fallback，是的话则显示fallback图片，否的话显示error图片，如果error还是没有设置则显示placeholder图片.
    }

}
