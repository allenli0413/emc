package com.zwyl;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 保存View和Fragment的对应关系
 * @date 2015/2/3 14:03
 */
public class ViewFragmentPari {

    View view;
    Fragment fragment;

    public ViewFragmentPari(View view, Fragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }

    public View getView() {
        return view;
    }

    public Fragment getFragment() {
        return fragment;
    }

}
