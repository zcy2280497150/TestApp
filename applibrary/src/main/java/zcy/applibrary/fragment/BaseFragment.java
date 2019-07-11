package zcy.applibrary.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import java.util.Objects;

import zcy.applibrary.holder.ViewHolder;

/**
 * Created by ChengYan Zhang
 * on 2018/12/17
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    protected ViewHolder holder;
    protected abstract int getLayoutId();
    public static final String BUNDLE_DATA_KEY = "BUNDLE_DATA_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        holder = new ViewHolder(inflater.inflate(getLayoutId(), container, false));
        initViews();
        return holder.getT();
    }

    protected void initViews() {

    }

    protected void finish(){
        Objects.requireNonNull(getActivity()).finish();
    }

    protected void setViewListeners(@IdRes int... ids){
        holder.setOnClickListener(this,ids);
    }

    protected void findView(@IdRes int id){
        holder.findView(id);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected Bundle createBundle(Object obj){
        Bundle bundle = new Bundle();
        if (null != obj){
            if (obj instanceof Integer){
                bundle.putInt(BUNDLE_DATA_KEY , (Integer) obj);
            }else if (obj instanceof Boolean){
                bundle.putBoolean(BUNDLE_DATA_KEY , (Boolean) obj);
            }else if (obj instanceof Float){
                bundle.putFloat(BUNDLE_DATA_KEY , (Float) obj);
            }else if (obj instanceof Long){
                bundle.putLong(BUNDLE_DATA_KEY , (Long) obj);
            }else if (obj instanceof Double){
                bundle.putDouble(BUNDLE_DATA_KEY , (Double) obj);
            }else if (obj instanceof String){
                bundle.putString(BUNDLE_DATA_KEY , (String) obj);
            }else {
                bundle.putString(BUNDLE_DATA_KEY , JSON.toJSONString(obj));
            }
        }
        return bundle;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        holder.clear();
        holder = null;
    }
}