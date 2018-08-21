package com.common.wedgit.popupMenu;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.example.common.R;

public class ImersionPopupMenu extends AppCompatActivity {
    private Context mContext;
    private View mRootView;
    private List<PopupMenuItem> items;
    private MenuItemClickListener mListener;
    private PopupMenuAdapter mAdapter;
    private PopupWindow mPopWindow;

    private boolean mScroll;

    public ImersionPopupMenu(Context context) {
        mContext = context;
    }

    /**
     * 初始化列表界面
     */
    private void initListView() {
        if (mRootView == null) {
            mRootView = LayoutInflater.from(this).inflate(R.layout.popup_menu_layout, null);
            ListView menuListView = mRootView.findViewById(R.id.popmenu_listview);
            menuListView.setOnClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mListener != null) {
                        mListener.onItemClick(items.get(position));
                    }
                }
            });
            mAdapter = new PopupMenuAdapter(context, items, typeBg);
            menuListView.setAdapter(mAdapter);
        }

        mRootView.setFocusableInTouchMode(true);
        mRootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && mPopWindow.isShowing() &&
                        event.getAction() == KeyEvent.ACTION_DOWN) {
                    mPopWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    private void initPopupWindow() {
        if (mPopWindow == null) {
            mPopWindow = new PopupWindow();
            mPopWindow.setContentView(mRootView);
            mPopWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

            if (mScroll) {
                mPopWindow.setHeight(FullScren * 2 / 3);
            } else {
                mPopWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            }

            mPopWindow.setTouchable(true);
            setPopWindowBackgroud(new BitmapDrawable());
        }
    }

    public void setPopWindowBackgroud(Drawable drawable) {
        mPopWindow.setBackgroundDrawable(drawable);
        mPopWindow.update();
    }

    public void show(View anchor) {
        if (mPopWindow == null) {
            return;
        }

        if (mPopWindow.isShowing()) {
            return;
        } else {
            if (mScroll) {
                Configuration configuration = mContext.getResources().getConfiguration();
                int ori = configuration.orientation;
                if (ori == Configuration.ORIENTATION_LANDSCAPE) {
                    mPopWindow.setHeight(ScreenUtil.getDialogWidth() * 3 / 4);
                } else {
                    mPopWindow.setHeight(ScreenUtil.getDialogWidth() * 2 / 3);
                }

            }
            mPopWindow.setFocusable(true);
            mPopWindow.showAsDropDown(anchor, -10, 0);
        }
    }

    public boolean isShowing() {
        return mPopWindow != null && mPopWindow.isShowing();
    }

    public void dissmiss() {
        if (isShowing()) {
            mPopWindow.dismiss();
        }
    }

    public interface MenuItemClickListener {
        void onItemClick(PopupMenu item);
    }
}
