package com.pshop.app.main.views.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.paditech.core.DisableTouchView;
import com.paditech.core.helper.StringUtil;
import com.paditech.core.image.GlideApp;
import com.paditech.core.mvp.MVPFragment;
import com.pshop.app.AppConstans;
import com.pshop.app.R;
import com.pshop.app.main.contracts.ProfileContract;
import com.pshop.app.main.models.News;
import com.pshop.app.main.presenters.ProfilePresenter;
import com.pshop.app.main.views.activities.MainActivity;
import com.pshop.app.main.views.customs.DegreeView;
import com.pshop.app.transaction.user.Customer;
import com.pshop.app.transaction.user.Promoter;
import com.pshop.app.transaction.user.PromoterLeader;
import com.pshop.app.utils.DateTimeUtils;
import com.pshop.app.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.pshop.app.main.presenters.ProfilePresenter.REQUEST_PHOTO_CAMERA;

public class ProfileFragment extends MVPFragment<ProfilePresenter> implements ProfileContract.ViewImpl, AppConstans {
    @BindView(R.id.layoutEdit)
    View layoutEdit;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.ln_degree)
    LinearLayout lnDegree;
    @BindView(R.id.ln_manager_sales)
    LinearLayout lnManagerSales;
    @BindView(R.id.rltEditName)
    RelativeLayout rltEditName;
    @BindView(R.id.rltEditAddress)
    RelativeLayout rltEditAddress;
    @BindView(R.id.tx_point)
    TextView tvPoint;
    @BindView(R.id.tx_numbers_point)
    TextView tvNumberPoint;
    @BindView(R.id.tx_number_sales)
    TextView tvNumberSales;
    @BindView(R.id.img_avar)
    ImageView imgAvar;
    @BindView(R.id.img_avar_under)
    ImageView imgAvarUnder;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_time_profile)
    TextView tvTime;
    @BindView(R.id.tv_sales_expect)
    TextView tvSalesExpect;
    @BindView(R.id.tv_sales_actual)
    TextView tvSalesActual;
    @BindView(R.id.degree_sale)
    DegreeView degreeSale;
    @BindView(R.id.layoutLoading)
    DisableTouchView layoutLoading;
    @BindView(R.id.layoutDisable)
    View layoutDisable;
    @BindView(R.id.layoutControllSelectImage)
    View layoutControllSelectImage;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.edtName)
    EditText edtName;

    private final static int REQUEST_PHOTO_GALLERY = 200;
    private final static int REQUEST_WRITE_EXTERNAL_STORAGE = 112;
    private final static String LAST_SCROLL_Y = "last_scroll_y";
    private boolean isEditing = false;
    private String mCurrentPhotoPath;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    public String getScreenTitle() {
        return getString(R.string.title_profile);
    }

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveViewInstanceState(@NonNull Bundle outState) {
        super.onSaveViewInstanceState(outState);
        outState.putInt(LAST_SCROLL_Y, (int) scrollView.getY());

    }

    @Override
    public void onRestoreViewState(Bundle savedInstanceState) {
        super.onRestoreViewState(savedInstanceState);
        if (savedInstanceState != null) {
            scrollView.scrollBy(0, savedInstanceState.getInt(LAST_SCROLL_Y));
        }
    }

    @Override
    public void goToChangePassFragment() {
        switchFragment(UpdatePasswordFragment.newInstance(), true);
    }

    @Override
    public void goToOrderFragment() {
        switchFragment(OrderListFragment.newInstance(OrderListFragment.COME_FROM_PROFILE_FRAGMENT), true);
    }

    @Override
    public void goToListProfileFragment() {
        switchFragment(ProfilePromoterListFragment.newInstance(), true);
    }

    @Override
    public void goToHomeProfile() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void updateUI() {
        tvName.setText(app.getCurrentUser().getName());
        tvEmail.setText(app.getCurrentUser().getEmail());
        tvAddress.setText(app.getCurrentUser().getAddress());
        tvPhone.setText(app.getCurrentUser().getPhone());
        tvNumberSales.setText(app.getCurrentUser().getNumberOrders());
        try {
            if (!isEditing) {
                GlideApp.with(this).load(app.getCurrentUser().getAvatar()).placeholder(R.drawable.ic_avatar_holder)
                        .error(R.drawable.ic_avatar_holder)
                        .circleCrop().into(imgAvar);
                GlideApp.with(this).load(app.getCurrentUser().getAvatar()).placeholder(R.drawable.ic_avatar_holder)
                        .error(R.drawable.ic_avatar_holder)
                        .into(imgAvarUnder);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void updateUIForCustomer() {
        tvPoint.setText(getResources().getString(R.string.point_profile_fragment));
        tvNumberPoint.setText(((Customer) app.getCurrentUser()).getPoint());
    }

    @Override
    public void updateUIForPromoter() {
        Promoter promoter = (Promoter) app.getCurrentUser();
        tvPoint.setText(getResources().getString(R.string.custom_profile_fragment));
        tvNumberPoint.setText(promoter.getNumberCustomers());
        if(Long.parseLong(promoter.getSalesExpect()) != 0) {
            tvSalesExpect.setText(getResources().getString(R.string.value_sales_profile_fragment,StringUtil.formatMoney(promoter.getSalesExpect())));
        } else {
            tvSalesExpect.setText(getResources().getString(R.string.sales_expect_is_zero_profile_fragment));
        }
        tvSalesActual.setText(getResources().getString(R.string.value_sales_profile_fragment,
                StringUtil.formatMoney(promoter.getSalesActual())));
        degreeSale.setValue(R.color.white, R.color.colorPrimary, Long.parseLong(promoter.getSalesActual()), Long.parseLong(promoter.getSalesExpect()));
        tvTime.setText(Html.fromHtml(getResources().getString(R.string.value_time_profile_fragment,
                DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(promoter.getFrom())),
                DateTimeUtils.getStringDayMonthYear(DateTimeUtils.getDateFromServerDayMonthYear(promoter.getTo())))));

        lnDegree.setVisibility(View.VISIBLE);
        if (app.getCurrentUser() instanceof PromoterLeader) {
            lnManagerSales.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void goToPolicyPermisWeb(News news) {
        switchFragment(WebViewFragment.newInstance(news.getContent(), news.getTitle()), true);
    }

    @Override
    public void goToQuestionWeb(News news) {
        switchFragment(WebViewFragment.newInstance(news.getContent(), news.getTitle()), true);
    }

    @Override
    public void slideUpInputImageArea() {
        Utils.slideUp(layoutDisable, layoutControllSelectImage, 0, 0, 0, layoutControllSelectImage.getHeight(), 5000L);
    }

    @Override
    public void startActivityForResultFromPresent(Intent takePictureIntent, int requestPhotoCamera) {
        startActivityForResult(takePictureIntent, requestPhotoCamera);
    }

    @OnClick(R.id.rlt_logout)
    protected void logout() {
        showConfirmDialog(getString(R.string.confirm_logout), getString(R.string.action_confirm), getString(R.string.action_cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getPresenter().onLogOutClick();
                    }
                }, null);
    }

    @OnClick(R.id.rlt_change_pass)
    public void onChangePassClick() {
        getPresenter().onChangePassClick();
    }

    @OnClick(R.id.rlt_list_order)
    public void onListOrderClick() {
        getPresenter().onListOrderClick();
    }

    @OnClick(R.id.rlt_manager_sales)
    public void onManagerSalesClick() {
        getPresenter().onManagerSalesClick();
    }

    @OnClick(R.id.rlt_policy_permis)
    public void onPolicyPermisClick() {
        getPresenter().onPolicyPermisClick();
    }

    @OnClick(R.id.rlt_questions)
    public void onQuestionClick() {
        getPresenter().onQuestionClick();
    }

    @OnClick(R.id.img_avar)
    public void onChangeAvarClick() {
        Utils.checkCameraPermission(this.getActivity());
        getPresenter().onChangeAvarClick();
    }

    @Override
    public void showProgressDialog(boolean isShown) {
        layoutLoading.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length * grantResults.length == 0) {
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            Log.v("My Log : ", "Permission: " + permissions[0] + "was " + grantResults[0]);
            getPresenter().takePicture();
        }
        if (grantResults[0] == PackageManager.PERMISSION_DENIED && requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            showToast("Must allow write external storage pemission");
        }
    }

    @OnClick(R.id.btnCamera)
    protected void openCamera() {
        Utils.checkCameraPermission(this.getActivity());
        if (getActivity() == null) {
            return;
        }
        boolean hasWritePermission = (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasWritePermission) {
            this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            getPresenter().takePicture();
        }
    }

    @OnClick(R.id.btnGallery)
    protected void openGallery() {
        if (getActivity() == null) {
            return;
        }
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
        Utils.slideDown(layoutDisable, layoutControllSelectImage, 0, 0, layoutControllSelectImage.getHeight(), 0, 5000L);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO_CAMERA) {
            getPresenter().addImageToSystem(mCurrentPhotoPath);
        }
        if (requestCode == REQUEST_PHOTO_GALLERY) {
            Uri imageUri = data.getData();
            mCurrentPhotoPath = getPresenter().getRealPathFromURI(imageUri);
        }
        if (getContext() != null) {
            GlideApp.with(this).load(mCurrentPhotoPath).placeholder(R.drawable.ic_avatar_holder)
                    .error(R.drawable.ic_avatar_holder)
                    .circleCrop().into(imgAvar);
            GlideApp.with(this).load(mCurrentPhotoPath).placeholder(R.drawable.ic_avatar_holder)
                    .error(R.drawable.ic_avatar_holder)
                    .into(imgAvarUnder);
            Utils.slideDown(layoutDisable, layoutControllSelectImage, 0, 0, layoutControllSelectImage.getHeight(), 0, 1000L);
            enableEditMode(requestCode == REQUEST_PHOTO_GALLERY || requestCode == REQUEST_PHOTO_CAMERA);
        }
    }

    public FragmentActivity getProfileActivity() {
        return getActivity();
    }

    @Override
    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    @Override
    public void setMCurrentPhotoPath(String mCurrentPhotoPath) {
        this.mCurrentPhotoPath = mCurrentPhotoPath;
    }

    @Override
    public String getCurrentUserName() {
        return edtName.getText().toString();
    }

    @Override
    public String getCurrentAddress() {
        return edtAddress.getText().toString();
    }

    @Override
    public void onBackPress() {
        if (isEditing) {
            getPresenter().onUserCancelEdit();
        } else {
            super.onBackPress();
        }
    }

    @Override
    public boolean isActionShow(int resId) {
        switch (resId) {
            case R.id.btnEdit:
                return !isEditing && app.getCurrentUser() instanceof Customer;
            case R.id.btnDone:
                return isEditing;
        }
        return false;
    }

    @Override
    public void onActionSelected(int resId) {
        super.onActionSelected(resId);
        switch (resId) {
            case R.id.btnEdit:
                enableEditMode(true);
                break;
            case R.id.btnDone:
                getPresenter().onDoneButtonClick();
                break;
        }
    }

    @Override
    public void enableEditMode(boolean isEnable) {
        if (isEnable) {
            edtName.setText(tvName.getText());
            edtAddress.setText(tvAddress.getText());
        } else {
            tvAddress.setText(app.getCurrentUser().getAddress());
            tvName.setText(app.getCurrentUser().getName());
            if (getContext() != null) {
                GlideApp.with(this).load(app.getCurrentUser().getAvatar()).placeholder(R.drawable.ic_avatar_holder)
                        .error(R.drawable.ic_avatar_holder)
                        .circleCrop().into(imgAvar);
                GlideApp.with(this).load(app.getCurrentUser().getAvatar()).placeholder(R.drawable.ic_avatar_holder)
                        .error(R.drawable.ic_avatar_holder)
                        .into(imgAvarUnder);
            }
        }
        layoutEdit.setVisibility(isEnable ? View.VISIBLE : View.GONE);
        if (app.getCurrentUser() instanceof Customer) {
            tvName.setVisibility(!isEnable ? View.VISIBLE : View.GONE);
            tvAddress.setVisibility(!isEnable ? View.VISIBLE : View.GONE);
            edtName.requestFocus();
        } else if (app.getCurrentUser() instanceof Promoter) {
            rltEditName.setVisibility(View.GONE);
            rltEditAddress.setVisibility(View.GONE);
        }

        isEditing = isEnable;
        if (getActivity() != null) {
            ((MainActivity) getActivity()).updateActionButtonAppearance(this);
        }

    }

    @Override
    public boolean isEditing() {
        return isEditing;
    }

    @Override
    public void backToLastScreen() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }
}
