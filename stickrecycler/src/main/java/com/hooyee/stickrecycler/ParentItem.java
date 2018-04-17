package com.hooyee.stickrecycler;

import java.util.ArrayList;
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

public class ParentItem<T extends ChildItem>{
    private String name;
    private List<T> children;
    private boolean expand;

    public ParentItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public int getChildrenCount() {
        return children == null ? 0 : children.size();
    }

    public T getChildAt(int position) {
        if (children == null) return null;
        return children.get(position);
    }

    public void addChild(T t) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(t);
    }

    public void addChildren(List<T> list) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.addAll(list);
    }

    public boolean contains(T t) {
        if (children == null || t == null) return false;
        return children.contains(t);
    }

    public int indexOf(T t) {
        if (children == null || t == null) return -1;
        return children.indexOf(t);
    }
}
