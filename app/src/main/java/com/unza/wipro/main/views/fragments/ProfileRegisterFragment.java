package com.unza.wipro.main.views.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.paditech.core.BaseFragment;
import com.unza.wipro.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileRegisterFragment extends BaseFragment {

    @BindView(R.id.dialogSelectImage)
    FrameLayout frameLayout;

    @BindView(R.id.avatar)
    ImageView imgDemo;

    public static ProfileRegisterFragment newInstance() {

        Bundle args = new Bundle();

        ProfileRegisterFragment fragment = new ProfileRegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile_register;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_profile_register);
    }

    @OnClick(R.id.avatar)
    protected void showSelectFrame() {
        slideUp(frameLayout);
    }

    @OnClick(R.id.btnCamera)
    protected void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @OnClick(R.id.btnGallery)
    protected void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 200);
    }

    @OnClick(R.id.btnCancel)
    protected void openCancel() {
        slideDown(frameLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgDemo.setImageBitmap(bitmap);
        }
        if (requestCode == 200) {
            Uri imageUri = data.getData();
            imgDemo.setImageURI(imageUri);
        }
        slideDown(frameLayout);
    }

    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                frameLayout.setVisibility(View.GONE);
            }
        }, 500);

    }
}
