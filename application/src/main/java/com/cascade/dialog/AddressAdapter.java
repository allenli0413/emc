package com.cascade.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zwyl.ZwyOSInfo;
import com.emiancang.emiancang.R;
import com.emiancang.emiancang.uitl.DensityUtil;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * @author weixy@zhiwy.com
 * @version V1.0
 * @Description: com.cascade.dialog
 * @date 2016/4/28 18:08
 */
public class AddressAdapter<T> extends AbstractWheelTextAdapter {

    // items
    private T items[];
    /**
     * Constructor
     */
    protected AddressAdapter(Context context,T items[]) {
        super(context, R.layout.address_item_view, NO_RESOURCE);
        this.items = items;

        setItemTextResource(R.id.item_text);
        setTextColor(context.getResources().getColor(
                R.color.black));
        setUnSelectTextColor(context.getResources().getColor(
                R.color.black));
    }

    @Override
    public void unSelectTextView(TextView textView) {
        if(ZwyOSInfo.getPhoneWidth()>1080){
            textView.setTextSize(DensityUtil.px2dip(textView.getContext(),
                    textView.getContext().getResources().getDimension(R.dimen.T2)));
        }else{
            textView.setTextSize(DensityUtil.px2dip(textView.getContext(),
                    textView.getContext().getResources().getDimension(R.dimen.T2)));
        }
    }

    @Override
    public void SelectTextView(TextView textView) {
        if(ZwyOSInfo.getPhoneWidth()>1080){
            textView.setTextSize(DensityUtil.px2dip(textView.getContext(),
                    textView.getContext().getResources().getDimension(R.dimen.T1)));
        }else{
            textView.setTextSize(DensityUtil.px2dip(textView.getContext(),
                    textView.getContext().getResources().getDimension(R.dimen.T1)));
        }
    }
    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.length) {
            T item = items[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.length;
    }
}