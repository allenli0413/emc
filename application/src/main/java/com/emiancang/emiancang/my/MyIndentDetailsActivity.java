package com.emiancang.emiancang.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mayigeek.frame.http.state.HttpSucess;
import com.mayigeek.frame.view.state.ViewControl;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.base.TitleActivity;
import com.emiancang.emiancang.bean.IndentDetailsTailEntity;
import com.emiancang.emiancang.bean.OrderDetail;
import com.emiancang.emiancang.http.ApiUtil;
import com.emiancang.emiancang.http.ViewControlUtil;
import com.emiancang.emiancang.service.UserService;
import com.emiancang.emiancang.uitl.Util;
import com.emiancang.emiancang.user.User;
import com.emiancang.emiancang.user.UserManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.R.attr.data;
import static android.R.attr.key;
import static android.R.id.list;
import static com.emiancang.emiancang.R.id.details_rate;

/**
 * Created by 22310 on 2016/11/10.
 */

public class MyIndentDetailsActivity extends TitleActivity {
    @InjectView(R.id.details_status_goto)
    ImageView detailsStatusGoto;
    @InjectView(R.id.details_status)
    TextView detailsStatus;
    @InjectView(R.id.indetails_status_layout)
    RelativeLayout indetailsStatusLayout;
    @InjectView(R.id.details_tail_goto)
    ImageView detailsTailGoto;
    @InjectView(R.id.details_tail)
    TextView detailsTail;
    @InjectView(R.id.details_tail_layout)
    RelativeLayout detailsTailLayout;
    @InjectView(R.id.details_number)
    TextView detailsNumber;
    @InjectView(R.id.details_quality_layout)
    RelativeLayout detailsQualityLayout;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.details_colour)
    TextView detailsColour;
    @InjectView(R.id.details_length)
    TextView detailsLength;
    @InjectView(details_rate)
    TextView detailsRate;
    @InjectView(R.id.details_producing)
    TextView detailsProducing;
    @InjectView(R.id.details_plant)
    TextView detailsPlant;
    @InjectView(R.id.details_store)
    TextView detailsStore;
    @InjectView(R.id.details_price)
    TextView detailsPrice;
    @InjectView(R.id.details_money)
    TextView detailsMoney;

    private String mEDdId;
    private int Type;
    private List<IndentDetailsTailEntity> mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getActivity(), R.layout.activity_indent_details, null);
        setContentView(view);
        ButterKnife.inject(this);
        initHead();
        initView();
    }

    private void initView() {
        mEDdId = getIntent().getStringExtra("id");
        Type = getIntent().getIntExtra("type",-1);
        mDate = new ArrayList<>();
        ViewControl viewControl = ViewControlUtil.create2Dialog(getActivity());
        UserService api = ApiUtil.createDefaultApi(UserService.class);
        ApiUtil.doDefaultApi(api.getOrderDetails(mEDdId), new HttpSucess<OrderDetail>() {
            @Override
            public void onSucess(OrderDetail orderDetail) {
                if(orderDetail.getEDdDdzt().equals("01"))
                    detailsStatus.setText("代付款");
                else if(orderDetail.getEDdDdzt().equals("02"))
                    detailsStatus.setText("待收货");
                else if(orderDetail.getEDdDdzt().equals("03"))
                    detailsStatus.setText("已完成");
                else if(orderDetail.getEDdDdzt().equals("04"))
                    detailsStatus.setText("已违约");
                else
                    detailsStatus.setText("代付款");
                detailsTail.setText(getData(orderDetail));
                detailsNumber.setText(""+orderDetail.getGcmgyMhph());
                detailsColour.setText(orderDetail.getHyJysjYsj()+"("+orderDetail.getHyJysjYsjZj()+")");
                detailsLength.setText(orderDetail.getHyJysjCdz()+" 强力: "+orderDetail.getHyJysjDlbqd()+" 马值: "+orderDetail.getHyJysjMklz()+"  回潮: "+orderDetail.getHyJysjHcl());
                detailsRate.setText(orderDetail.getHyJysjHzl()+"  件数(包)："+orderDetail.getEDdJs()+"  重量(吨): "+orderDetail.getEDdJszl()+"（公）");
                detailsProducing.setText(orderDetail.getEDdCd());
                detailsPlant.setText(orderDetail.getEDdJgc());
                detailsStore.setText(orderDetail.getCustName());
                detailsPrice.setText(""+orderDetail.getEDdDj());
                detailsMoney.setText(""+orderDetail.getESpbsHbje());
            }
        }, viewControl);
    }


    private void initHead() {
        Util.setStatusBarColor(getActivity(), R.color.green);
        getHeadBar().setBackground(getResources().getColor(R.color.green));
        getHeadBar().setCenterTitle("订单详情",getResources().getColor(R.color.white));
    }

    @OnClick(R.id.details_tail_layout)
    public void onClick() {
        Intent intent = new Intent(this,MyIndentDetailsTail.class);
        intent.putExtra("date", (Serializable) mDate);
        startActivity(intent);
    }

    public String getData(OrderDetail orderDetail){
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
            return "已完成";
        }
        if(Type == 0 && orderDetail.getEDdCgsbzjzt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货",orderDetail.getEDdBjthsj(),true));
            mDate.add(new IndentDetailsTailEntity("5","开发票",orderDetail.getEDdFpkddShsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金",orderDetail.getEDdCgsbzjsfsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return "释放保证金";
        }
        if(Type == 1 && orderDetail.getEDdGhsbzjzt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货",orderDetail.getEDdBjthsj(),true));
            mDate.add(new IndentDetailsTailEntity("5","开发票",orderDetail.getEDdFpkddShsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金",orderDetail.getEDdGhsbzjsfsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return "释放保证金";
        }
        if(orderDetail.getEDdFpkddZt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货",orderDetail.getEDdBjthsj(),true));
            mDate.add(new IndentDetailsTailEntity("5","开发票",orderDetail.getEDdFpkddShsj(),true));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return "开发票";
        }
        if(orderDetail.getEDdSfth().equals("1")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货",orderDetail.getEDdBjthsj(),true));
            mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return "提货";
        }
        if(orderDetail.getEDdHqzypzZt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证",orderDetail.getEDdHqzypzShsj(),true));
            mDate.add(new IndentDetailsTailEntity("4","提货","",false));
            mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return "货权转移证";
        }
        if(orderDetail.getEDdDdzt().equals("02")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款",orderDetail.getEDdHkHzsj(),true));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证","",false));
            mDate.add(new IndentDetailsTailEntity("4","提货","",false));
            mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return "付款";
        }
        if(orderDetail.getEDdDdzt().equals("01")){
            mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
            mDate.add(new IndentDetailsTailEntity("2","付款","",false));
            mDate.add(new IndentDetailsTailEntity("3","货权转移证","",false));
            mDate.add(new IndentDetailsTailEntity("4","提货","",false));
            mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
            mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
            mDate.add(new IndentDetailsTailEntity("6","完成","",false));
            return "下单";
        }
        mDate.add(new IndentDetailsTailEntity("1","下单",orderDetail.getEDdXdsj(),true));
        mDate.add(new IndentDetailsTailEntity("2","付款","",false));
        mDate.add(new IndentDetailsTailEntity("3","货权转移证","",false));
        mDate.add(new IndentDetailsTailEntity("4","提货","",false));
        mDate.add(new IndentDetailsTailEntity("5","开发票","",false));
        mDate.add(new IndentDetailsTailEntity("6","释放保证金","",false));
        mDate.add(new IndentDetailsTailEntity("6","完成","",false));
        return "下单";
    }
}
