package com.emiancang.emiancang.my.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emiancang.emiancang.eventbean.ShoppingCartSalesmanTransfer;
import com.emiancang.emiancang.main.home.redpapercotton.activity.RedPaperCottonDetailActivity;
import com.emiancang.emiancang.store.model.MainStoreModel;
import com.emiancang.emiancang.uitl.DoubiUtils;
import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.bean.ResEntity;
import com.emiancang.emiancang.bean.ShoppingCartEntity;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.my.MyEnterpriseBindingActivity;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.user.UserManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

import static com.emiancang.emiancang.update.ZwyCommon.showToast;


/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: TODO
 * @date 16-9-1 下午3:17
 */
public class AdapterShoppingCartSalesman extends BaseAdapter {

    private AlertDialog mShowLoginDialog;
    private Activity mActivity;

    private List<ShoppingCartEntity> list;

    public AdapterShoppingCartSalesman(Activity activity) {
        list = new ArrayList<>();
        mActivity = activity;
    }

    public void addList(List<? extends ShoppingCartEntity> list) {
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public void setList(List<ShoppingCartEntity> list) {
        this.list = list;
    }

    public List<ShoppingCartEntity> getList() {
        return list;
    }

    public void clear() {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ShoppingCartEntity getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shopping_cart_salesman, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        ShoppingCartEntity entity = list.get(position);
        viewHolder.indentNumber.setText(entity.getEGwcPh() + " (" + entity.getHyJysjZbs() + ")");
        viewHolder.shoppingCartAdminItemOrderDetail.setText(entity.getHyJysjYsj() + " 长度：+" + entity.getHyJysjCdz() + " 强力：" + entity.getHyJysjDlbqd() + " 马值：" + entity.getHyJysjMklz());//淡黄染棉1级
        viewHolder.shoppingCartAdminItemPrice.setText("" + entity.getEGwcDj());
        viewHolder.shoppingCartAdminItemSupplierName.setText(entity.getCustName());
        viewHolder.shoppingCartAdminItemWarehouseName.setText(entity.getHyCkbmc());
        viewHolder.shoppingCartAdminItemDate.setText(entity.getPublishDate());
        if(TextUtils.isEmpty(entity.getESpbsHbje()))
            viewHolder.shoppingCartAdminItemWarehouseRedPaper.setVisibility(View.GONE);
        else
            viewHolder.shoppingCartAdminItemWarehouseRedPaper.setVisibility(View.VISIBLE);
        viewHolder.shoppingCartAdminItemWarehouseRedPaper.setText(entity.getESpbsHbje());
        viewHolder.shoppingCartAdminItemDistance.setText(DoubiUtils.hehe(entity.getDistance()));
        if (entity.getEGwcSftszqy().equals("0")) {
            viewHolder.salesmanPushType.setText("未推送");
        } else if (entity.getEGwcSftszqy().equals("1")) {
            viewHolder.salesmanPushType.setText("已推送");
            viewHolder.tuisongLayout.setVisibility(View.GONE);
        } else if (entity.getEGwcSftszqy().equals("2")) {
            viewHolder.salesmanPushType.setText("已驳回");
        }

        viewHolder.ll_adapter_shopping_cart_salesman_main.setOnClickListener(v -> {
            Intent intent = new Intent(mActivity, RedPaperCottonDetailActivity.class);
            MainStoreModel item = new MainStoreModel();
            item.setGcmgyMhph(entity.geteGwcPh());
            item.setCustId(entity.getCustId());
            item.setProductId(entity.getProductId() + "");
            intent.putExtra("data", item);
            mActivity.startActivity(intent);
        });
        viewHolder.shoppingCartRemove.setOnClickListener(v -> removeDialog(entity));
        viewHolder.shoppingCartPush.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(UserManager.getInstance().getUser().getESjzcHynm())) {
                bindingDialog(entity);
            } else {
                orderDialog();
            }
        });
        return convertView;
    }


    private void removeDialog(ShoppingCartEntity entity) {
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("棉花移出购物车");
        content_tv.setText("是否将选中的棉花移出购物车？");
        cancel_tv.setText("移出");
        cancel_tv.setTextColor(mActivity.getResources().getColor(R.color.half_red_color1));
        verify_tv.setText("不移出");
        verify_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowLoginDialog.dismiss();
            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewControl viewControl = ViewControlUtil.create2Dialog(mActivity);
                UserService api = ApiUtil.createDefaultApi(UserService.class, ApiUtil.SMURL);
                //状态
                ApiUtil.doDefaultApi(api.removeGwc(entity.getEGwcId(), ""+Integer.valueOf(entity.getEGwcSftszqy())), new HttpSucess<ResEntity>() {
                    @Override
                    public void onSucess(ResEntity resEntity) {
                        showToast(resEntity.getResMsg());
                        mShowLoginDialog.dismiss();
                        if(resEntity.getResCode().equals("1"))
                            EventBus.getDefault().post(new ShoppingCartSalesmanTransfer());
                    }
                }, viewControl);
            }
        });
        mShowLoginDialog = new AlertDialog.Builder(mActivity).setView(dialogView).create();
        mShowLoginDialog.show();
    }

    private void orderDialog() {
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setVisibility(View.INVISIBLE);
        content_tv.setText("企业账号未绑定");
        cancel_tv.setText("查看");
        verify_tv.setText("取消");
        verify_tv.setTextColor(mActivity.getResources().getColor(R.color.half_red_color1));
        verify_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowLoginDialog.dismiss();
            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MyEnterpriseBindingActivity.class);
                mActivity.startActivity(intent);
                mShowLoginDialog.dismiss();
            }
        });
        mShowLoginDialog = new AlertDialog.Builder(mActivity).setView(dialogView).create();
        mShowLoginDialog.show();
    }

    private void bindingDialog(ShoppingCartEntity entity) {
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.activity_servicelist_dialog, null);
        TextView title_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_title_tv);
        TextView content_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_content_tv);
        TextView verify_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_verify_tv);
        TextView cancel_tv = (TextView) dialogView.findViewById(R.id.servicelist_dialog_cancel_tv);
        title_tv.setText("推送至企业管理员");
        content_tv.setText("是否将选中的棉花推送至企业管理员？");
        cancel_tv.setText("不推送");
        verify_tv.setText("推送");
        verify_tv.setTextColor(mActivity.getResources().getColor(R.color.half_red_color1));
        verify_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewControl viewControl = ViewControlUtil.create2Dialog(mActivity);
                UserService api = ApiUtil.createDefaultApi(UserService.class, ApiUtil.SMURL);
                ApiUtil.doDefaultApi(api.pushToQyGwc(entity.getEGwcId()), new HttpSucess<ResEntity>() {
                    @Override
                    public void onSucess(ResEntity resEntity) {
                        showToast(resEntity.getResMsg());
                        mShowLoginDialog.dismiss();
                    }
                }, viewControl);
            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShowLoginDialog.dismiss();
            }
        });
        mShowLoginDialog = new AlertDialog.Builder(mActivity).setView(dialogView).create();
        mShowLoginDialog.show();
    }

    static class ViewHolder {
        @InjectView(R.id.ll_adapter_shopping_cart_salesman_main)
        LinearLayout ll_adapter_shopping_cart_salesman_main;
        @InjectView(R.id.indent_number)
        TextView indentNumber;
        @InjectView(R.id.salesman_push_type)
        TextView salesmanPushType;
        @InjectView(R.id.shopping_cart_admin_item_order_detail)
        TextView shoppingCartAdminItemOrderDetail;
        @InjectView(R.id.shopping_cart_admin_item_price)
        TextView shoppingCartAdminItemPrice;
        @InjectView(R.id.shopping_cart_admin_item_supplier_name)
        TextView shoppingCartAdminItemSupplierName;
        @InjectView(R.id.shopping_cart_admin_item_warehouse_red_paper)
        TextView shoppingCartAdminItemWarehouseRedPaper;
        @InjectView(R.id.shopping_cart_admin_item_warehouse_name)
        TextView shoppingCartAdminItemWarehouseName;
        @InjectView(R.id.shopping_cart_admin_item_distance)
        TextView shoppingCartAdminItemDistance;
        @InjectView(R.id.shopping_cart_admin_item_date)
        TextView shoppingCartAdminItemDate;
        @InjectView(R.id.shopping_cart_remove)
        TextView shoppingCartRemove;
        @InjectView(R.id.shopping_cart_push)
        TextView shoppingCartPush;
        @InjectView(R.id.tuisong_layout)
        LinearLayout tuisongLayout;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
