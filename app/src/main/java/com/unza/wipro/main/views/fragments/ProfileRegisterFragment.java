package com.unza.wipro.main.views.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.paditech.core.BaseFragment;
import com.paditech.core.helper.ImageHelper;
import com.unza.wipro.R;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileRegisterFragment extends BaseFragment {
    public static int REQUEST_PHOTO_CAMERA = 100;
    public static int REQUEST_PHOTO_GALLERY = 200;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private String mCurrentPhotoPath;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("My Log : ", "Permission: " + permissions[0] + "was " + grantResults[0]);
            takePicture();
        }
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }
        startActivityForResult(takePictureIntent, REQUEST_PHOTO_CAMERA);
    }

    @OnClick(R.id.btnCamera)
    protected void openCamera() {

        boolean hasPermission = (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        Log.e("Check permission : ", "" + hasPermission);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 112);
        } else {
            takePicture();
        }
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO_CAMERA) {
            galleryAddPic();
            ImageHelper.loadThumbCircleImage(this.getContext(), mCurrentPhotoPath, imgAvatar);
            mCurrentPhotoPath = null;
        }
        if (requestCode == REQUEST_PHOTO_GALLERY) {
            Uri imageUri = data.getData();
            ImageHelper.loadThumbCircleImage(this.getContext(), imageUri.toString(), imgAvatar);
        }
        slideDown();
    }

    public void slideUp() {
        layoutDisable.setVisibility(View.VISIBLE);
        layoutDisable.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                slideDown();
                return true;
            }
        });
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
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                layoutControllSelectImage.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        layoutControllSelectImage.startAnimation(animate);
        layoutDisable.setVisibility(View.INVISIBLE);

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                storageDir = new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_PICTURES), "CameraSample");
            } else {
                new File(Environment.getExternalStorageDirectory() + "/dcim/" + "CameraSample");
            }
            Log.e("My log : storageDir : ", storageDir.getAbsolutePath());
            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.getActivity().sendBroadcast(mediaScanIntent);
    }
}
