package com.emiancang.emiancang.my;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.emiancang.emiancang.eventbean.SalesmanBindTransfer;
import com.litesuits.android.log.Log;
import com.mayigeek.frame.http.state.HttpError;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.mayigeek.nestrefreshlayout.NestRefreshManager;
import com.zwyl.BaseActivity;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.ContactEntity;
import com.emiancang.emiancang.bean.OrbindSalesman;
import com.emiancang.emiancang.bean.SalesmanMangeEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.HttpResultFunc;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.adapter.AdapterBindingContacts;
import com.emiancang.emiancang.my.adapter.AdapterBindingContactsSearch;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.UserManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.appsdream.nestrefresh.normalstyle.NestRefreshLayout;
import de.greenrobot.event.EventBus;
import rx.Observable;

import static com.igexin.sdk.PushService.context;

/**
 * Created by 22310 on 2016/11/9.
 */

public class MySalesmanBindingActivity extends BaseActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    @InjectView(R.id.salesman_search)
    TextView salesmanSearch;
    @InjectView(R.id.salesman_cancel)
    TextView salesmanCancel;
    @InjectView(R.id.salesman_contacts_title)
    TextView salesmanContactsTitle;
    @InjectView(R.id.nestRefreshLayout_contacts)
    NestRefreshLayout nestRefreshLayoutContacts;
    @InjectView(R.id.contacts_layout)
    LinearLayout contactsLayout;
    @InjectView(R.id.listView_contacts2)
    ListView listViewContacts2;
    @InjectView(R.id.nestRefreshLayout_contacts2)
    NestRefreshLayout nestRefreshLayoutContacts2;
    @InjectView(R.id.listView_contacts)
    ListView listViewContacts;



    private AdapterBindingContacts mContactsAdapter;
    private AdapterBindingContactsSearch mContactsSearchAdapter;
    private AlertDialog mShowLoginDialog;
    private List<ContactEntity> mUserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_salesman_binding, null);
        setContentView(view);
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        initHead();
        initView();
    }

    public void onEventMainThread(SalesmanBindTransfer salesmanBindTransfer){
        finish();
    }

    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限申请成功
                refreshContacts();
            } else {
                showToast("获取联系人的权限申请失败");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initView() {
        nestRefreshLayoutContacts.setPullRefreshEnable(false);
        nestRefreshLayoutContacts.setPullLoadEnable(false);
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
        } else {
            refreshContacts();
        }
        listViewContacts.setOnItemClickListener((parent, view, position, id) -> {
            if(TextUtils.isEmpty(getPhone().get(position).getPhone())){
                showToast("该联系人没有手机号码");
                return;
            }
            Intent intent = createIntent(MySalesmanBindingSearchActivity.class);
            intent.putExtra("phone", getPhone().get(position).getPhone().replaceAll(" ",""));
            startActivity(intent);

//            String phone = getPhoneContacts().get(position).getPhone();
//            ViewControl viewControl = ViewControlUtil.create2View(nestRefreshLayoutContacts2,"该业务员不存在","该业务员不存在",R.drawable.icon_content_empty);
//            NestRefreshManager<SalesmanMangeEntity> nestRefreshManager = new NestRefreshManager<>(nestRefreshLayoutContacts2, viewControl, (page, perPage) -> {
//                UserService api = ApiUtil.createDefaultApi(UserService.class);
//                return api.getSalesmanListMobile(phone).map(new HttpResultFunc<>());
//            });
//            nestRefreshManager.setPullLoadEnable(true);
//            nestRefreshManager.setHttpError(e -> Log.i("test", e.toString()));
//            nestRefreshManager.setCallBack((allList, currentList) -> {
//                if (allList != null && allList.size() > 0) {
//                    binding(allList.get(0).getESjzcNm());
//                } else {
//                    showToast("该用户未被注册成业务员，请注册业务员之后再进行此操作");
//                }
//            });
//            nestRefreshManager.doApi();
        });
    }

    public void refreshContacts() {
        mContactsAdapter = new AdapterBindingContacts();
        listViewContacts.setAdapter(mContactsAdapter);
        mUserPhone = getPhone();
        mContactsAdapter.addList(mUserPhone);
        mContactsAdapter.notifyDataSetChanged();
    }

    private Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;// 联系人的Uri对象
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;// 获取联系人电话的Uri

    public List<ContactEntity> getPhoneContacts() {
        List<ContactEntity> contacts = new ArrayList<ContactEntity>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(contactsUri, null, null, null, null);
        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    ContactEntity contact = new ContactEntity();
                    // 联系人的id
                    int contactid = cursor.getInt(cursor
                            .getColumnIndex(ContactsContract.Contacts._ID));
                    // 获取联系人的name
                    String contactName = cursor.getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contact.setName(contactName);
                    // 通过联系人的id 获取联系人电话信息
                    Cursor phoneCusor = getContentResolver().query(phoneUri, null,
                            ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + "=?",
                            new String[]{contactid + ""}, null);
                    if (phoneCusor != null) {
                        while (phoneCusor.moveToNext()) {
                            // 获取联系人电话号码
                            String phone = phoneCusor
                                    .getString(phoneCusor
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contact.setPhone(phone);
                        }
                        phoneCusor.close();
                    }
                    contacts.add(contact);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(cursor != null)
                cursor.close();
        }
        return contacts;
    }


    private void binding(String id) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("业务员绑定");
        content_tv.setText("是否要将业务员姓名（电话号码）的与本公司进行绑定？");
        cancel_tv.setText("不绑定");
        verify_tv.setText("绑定");
        cancel_tv.setTextColor(this.getResources().getColor(R.color.red));
        cancel_tv.setOnClickListener(v -> mShowLoginDialog.dismiss());
        verify_tv.setOnClickListener(v -> {
            ViewControl viewControl = ViewControlUtil.create2Dialog(MySalesmanBindingActivity.this);
            UserService api = ApiUtil.createDefaultApi(UserService.class);
            ApiUtil.doDefaultApi(api.rmOrbindSalesman("1", id, UserManager.getInstance().getUser().getESjzcHynm()), orbindSalesman -> {
                showToast("绑定成功");
                mShowLoginDialog.dismiss();
            }, viewControl);
        });
        mShowLoginDialog = new AlertDialog.Builder(this).setView(dialogView).create();
        mShowLoginDialog.show();
    }

    @OnClick({R.id.salesman_search, R.id.salesman_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.salesman_search:
                startActivity(createIntent(MySalesmanBindingSearchActivity.class));
                break;
            case R.id.salesman_cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private List<ContactEntity> getPhone(){
        List<ContactEntity> contacts = new ArrayList<ContactEntity>();
        try {
            Uri contactUri =ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = getContentResolver().query(contactUri,
                    null,
                    null, null, "sort_key");
            String contactName;
            String contactNumber;
            int contactId;
            while (cursor.moveToNext()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                ContactEntity contact = new ContactEntity();
                contact.setName(contactName);
                contact.setPhone(contactNumber);
                contacts.add(contact);
            }
            cursor.close();//使用完后一定要将cursor关闭，不然会造成内存泄露等问题
        }catch (Exception e){
            e.printStackTrace();
        }
        return contacts;
    }
}
