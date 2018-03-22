package com.pshop.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.paditech.core.image.GlideApp;
import com.pshop.app.R;

public class ImageHelper extends com.paditech.core.helper.ImageHelper {
    private static final int THUMB_SIZE = 256;

    public static void loadAvatar(Context context, Object url, ImageView imageView) {
        GlideApp.with(context).load(url + "").placeholder(R.drawable.ic_avatar_holder).error(R.drawable.ic_avatar_holder).circleCrop().into(imageView);
    }
}
