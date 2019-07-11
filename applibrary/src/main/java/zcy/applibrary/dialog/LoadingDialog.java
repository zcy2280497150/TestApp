package zcy.applibrary.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import zcy.applibrary.R;
import zcy.applibrary.holder.ViewHolder;


public class LoadingDialog extends BDVH {

    private CharSequence text;

    public LoadingDialog(CharSequence text) {
        this.text = text;
    }

    @Override
    protected int layoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void initView(ViewHolder holder) {
        holder.loadImage(R.id.gif_iv,R.drawable.gif_loading);
        if (!TextUtils.isEmpty(text)){
            holder.setText(R.id.text_view_loading,text);
            holder.findView(R.id.text_view_loading).setVisibility(View.VISIBLE);
        }
    }

}
