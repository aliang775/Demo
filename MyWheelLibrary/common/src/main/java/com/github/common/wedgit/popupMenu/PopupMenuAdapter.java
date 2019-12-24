package com.github.common.wedgit.popupMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.adapter.ArrayAdapter;
import com.xchat.R;

import java.util.ArrayList;
import java.util.List;

public class PopupMenuAdapter extends ArrayAdapter<PopupMenuItem> {
    private Context context;

    private List<PopupMenuItem> list;

    private LayoutInflater inflater;

    public PopupMenuAdapter(Context context, List<PopupMenuItem> list, int typeBg) {
        super(context);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView icon = null;
        TextView title = null;
        if (convertView == null) {

        } else {

        }
        PopupMenuItem item = list.get(position);
        if (item.getIcon() != 0) {
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(item.getIcon());
        } else {
            icon.setVisibility(View.GONE);
        }
        title.setText(item.getTitle());

        // 下面代码实现数据绑定
        return convertView;
    }

    @Override
    protected View createView(Context context, int position, PopupMenuItem data, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.nim_popup_menu_list_item, null);
        if (typeBg == TYPE_BG_BLACK) {
            convertView = inflater.inflate(R.layout.nim_popup_menu_list_black_item, null);
        } else {


        }
        icon = (ImageView) convertView.findViewById(R.id.popup_menu_icon);
        title = (TextView) convertView.findViewById(R.id.popup_menu_title);
        ViewHolder cache = new ViewHolder();
        cache.icon = icon;
        cache.title = title;
        convertView.setTag(cache);

        return null;
    }

    @Override
    protected View bindView(View convertView, int position, PopupMenuItem data) {
        ViewHolder cache = (ViewHolder) convertView.getTag();
        icon = cache.icon;
        title = cache.title;


        return null;
    }

    private final class ViewHolder {

        public ImageView icon;

        public TextView title;
    }
}
