package com.unza.wipro.main.views.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.ImageHelper;
import com.unza.wipro.R;
import butterknife.BindView;
import butterknife.OnClick;

public class ProfileRegisterFragment extends BaseFragment {
    public static int REQUEST_PHOTO_CAMERA = 100;
    public static int REQUEST_PHOTO_GALLERY = 200;


    @BindView(R.id.layoutDisable)
    View layoutDisable;

    @BindView(R.id.layoutControllSelectImage)
    View layoutControllSelectImage;

    @BindView(R.id.registView)
    View registView;

    @BindView(R.id.avatar)
    ImageView imgAvatar;

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
        slideUp();
    }

    @OnClick(R.id.btnCamera)
    protected void openCamera() {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        startActivityForResult(takePictureIntent, REQUEST_PHOTO_CAMERA);
    }

    @OnClick(R.id.btnGallery)
    protected void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUEST_PHOTO_GALLERY);
    }

    @OnClick(R.id.btnCancel)
    protected void openCancel() {
        slideDown();
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO_CAMERA) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageBitmap = getRoundedCornerBitmap(imageBitmap, 150);
            imgAvatar.setImageBitmap(imageBitmap);
        }

        if (requestCode == REQUEST_PHOTO_GALLERY) {
            Uri imageUri = data.getData();
            ImageHelper.loadThumbCircleImage(this.getContext(), imageUri.toString(), imgAvatar);
        }
        slideDown();
    }

    public void slideUp() {
        layoutDisable.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                layoutControllSelectImage.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        layoutControllSelectImage.startAnimation(animate);
    }

    public void slideDown() {
        layoutDisable.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                layoutControllSelectImage.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        layoutControllSelectImage.startAnimation(animate);

    }
}
