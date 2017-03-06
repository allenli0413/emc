package com.zwyl.view;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zwyl.library.R;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<String> {
    private SimpleDraweeView imageView;



    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new SimpleDraweeView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())
                .setPlaceholderImage(ContextCompat.getDrawable(context, R.drawable.place), ScalingUtils.ScaleType.FIT_XY)
                .setProgressBarImage(ContextCompat.getDrawable(context, R.drawable.place), ScalingUtils.ScaleType.FIT_XY)
                .setFailureImage(ContextCompat.getDrawable(context, R.drawable.place), ScalingUtils.ScaleType.FIT_XY)
                .build();
        imageView.setHierarchy(hierarchy);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context,int position, String data) {
        imageView.setImageURI(Uri.parse(data));
    }
}
