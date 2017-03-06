package com.emiancang.emiancang.my.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.litesuits.android.log.Log;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ShowItem;
import com.emiancang.emiancang.http.ViewControlUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import rx.Observable;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: TODO
 * @date 16-9-1 下午3:17
 */
public class ShowAdapter extends BaseAdapter {

    List<ShowItem> list = new ArrayList<>();

    public void addList(List<? extends ShowItem> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public void clear() {
        list.clear();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ShowItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_item, parent, false);
        }
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        ShowItem item = getItem(position);
        txtName.setText(item.getNickname());
        txtMessage.setText(item.getContent());

        return convertView;
    }
}
