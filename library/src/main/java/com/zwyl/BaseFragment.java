package com.zwyl;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.litesuits.common.assist.Toastor;

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: fragment基类
 * @date 2015/1/23 14:26
 */
public class BaseFragment extends Fragment {

    Toastor toastor ;

    public Intent createIntent(Class<? extends Activity> cls) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(getActivity().getIntent());
        return intent;
    }

    public BaseFragment() {
        toastor = new Toastor(App.getContext());
    }

    //    /**
//     * 代码说明：
//     * <p/>
//     * 通过阅读ViewPager和PageAdapter相关的代码，切换Fragment实际上就是通过设置setUserVisibleHint和setMenuVisibility来实现的，调用这个方法时并不会释放掉Fragment（即不会执行onDestoryView）。
//     */
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            //相当于Fragment的onResume
//            onShow();
//        } else {
//            //相当于Fragment的onPause
//            onHide();
//        }
//    }

    protected void onShow() {

    }

    protected void onHide() {

    }


    public void showToast(String text){
        toastor.showToast(text);
    }

    public void showToast(int resid){
        toastor.showToast(resid);
    }

    /**
     * 第一次进入fragment执行onResume,以后再显示和切换就是执行onHiddenChanged方法了
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onShow();
        } else {
            onHide();
        }
    }

    public void onSelect(){

    }
}
