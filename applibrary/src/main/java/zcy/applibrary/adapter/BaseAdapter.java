package zcy.applibrary.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zcy.applibrary.holder.ViewHolder;
import zcy.applibrary.interfaces.OnClickViewListener;
import zcy.applibrary.interfaces.OnLongClickViewListener;

/**
 * Created by ChengYan Zhang
 * on 2019/5/23
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    private List<T> dataList;

    protected OnClickViewListener<T> onClickViewListener;
    protected OnLongClickViewListener<T> onLongClickViewListener;

    public void setOnClickViewListener(OnClickViewListener<T> onClickViewListener) {
        this.onClickViewListener = onClickViewListener;
    }

    public void setOnLongClickViewListener(OnLongClickViewListener<T> onLongClickViewListener) {
        this.onLongClickViewListener = onLongClickViewListener;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void addDataList(List<T> dataList){
        if (null == this.dataList) setDataList(dataList);
        else {
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return null == dataList ? 0 : dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    protected @LayoutRes
    abstract int layoutId();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder = getHolder(convertView,parent);
        holder.setPosition(position);
        return holder.getT();
    }

    private VH getHolder(View convertView, ViewGroup parent){
        if (null == convertView){
            return new VH(LayoutInflater.from(parent.getContext()).inflate(layoutId(),parent,false));
        }else {
            return (VH) convertView.getTag();
        }
    }

    public void initViewHolder(VH vh){
        vh.getT().setOnClickListener(vh);
        vh.getT().setOnLongClickListener(vh);
    }

    public class VH extends ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        private int position;

        public int getPosition() {
            return position;
        }

        public VH(@NonNull View view) {
            super(view);
            view.setTag(this);
            initViewHolder(this);
        }

        private void setPosition(int position) {
            this.position = position;
            bindData(this , getItem(position));
        }

        @Override
        public void onClick(View v) {
            if (null != onClickViewListener)onClickViewListener.onClick(v,getItem(position));
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onLongClickViewListener){
                onLongClickViewListener.onLongClick(v,getItem(position));
                return true;
            }
            return false;
        }
    }

    public abstract void bindData(VH holder , T t);

}
