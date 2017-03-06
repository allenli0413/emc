package com.emiancang.emiancang.login;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.igexin.sdk.PushManager;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.zwyl.App;
import com.zwyl.BaseActivity;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.IndentDetailsTailEntity;
import com.emiancang.emiancang.bean.MessageInfo;
import com.emiancang.emiancang.bean.OrderDetail;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.eventbean.SYSMSGTransfer;
import com.emiancang.emiancang.getui.GeTuiPushReceiver;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.main.MainActivity;
import com.emiancang.emiancang.main.home.businesshelp.activity.BusinessHelpActivity;
import com.emiancang.emiancang.main.home.findmoney.activity.FindMoneyActivity;
import com.emiancang.emiancang.main.home.findmoney.activity.FindMoneyDetailActivity;
import com.emiancang.emiancang.my.MyIndentDetailsTail;
import com.emiancang.emiancang.my.MyWarnActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.JsonUtil;
import com.emiancang.emiancang.uitl.SharedPrefsUtil;
import com.emiancang.emiancang.update.CommonUpdateService;
import com.emiancang.emiancang.user.UserManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.igexin.sdk.PushService.context;
import static com.emiancang.emiancang.uitl.JsonUtil.fromJson;

/**
 * Created by 22310 on 2016/9/3.
*/
public class StartActivity extends BaseActivity {

