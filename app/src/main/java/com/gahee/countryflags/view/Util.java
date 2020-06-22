package com.gahee.countryflags.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gahee.countryflags.R;

public class Util {
    public static void loadImage(
            ImageView view, String url, CircularProgressDrawable progressDrawable
    ){//이미지가 로딩하는 동안 사용할 수 있는 스피너
        Glide.with(view.getContext())
                .asBitmap()
                .load(Uri.parse(url))
                .placeholder(progressDrawable)
                .error(R.mipmap.ic_launcher_round)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        view.setImageBitmap(resource);
                    }
                });
    }

    public static CircularProgressDrawable getProgressDrawable(Context context){
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setStrokeWidth(10f);//float 스피너의 굵기
        progressDrawable.setCenterRadius(50f);//스피너의 동그란 정도
        progressDrawable.start();
        return progressDrawable;
    }
}
