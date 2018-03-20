package com.unza.wipro.main.views.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.core.BaseFragment;
import com.paditech.core.helper.ImageHelper;
import com.paditech.core.helper.StringUtil;
import com.unza.wipro.AppAction;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.models.UserData;
import com.unza.wipro.main.models.responses.CreateCustomerRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRegisterFragment extends BaseFragment implements AppConstans {
    public final static int REQUEST_PHOTO_CAMERA = 100;
    public final static int REQUEST_PHOTO_GALLERY = 200;
    public final static int REQUEST_WRITE_EXTERNAL_STORAGE = 112;
    public static final String JPEG_FILE_PREFIX = "IMG_";
    public static final String JPEG_FILE_SUFFIX = ".jpg";
    private String mCurrentPhotoPath;
    private boolean isPending;

    @BindView(R.id.edtUserName)
    EditText edtUserName;

    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

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
    public void initView() {
        super.initView();
        Utils.showKeyboard(getActivity());

        edtAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submitRegister();
                    Utils.hideSoftKeyboard(getActivity());
                    return true;
                }
                return false;
            }
        });
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
        if (permissions.length * grantResults.length == 0) {
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            Log.v("My Log : ", "Permission: " + permissions[0] + "was " + grantResults[0]);
            takePicture();
        }
        if (grantResults[0] == PackageManager.PERMISSION_DENIED && requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            showToast("Must allow write external storage pemission");
        }
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        try {
            f = setUpPhotoFile();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                Uri photoUri = FileProvider.getUriForFile(getContext(), "com.example.file.provider", f);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            }
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(takePictureIntent, REQUEST_PHOTO_CAMERA);
    }

    @OnClick(R.id.btnCamera)
    protected void openCamera() {
        Utils.checkCameraPermission(this.getActivity());
        boolean hasWritePermission = (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasWritePermission) {
            this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            takePicture();
        }
    }

    @OnClick(R.id.btnGallery)
    protected void openGallery() {
        boolean hasWritePermission = (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasWritePermission) {
            this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            startActivityForResult(Intent.createChooser(galleryIntent, getString(R.string.msg_select_image_from_gallery)), REQUEST_PHOTO_GALLERY);
        }
    }

    @OnClick(R.id.btnCancel)
    protected void openCancel() {
        slideDown();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO_CAMERA) {
            galleryAddPic();
            Log.e("onActivityResult: ", mCurrentPhotoPath);
            ImageHelper.loadThumbCircleImage(this.getContext(), mCurrentPhotoPath, imgAvatar);
        }
        if (requestCode == REQUEST_PHOTO_GALLERY) {
            Uri imageUri = data.getData();
            ImageHelper.loadThumbCircleImage(this.getContext(), imageUri.toString(), imgAvatar);

            mCurrentPhotoPath = getRealPathFromURI(imageUri);
        }
        slideDown();
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = "";
        try {
            Cursor cursor = getContext().getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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

    @OnClick(R.id.btnRegister)
    void submitRegister() {
        if (!app.isLogin()) {
            return;
        }
        if (dataIsValid()) {
            if (isPending) {
                return;
            }
            isPending = true;
            showProgressDialog(true);

            RequestBody name = MultipartBody.create(MultipartBody.FORM, edtUserName.getText().toString());
            RequestBody phone = MultipartBody.create(MultipartBody.FORM, edtPhoneNumber.getText().toString());
            RequestBody email = MultipartBody.create(MultipartBody.FORM, edtEmail.getText().toString());
            final RequestBody address = MultipartBody.create(MultipartBody.FORM, edtAddress.getText().toString());
            MultipartBody.Part body = null;
            if (mCurrentPhotoPath != null) {
                File file = new File(mCurrentPhotoPath);
                try {
                    File compressedImageFile = new Compressor(getContext())
                            .setMaxHeight(100)
                            .setMaxWidth(100)
                            .compressToFile(file);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("application/image"), compressedImageFile);
                    body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            AppClient.newInstance().getService().createCustomer(
                    app.getToken(),
                    app.getAppKey(),
                    name, phone, email, address, body)
                    .enqueue(new Callback<CreateCustomerRSP>() {
                        @Override
                        public void onResponse(Call<CreateCustomerRSP> call, Response<CreateCustomerRSP> response) {
                            try {
                                Log.e("testcreateCustomer", String.valueOf(response.code()));
                                isPending = false;
                                showProgressDialog(false);
                                if (response.body() != null) {
                                    if (response.body().getResult() == AppConstans.Api.Success) {
                                        CreateCustomerRSP createCustomerRSP = response.body();
                                        final UserData customer = createCustomerRSP.getCustomer();
                                        if (customer != null && getActivity() != null) {
                                            if (customer.getPassword() != null && !StringUtil.isEmpty(customer.getPassword())) {
                                                showAlertDialog(response.body().getMessage(),
                                                        getString(R.string.create_customer_pass, customer.getPassword()),
                                                        "OK",
                                                        null
                                                );
                                            }
                                            getActivity().getSupportFragmentManager().popBackStack();
                                            bus.post(AppAction.NOTIFY_CUSTOMER_SELECTED_AFTER_CREATE.setData(Utils.convertObjectToString(customer)));
                                        }
                                    } else {
                                        showToast(response.body().getMessage());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<CreateCustomerRSP> call, Throwable t) {
                            isPending = false;
                            showProgressDialog(false);
                        }
                    });
        }
    }

    private boolean dataIsValid() {
        String userName = edtUserName.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();
        String email = edtEmail.getText().toString();

        if (userName.length() * phoneNumber.length() == 0) {
            showToast("Họ tên và số điện thoại không được để trống");
            return false;
        } else if (!email.isEmpty() && !Utils.checkEmailValid(email)) {
            showToast("Email không hợp lệ");
            return false;
        }
        return true;
    }
}
