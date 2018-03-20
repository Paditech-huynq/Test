package com.unza.wipro.main.contracts;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.paditech.core.mvp.BasePresenterImpl;
import com.paditech.core.mvp.BaseViewImpl;
import com.unza.wipro.main.models.News;

public interface ProfileContract {
    interface ViewImpl extends BaseViewImpl {
        void goToChangePassFragment();

        void goToOrderFragment();

        void goToListProfileFragment();

        void goToHomeProfile();

        void updateUI();

        void updateUIForCustomer();

        void updateUIForPromoter();

        void goToPolicyPermisWeb(News news);

        void goToQuestionWeb(News news);

        void slideUpInputImageArea();

        void startActivityForResultFromPresent(Intent takePictureIntent, int requestPhotoCamera);

        FragmentActivity getProfileActivity();

        String getCurrentPhotoPath();

        void setMCurrentPhotoPath(String mCurrentPhotoPath);

        String getCurrentUserName();

        String getCurrentAddress();

        void enableEditMode(boolean isEnable);

        boolean isEditing();

        void backToLastScreen();
    }

    interface Presenter extends BasePresenterImpl {
        void getUserDataFromServer();

        void onChangePassClick();

        void onListOrderClick();

        void onManagerSalesClick();

        void onLogOutClick();

        void onPolicyPermisClick();

        void onQuestionClick();

        void onChangeAvarClick();

        void onDoneButtonClick();

        void onUserCancelEdit();
    }
}
