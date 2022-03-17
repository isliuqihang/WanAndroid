package com.lqh.wanandroid.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lqh.fastlibrary.manager.GlideManager;
import com.lqh.wanandroid.R;
import com.lqh.wanandroid.entity.BannerData;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，图片+标题
 */

public class ImageTitleAdapter extends BannerAdapter<BannerData, ImageTitleHolder> {


    public ImageTitleAdapter(List<BannerData> datas) {
        super(datas);
    }

    @Override
    public ImageTitleHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageTitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image_title, parent, false));
    }

    @Override
    public void onBindView(ImageTitleHolder holder, BannerData data, int position, int size) {
        GlideManager.loadImg(data.getImagePath(), holder.imageView);
        holder.title.setText(data.getTitle());
    }

}
