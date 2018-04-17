package com.hooyee.stickrecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

public class MyAdapter extends StickAdapter<ParentItem, ChildItem> {
    private List<ParentItem> mData;

    public MyAdapter(Context context, @NonNull List<ParentItem> data) {
        super(context);
        mData = data;
    }

    @Override
    public BaseViewHolder createParentViewHolder(ViewGroup parent, int type) {
        return new ParentViewHolder(getParentViewHolder(null));
    }

    @Override
    public BaseViewHolder createChildViewHolder(ViewGroup parent, int type) {
        return new ChildViewHolder(getChildViewHolder(null));
    }

//    @Override
//    protected void OnBindViewHolder(BaseViewHolder holder, int position, int viewType) {
//        if (viewType == VIEW_TYPE_PARENT) {
//            final ParentViewHolder h = (ParentViewHolder) holder;
//            h.setData(getParentData(position));
//            h.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    h.data.setExpand(!h.data.isExpand());
//                    notifyDataSetChanged();
//                }
//            });
//        } else if (viewType == VIEW_TYPE_CHILD) {
//            ChildViewHolder c = (ChildViewHolder) holder;
//            c.setData(getChildData(position));
//        }
//    }

    @Override
    protected View getParentViewHolder(ViewGroup parent) {
        return mInflater.inflate(R.layout.holder_parent, parent);
    }

    @Override
    protected View getChildViewHolder(ViewGroup parent) {
        return mInflater.inflate(R.layout.holder_child, parent);
    }

    @Override
    public int getItemViewType(int position) {
        List<ParentItem> data = mData;
        int groupCount = data.size();
        int offset = 0;
        for (int i = 0; i < groupCount; i++) {
            int current = i + offset;
            if (current == position) return VIEW_TYPE_PARENT;
            if (data.get(i).isExpand()) {
                int range = data.get(i).getChildrenCount();
                if (position <= range + current) return VIEW_TYPE_CHILD;
                // 若组展开了，则需要增加偏移量
                offset += range;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return getChildrenCount() + getParentCount();
    }

    public int getChildrenCount() {
        List<ParentItem> d = mData;
        int count = 0;
        for (ParentItem item : d) {
            if (item.isExpand()) {
                count += item.getChildrenCount();
            }
        }
        return count;
    }

    public int getParentCount() {
        return mData.size();
    }

    public ParentItem getParentData(int position) {
        List<ParentItem> data = mData;
        int groupCount = data.size();
        int current;
        int offset = 0;
        for (int i = 0; i < groupCount; i++) {
            // 加上parent
            current = i + offset;
            if (current == position) {
                return data.get(i);
            }
            if (data.get(i).isExpand()) {
                offset += data.get(i).getChildrenCount();
            }
        }
        throw new IllegalArgumentException();
    }

    public ChildItem getChildData(int position) {
        List<ParentItem> data = mData;
        int groupCount = data.size();
        int offset = 0;
        int current;
        for (int i = 0; i < groupCount; i++) {
            current = i + offset;
            if (data.get(i).isExpand()) {
                int range = data.get(i).getChildrenCount();
                if (position <= range + current)
                    return data.get(i).getChildAt(position - current - 1);
                // 若组展开了，则需要增加偏移量
                offset += range;
            }
        }
        throw new IllegalArgumentException();
    }

    public int getPosition(ParentItem parentItem) {
        if (!mData.contains(parentItem)) return -1;
        List<ParentItem> data = mData;
        int groupCount = data.size();
        int current;
        int offset = 0;
        for (int i = 0; i < groupCount; i++) {
            // 加上parent
            current = i + offset;
            if (data.get(i).equals(parentItem)) {
                return current;
            }
            if (data.get(i).isExpand()) {
                offset += data.get(i).getChildrenCount();
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public void insertParent(ParentItem parentItem) {
        mData.add(parentItem);
        int start = getPosition(parentItem);
        int count = calculateCount(parentItem);
        notifyItemRangeInserted(start, count);
    }

    public void insertChild(List<ChildItem> children, ParentItem parent) {
        if (children == null || children.size() == 0) return;
        parent.addChildren(children);
        if (!parent.isExpand()) return;
        int start = getPosition(parent) + 1;
        int count = children.size();
        Collections.sort(parent.getChildren());
        notifyItemRangeInserted(start, count);
    }

    @Override
    public void insertChild(ChildItem childItem, ParentItem parentItem) {
        List<ChildItem> l = new ArrayList();
        l.add(childItem);
        insertChild(l, parentItem);
    }

    public void remove(ParentItem p) {
        if (!mData.contains(p)) return;
        mData.remove(p);
        int start = getPosition(p);
        int count = calculateCount(p);
        notifyItemRangeRemoved(start, count);
    }

    public void remove(ChildItem c) {
        List<ParentItem> data = mData;
        int index = -1;
        int offset = 0;
        for (int i = 0; i < data.size(); i++) {
            // 加一次parent
            offset += 1;
            if (data.get(i).contains(c)) {
                if (data.get(i).isExpand()) {
                    index = data.get(i).indexOf(c);
                }
                data.get(i).getChildren().remove(c);
                break;
            }
            if (data.get(i).isExpand()) {
                offset += data.get(i).getChildrenCount();
            }
        }

        if (index != -1) {
            notifyItemRemoved(index + offset);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    private int calculateCount(ParentItem p) {
        int count = 1;
        if (p.isExpand()) {
            count = p.getChildrenCount();
        }
        return count;
    }
}
