package com.unza.wipro.main.presenters;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.paditech.core.mvp.BasePresenter;
import com.unza.wipro.AppConstans;
import com.unza.wipro.R;
import com.unza.wipro.main.contracts.ProfileContract;
import com.unza.wipro.main.models.News;
import com.unza.wipro.main.models.responses.ChangeInformationRSP;
import com.unza.wipro.main.models.responses.GetUserProfileRSP;
import com.unza.wipro.services.AppClient;
import com.unza.wipro.transaction.user.Customer;
import com.unza.wipro.transaction.user.Promoter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter extends BasePresenter<ProfileContract.ViewImpl> implements ProfileContract.Presenter, AppConstans {

    private static final String URL_POLICY_PERMIS = "http://wipro.crm.admin.paditech.com/app/policy.html";
    private static final String URL_FAQ = "http://wipro.crm.admin.paditech.com/app/faq.html";
    public final static int REQUEST_PHOTO_CAMERA = 100;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    @Override
    public void onPolicyPermisClick() {
        News news = new News(URL_POLICY_PERMIS, getView().getContext().getResources().getString(R.string.title_policy_permis));
        getView().goToPolicyPermisWeb(news);
    }

    @Override
    public void onQuestionClick() {
        News news = new News(URL_FAQ, getView().getContext().getResources().getString(R.string.title_question));
        getView().goToQuestionWeb(news);
    }

    @Override
    public void onChangeAvarClick() {
        getView().slideUpInputImageArea();
    }


    @Override
    public void onDoneButtonClick() {
        requestUpdateUserToServer();
    }

    @Override
    public void onUserCancelEdit() {
//        getView().showConfirmDialog(getView().getContext().getString(R.string.msg_cornfirm_cancel_edit), getView().getContext().getString(R.string.ok), getView().getContext().getString(R.string.cancel)
//                , new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        ///todo: show edit button
                        getView().enableEditMode(false);
//                        getView().backToLastScreen();
//                    }
//                }, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
    }


    @Override
    public void getUserDataFromServer() {
        if (app.getCurrentUser() == null) {
            return;
        }
        AppClient.newInstance().getService().getUserProfile(app.getToken(),
                app.getAppKey()).enqueue(new Callback<GetUserProfileRSP>() {
            @Override
            public void onResponse(Call<GetUserProfileRSP> call, Response<GetUserProfileRSP> response) {
                try {
                    Log.e("testgetUserProfile", String.valueOf(response.code()));
                    app.updateCurrentUser(response.body().getUser());
                    updateUi();
                    getView().showProgressDialog(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetUserProfileRSP> call, Throwable t) {
                try {
                    getView().showProgressDialog(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onChangePassClick() {
        getView().goToChangePassFragment();
    }

    @Override
    public void onListOrderClick() {
        getView().goToOrderFragment();
    }

    @Override
    public void onManagerSalesClick() {
        getView().goToListProfileFragment();
    }

    @Override
    public void onLogOutClick() {
        app.logout();
        getView().goToHomeProfile();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getUserDataFromServer();
    }

    @Override
    public void onViewAppear() {
        super.onViewAppear();
        updateUi();
    }

    private void updateUi() {
        getView().updateUI();
        if (app.getCurrentUser() instanceof Customer) {
            getView().updateUIForCustomer();
        }
        if (app.getCurrentUser() instanceof Promoter) {
            getView().updateUIForPromoter();
        }
    }

    private MultipartBody.Part getCurrentAvatar() {
        MultipartBody.Part body = null;
        if (getView().getCurrentPhotoPath() != null) {
            File file = new File(getView().getCurrentPhotoPath());
            try {
                File compressedImageFile = new Compressor(getView().getContext())
                        .setMaxHeight(100)
                        .setMaxWidth(100)
                        .compressToFile(file);
                RequestBody requestFile = RequestBody.create(MediaType.parse("application/image"), compressedImageFile);
                body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    private void requestUpdateUserToServer() {
        if (!app.isLogin()) {
            return;
        }
        getView().showProgressDialog(true);

        final RequestBody requestName = MultipartBody.create(MultipartBody.FORM, getView().getCurrentUserName());
        final RequestBody requestAddress = MultipartBody.create(MultipartBody.FORM, getView().getCurrentAddress());

        AppClient.newInstance().getService().changeInformation(
                app.getToken(),
                app.getAppKey(),
                requestName, requestAddress, getCurrentAvatar())
                .enqueue(new Callback<ChangeInformationRSP>() {
                    @Override
                    public void onResponse(Call<ChangeInformationRSP> call, Response<ChangeInformationRSP> response) {
                        try {
                            Log.e("changeInformation", String.valueOf(response.code()));
                            getView().showProgressDialog(false);
                            if (response.isSuccessful() && response.body().isSuccess()) {
                                app.updateCurrentUser(response.body().getData());
                                getView().showToast(response.body().getMessage());
                            } else {
                                getView().showToast(response.body().getMessage());
                            }
                            getView().enableEditMode(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangeInformationRSP> call, Throwable t) {
                        try {
                            getView().showProgressDialog(false);
                            getView().showToast(t.getLocalizedMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f;
        try {
            f = setUpPhotoFile();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                Uri photoUri = FileProvider.getUriForFile(getView().getContext(), "com.example.file.provider", f);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            }
        } catch (IOException e) {
            e.printStackTrace();
            getView().setMCurrentPhotoPath(null);
        }
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        getView().startActivityForResultFromPresent(takePictureIntent, REQUEST_PHOTO_CAMERA);
    }


    public String getRealPathFromURI(Uri contentURI) {
        String result = "";
        try {
            Cursor cursor = getView().getContext().getContentResolver().query(contentURI, null, null, null, null);
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        return File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
    }

    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        getView().setMCurrentPhotoPath(f.getAbsolutePath());
        return f;
    }

    private File getAlbumDir() {
        File storageDir = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_PICTURES), "CameraSample");
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d("CameraSample", "failed to create directory");
                    return null;
                }
            }
        } else {
            Log.v(getView().getContext().getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }
        return storageDir;
    }

    public void addImageToSystem(String mCurrentPhotoPath) {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getView().getProfileActivity().sendBroadcast(mediaScanIntent);
    }

}
