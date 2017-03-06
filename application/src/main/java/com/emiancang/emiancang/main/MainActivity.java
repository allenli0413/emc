package com.emiancang.emiancang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emiancang.emiancang.main.fragment.MainNearbyFragment;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.zwyl.ActivityManager;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.LoginBaseActivity;
import com.emiancang.emiancang.eventbean.JumpTransfer;
import com.emiancang.emiancang.eventbean.MainNearByTransfer;
import com.emiancang.emiancang.eventbean.MainStoreTransfer;
import com.emiancang.emiancang.main.fragment.MainHomeFragment;
import com.emiancang.emiancang.main.fragment.MainScanFragment;
import com.emiancang.emiancang.main.fragment.MainStoreFragment;
import com.emiancang.emiancang.main.fragment.MainUserInforFragment;
import com.emiancang.emiancang.scan.CommonScanActivity;
import com.emiancang.emiancang.uitl.Constant;
import com.emiancang.emiancang.uitl.Utils;
import com.emiancang.emiancang.update.UpdateManager;
import com.emiancang.emiancang.update.ZwyPreferenceManager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends LoginBaseActivity {

    long back_time;
    @InjectView(R.id.viewpager)
    FrameLayout viewpager;
    @InjectView(R.id.btn_main_home)
    TextView btnMainHome;
    @InjectView(R.id.btn_main_discover)
    TextView btnMainDiscover;
    @InjectView(R.id.btn_main_publish)
    TextView btnMainPublish;
    @InjectView(R.id.btn_main_store)
    TextView btnMainStore;
    @InjectView(R.id.btn_main_userinfor)
    TextView btnMainUserinfor;
    @InjectView(R.id.bottom_bar_view)
    LinearLayout bottomBarView;
    private View mSelectButton;
    ArrayList<Fragment> list = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        getHeadBar().hideHeader();

//        //恢复极光设置
//        JPushInterface.clearAllNotifications(App.getContext());//why
//        if (UserManager.INSTANCE.isLogin())
//            JpushUtil.getInstances().setAlias(UserManager.getInstance().getUserID());
//        else {
//            JpushUtil.getInstances().setAlias("0");
//        }

        //版本更新
        if (ZwyPreferenceManager.shouldShowUpdate(MainActivity.this)) {
            Intent intent = new Intent(UpdateManager.updaue_action);
            intent.putExtra("show_update", true);
            sendBroadcast(intent);
        }

        initView();
    }

    public void onEventMainThread(MainStoreTransfer mainStoreTransfer) {
        if (mSelectButton != null)
            mSelectButton.setSelected(false);
        btnMainStore.setSelected(true);
        mSelectButton = btnMainStore;
        setDefaultFragment(list.get(3));
    }

    public void onEventMainThread(MainNearByTransfer mainNearByTransfer) {
        if (mSelectButton != null)
            mSelectButton.setSelected(false);
        btnMainDiscover.setSelected(true);
        mSelectButton = btnMainDiscover;
        setDefaultFragment(list.get(1));
    }

    private void initView() {
        list.add(new MainHomeFragment());
        list.add(new MainNearbyFragment());
        list.add(new MainScanFragment());
        list.add(new MainStoreFragment());
        list.add(new MainUserInforFragment());
        int index = getIntExtra("fragmentIndex");
        if (index != -1) {
            switch (index) {
                case 0:
                    setDefaultFragment(list.get(index));
                    btnMainHome.setSelected(true);
                    mSelectButton = btnMainHome;
                    break;
                case 1:
                    setDefaultFragment(list.get(index));
                    btnMainDiscover.setSelected(true);
                    mSelectButton = btnMainDiscover;
                    break;
                case 2:
//                    showToast("扫一扫");
                    Intent intent=new Intent(this,CommonScanActivity.class);
                    intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_BARCODE_MODE);
                    startActivity(intent);
                    break;
                case 3:
                    setDefaultFragment(list.get(index));
                    btnMainStore.setSelected(true);
                    mSelectButton = btnMainStore;
                    break;
                case 4:
                    setDefaultFragment(list.get(index));
                    btnMainUserinfor.setSelected(true);
                    mSelectButton = btnMainUserinfor;
                    break;
            }
        } else {
            setDefaultFragment(list.get(0));
            btnMainHome.setSelected(true);
            mSelectButton = btnMainHome;
        }
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.viewpager, fragment);
        transaction.commit();
    }


    public void onBackPressed() {
        if (System.currentTimeMillis() - back_time > 5 * 1000) {
            back_time = System.currentTimeMillis();
            showToast(R.string.finish_toast);
            return;
        }
        ActivityManager.getInstance().exitAll();
        super.onBackPressed();
    }

    @OnClick({R.id.btn_main_home, R.id.btn_main_discover, R.id.btn_main_publish, R.id.btn_main_store, R.id.btn_main_userinfor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_home:
                if (mSelectButton != null)
                    mSelectButton.setSelected(false);
                btnMainHome.setSelected(true);
                mSelectButton = btnMainHome;
                setDefaultFragment(list.get(0));
                break;
            case R.id.btn_main_discover:
                if (mSelectButton != null)
                    mSelectButton.setSelected(false);
                btnMainDiscover.setSelected(true);
                mSelectButton = btnMainDiscover;
                setDefaultFragment(list.get(1));
                break;
            case R.id.btn_main_publish:
//                showToast("扫一扫");
                Intent intent=new Intent(this,CommonScanActivity.class);
                intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_BARCODE_MODE);
                startActivity(intent);
                break;
            case R.id.btn_main_store:
                if (mSelectButton != null)
                    mSelectButton.setSelected(false);
                btnMainStore.setSelected(true);
                mSelectButton = btnMainStore;
                setDefaultFragment(list.get(3));
                break;
            case R.id.btn_main_userinfor:
                if (mSelectButton != null)
                    mSelectButton.setSelected(false);
                btnMainUserinfor.setSelected(true);
                mSelectButton = btnMainUserinfor;
                setDefaultFragment(list.get(4));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        SharedPrefsUtil.remove(getActivity(), SharedPrefsUtil.KEEP_CITY);
        SharedPrefsUtil.remove(getActivity(), SharedPrefsUtil.KEEP_CITY_ID);
    }
}