    /** Notification管理 */
    public NotificationManager mNotificationManager;
    /** Notification构造器 */
    NotificationCompat.Builder mBuilder;
    /** Notification的ID */
    int notifyId = 100;
    String IndentDetailstitle  = "订单消息";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //初始化个推
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        GeTuiPushReceiver.init(this);
        PushManager pushManager = PushManager.getInstance();
        com.litesuits.android.log.Log.e("yangzheng",pushManager.getClientid(this));
        GeTuiPushReceiver.setOnGetPushMessageListener(new GeTuiPushReceiver.OnGetPushMessageListener() {
            @Override
            public void getOstfMessage(String message) {
                if(message!=null){
                    com.litesuits.android.log.Log.e("yangzheng",message);
                    MessageInfo messageInfo = JsonUtil.fromJson(message, MessageInfo.class);
                    switch(getMessageType(messageInfo)){
                        case 0:
                            //系统消息
                            EventBus.getDefault().post(new SYSMSGTransfer(true));
                            // Notification.FLAG_ONGOING_EVENT --设置常驻
                            // Flag;Notification.FLAG_AUTO_CANCEL 通知栏上点击此通知后自动清除此通知
                            // notification.flags = Notification.FLAG_AUTO_CANCEL;
                            // //在通知栏上点击此通知后自动清除此通知
                            String title  = "系统消息";
                            if(!TextUtils.isEmpty(messageInfo.getTitle())){
                                title = messageInfo.getTitle();
                            }
                            mBuilder.setAutoCancel(true)// 点击后让通知将消失
                                    .setContentTitle(title).setContentText(messageInfo.getSys_msg()).setTicker("系统消息");
                            // 点击的意图ACTION是跳转到Intent
                            Intent resultIntent = new Intent(StartActivity.this, MyWarnActivity.class);
                            resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent pendingIntent = PendingIntent.getActivity(StartActivity.this, 0,
                                    resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            mBuilder.setContentIntent(pendingIntent);
                            break;
                        case 1:
                            //买卖邦消息
                            String BusinessHelptitle  = "买卖邦消息";
                            if(!TextUtils.isEmpty(messageInfo.getTitle())){
                                BusinessHelptitle = messageInfo.getTitle();
                            }
                            mBuilder.setAutoCancel(true)// 点击后让通知将消失
                                    .setContentTitle(BusinessHelptitle).setContentText(messageInfo.getTrade_msg()).setTicker("买卖邦消息");
                            // 点击的意图ACTION是跳转到Intent
                            Intent BusinessHelp = new Intent(StartActivity.this, BusinessHelpActivity.class);
                            BusinessHelp.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent BusinessHelpIntent = PendingIntent.getActivity(StartActivity.this, 0,
                                    BusinessHelp, PendingIntent.FLAG_UPDATE_CURRENT);
                            mBuilder.setContentIntent(BusinessHelpIntent);
                            break;
                        case 2:
                            //下单消息
                            if(!TextUtils.isEmpty(messageInfo.getTitle())){
                                IndentDetailstitle = messageInfo.getTitle();
                            }
                            ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
                            UserService api = ApiUtil.createDefaultApi(UserService.class);
                            ApiUtil.doDefaultApi(api.getOrderDetails(messageInfo.getId()), new HttpSucess<OrderDetail>() {
                                @Override
                                public void onSucess(OrderDetail orderDetail) {
                                    List<IndentDetailsTailEntity> mDate = getData(orderDetail,Integer.valueOf(messageInfo.getType()));
                                    mBuilder.setAutoCancel(true)// 点击后让通知将消失
                                            .setContentTitle(IndentDetailstitle).setContentText(messageInfo.getTrade_msg()).setTicker("订单消息");
                                    // 点击的意图ACTION是跳转到Intent
                                    Intent IndentDetails = new Intent(StartActivity.this, MyIndentDetailsTail.class);
                                    IndentDetails.putExtra("date", (Serializable) mDate);
                                    IndentDetails.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    PendingIntent IndentDetailsIntent = PendingIntent.getActivity(StartActivity.this, 0,
                                            IndentDetails, PendingIntent.FLAG_UPDATE_CURRENT);
                                    mBuilder.setContentIntent(IndentDetailsIntent);
                                }
                            }, viewControl);
                            break;
                        case 3:
                            //找资金消息
                            String FindMoneytitle  = "找资金消息";
                            if(!TextUtils.isEmpty(messageInfo.getTitle())){
                                FindMoneytitle = messageInfo.getTitle();
                            }
                            mBuilder.setAutoCancel(true)// 点击后让通知将消失
                                    .setContentTitle(FindMoneytitle).setContentText(messageInfo.getFinancing_msg()).setTicker("找资金消息");
                            // 点击的意图ACTION是跳转到Intent
                            Intent FindMoney = new Intent(StartActivity.this, FindMoneyDetailActivity.class);
                            FindMoney.putExtra("id", messageInfo.getId());
                            FindMoney.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            PendingIntent FindMoneyIntent = PendingIntent.getActivity(StartActivity.this, 0,
                                    FindMoney, PendingIntent.FLAG_UPDATE_CURRENT);
                            mBuilder.setContentIntent(FindMoneyIntent);
                            break;
                        case 4:
                            //被踢下线
                            if(UserManager.getInstance().isLogin()){
                                String login  = "登录提醒";
                                logout();
                                mBuilder.setAutoCancel(true)// 点击后让通知将消失
                                        .setContentTitle(login).setContentText(messageInfo.getFinancing_msg()).setTicker("找资金消息");
                                // 点击的意图ACTION是跳转到Intent
                                Intent intentLogin = new Intent(StartActivity.this, LoginActivity.class);
                                intentLogin.putExtra("id", messageInfo.getId());
                                PendingIntent l = PendingIntent.getActivity(StartActivity.this, 0,
                                        intentLogin, PendingIntent.FLAG_UPDATE_CURRENT);
                                mBuilder.setContentIntent(l);
                            }
                            break;
                        case 5:
                            //领取红包
                            String login  = "邀请通知";
                            logout();
                            mBuilder.setAutoCancel(true)// 点击后让通知将消失
                                    .setContentTitle(login).setContentText(messageInfo.getInvitedcode_msg()).setTicker("邀请通知");
                            break;
                    }

                    mNotificationManager.notify(notifyId, mBuilder.build());
                }
            }
        });
        boolean show = SharedPrefsUtil.getValue(this,SharedPrefsUtil.SHOWGUIDE,true);
        Intent rongService = new Intent();
        rongService.setClass(this, CommonUpdateService.class);
        startService(rongService);
        if(show){
            SharedPrefsUtil.putValue(this,SharedPrefsUtil.SHOWGUIDE,false);
            startActivity(createIntent(GuideActivity.class));
            finish();
        }else{
            startActivity(createIntent(SplashActivity.class));
            finish();
        }
        initNotify();
    }
    /** 初始化通知栏 */
    private void initNotify() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
                .setContentIntent(
                        getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                // .setNumber(number)//显示数量
                .setTicker("测试通知来啦")// 通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_ALL|Notification.DEFAULT_SOUND)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                // Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
                // requires VIBRATE permission
                .setSmallIcon(R.drawable.logo_real);
    }

    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性:
     * 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }


    public int getMessageType(MessageInfo messageInfo){
        if(!TextUtils.isEmpty(messageInfo.getSys_msg())){
            //系统消息
            return 0;
        }else if(!TextUtils.isEmpty(messageInfo.getTrade_msg())){
            //买卖邦消息
            return 1;
        }else if(!TextUtils.isEmpty(messageInfo.getOrder_msg())){
            //订单消息
            return 2;
        }else if(!TextUtils.isEmpty(messageInfo.getFinancing_msg())){
            //找资金消息
            return 3;
        }else if(!TextUtils.isEmpty(messageInfo.getLogin_remind())){
            //被踢下线通知
            return 4;
        }else if(!TextUtils.isEmpty(messageInfo.getInvitedcode_msg())){
            //领取红包
            return 5;
        }else{
            return 0;
        }
    }


    public List<IndentDetailsTailEntity> getData(OrderDetail orderDetail,int Type){
        List<IndentDetailsTailEntity> mDate = new ArrayList<IndentDetailsTailEntity>();
        if(orderDetail.getEDdDdzt().equals("03")){
            //已完成
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货",orderDetail.getEDdBjthsj(),true));
            mDate.add(new IndentDetailsTailEntity("5","开发票",orderDetail.getEDdFpkddShsj(),true));
            if(Type == 0){
                mDate.add(new IndentDetailsTailEntity("6","释放保证金",orderDetail.getEDdCgsbzjsfsj(),true));
            }else{
                mDate.add(new IndentDetailsTailEntity("6","释放保证金",orderDetail.getEDdGhsbzjsfsj(),true));
            }
            mDate.add(new IndentDetailsTailEntity("6","完成","",true));
            return mDate;
        }
        if(Type == 0 && orderDetail.getEDdCgsbzjzt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货",orderDetail.getEDdBjthsj(),true));
            mDate.add(new IndentDetailsTailEntity("5","开发票",orderDetail.getEDdFpkddShsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金",orderDetail.getEDdCgsbzjsfsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return mDate;

        }
        if(Type == 1 && orderDetail.getEDdGhsbzjzt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货",orderDetail.getEDdBjthsj(),true));
            mDate.add(new IndentDetailsTailEntity("5","开发票",orderDetail.getEDdFpkddShsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金",orderDetail.getEDdGhsbzjsfsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return mDate;

        }
        if(orderDetail.getEDdFpkddZt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货",orderDetail.getEDdBjthsj(),true));
            mDate.add(new IndentDetailsTailEntity("5","开发票",orderDetail.getEDdFpkddShsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return mDate;

        }
        if(orderDetail.getEDdSfth().equals("1")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货",orderDetail.getEDdBjthsj(),true));
            mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return mDate;

        }
        if(orderDetail.getEDdHqzypzZt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货","",false));
            mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return mDate;

        }
        if(orderDetail.getEDdDdzt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证","",false));
            mDate.add(new IndentDetailsTailEntity("4","提货","",false));
            mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return mDate;

        }
        if(orderDetail.getEDdDdzt().equals("01")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款","",false));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证","",false));
            mDate.add(new IndentDetailsTailEntity("4","提货","",false));
            mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return mDate;

        }
        mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
        mDate.add(new IndentDetailsTailEntity("2","付款","",false));
        mDate.add(new IndentDetailsTailEntity("3","货权转移证","",false));
        mDate.add(new IndentDetailsTailEntity("4","提货","",false));
        mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
        mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
        mDate.add(new IndentDetailsTailEntity("6","完成","",false));
        return mDate;
    }

    private void logout() {
        com.emiancang.emiancang.App.getInstance().logout(false,new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        UserManager.getInstance().remove();
                        showToast("您当前账号已在其他设备登陆！");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(StartActivity.this, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
