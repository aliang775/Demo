package com.github.common.wedgit.popupMenu;

import android.content.Context;

public class PopupMenuItem {
    private int mTag;
    private int icon;
    private String mTitle;
    private Context mContext;

    public PopupMenuItem(int tag, int icon, String title) {

    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
