package com.hooyee.stickrecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;

/**
 * @author Hooyee on 2018/3/28.
 *         　　　┏┓　　　┏┓
 *         　　┏┛┻━━━┛┻┓
 *         　　┃　　　　　　　┃
 *         　　┃　　　━　　　┃
 *         　　┃　┳┛　┗┳　┃
 *         　　┃　　　　　　　┃
 *         　　┃　　　┻　　　┃
 *         　　┃　　　　　　　┃
 *         　　┗━┓　　　┏━┛
 *         　　　　┃　　　┃神兽保佑
 *         　　　　┃　　　┃代码无BUG！
 *         　　　　┃　　　┗━━━┓
 *         　　　　┃　　　　　　　┣┓
 *         　　　　┃　　　　　　　┏┛
 *         　　　　┗┓┓┏━┳┓┏┛
 *         　　　　　┃┫┫　┃┫┫
 *         　　　　　┗┻┛　┗┻┛
 *         ━━━━━━神兽出没━━━━━━
 *         玄冥守护，铁剑无敌！
 *         千剑藏锋数十载，未曾出鞘！
 *         待回头，流星赶月，瞬息十九州！
 *         斩妖魔！亦斩神佛！
 *         千世为泽，巨剑弥合！
 *         偏安一隅任逍遥，红尘莫扰！
 *         今转身！剑气纵横，寒光三万里！
 *         只问天下——
 *         谁人能匹！
 */

public abstract class StickAdapter<P extends ParentItem, C extends ChildItem> extends RecyclerView.Adapter<StickAdapter.BaseViewHolder> {
    protected static final int VIEW_TYPE_PARENT = 0x101;
    protected static final int VIEW_TYPE_CHILD = 0x102;
    protected LayoutInflater mInflater;

    public StickAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PARENT) {
            return createParentViewHolder(parent, VIEW_TYPE_PARENT);
        } else if (viewType == VIEW_TYPE_CHILD) {
            return createChildViewHolder(parent, VIEW_TYPE_CHILD);
        }
        return null;
    }

    public abstract BaseViewHolder createParentViewHolder(ViewGroup parent, int type);

    public abstract BaseViewHolder createChildViewHolder(ViewGroup parent, int type);

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        int type = getItemViewType(position);

        if (type == VIEW_TYPE_PARENT) {
            final ParentViewHolder h = (ParentViewHolder) holder;
            h.setData(getParentData(position));
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParentItem p = h.data;
                    p.setExpand(!p.isExpand());
                    if (p.isExpand() && p.getChildren() != null) {
                        Collections.sort(p.getChildren());
                    }
                    notifyDataSetChanged();
                }
            });
        } else if (type == VIEW_TYPE_CHILD) {
            ChildViewHolder c = (ChildViewHolder) holder;
            c.setData(getChildData(position));
        }
        OnBindViewHolder(holder, position, type);
    }

    protected void OnBindViewHolder(BaseViewHolder holder, int position, int viewType) {}

    protected abstract View getParentViewHolder(ViewGroup parent);

    protected abstract View getChildViewHolder(ViewGroup parent);

    public abstract P getParentData(int position);

    public abstract C getChildData(int position);

    public abstract void insertParent(P p);

    public abstract void insertChild(C c, P p);

    public abstract static class BaseViewHolder<T> extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setData(T o);
    }
}
