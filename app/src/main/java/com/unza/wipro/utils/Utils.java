package com.unza.wipro.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.unza.wipro.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {
    private static final int DURATION_TIME_DEFAULT = 500;

    public static TransitionDrawable getTransitionChangeColor(int colorBefore, int colorAfter){
        ColorDrawable[] color = {new ColorDrawable(colorBefore), new ColorDrawable(colorAfter)};
        TransitionDrawable trans = new TransitionDrawable(color);
        trans.startTransition(DURATION_TIME_DEFAULT);
        return trans;
    }

    public static void checkCameraPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    public static void dismissSoftKeyboard(final View view, final Activity activity) {
        if (activity == null) return;
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }

            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                dismissSoftKeyboard(innerView, activity);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity == null) return;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            view.clearFocus();
        }
    }

    public static void hideSoftKeyboard(View view, Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }

    public static void setStatusBarTranslucent(boolean makeTranslucent, Activity activity) {
        Window window = activity.getWindow();

        if (makeTranslucent) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static String getTimeCreated(Context context, long created) {
        final long seconds = 1000;
        long diff = Math.abs(System.currentTimeMillis() - created * 1000L);

        if (diff < seconds * 3) {
            return context.getString(R.string.just_now);
        } else if (diff < seconds * 60) {
            int d = (int) (diff / seconds);
            return String.format(context.getString(R.string.some_seconds_ago), String.valueOf(d));
        } else if (diff < seconds * 60 * 2) {
            return context.getString(R.string.one_minute_ago);
        } else if (diff < seconds * 60 * 60) {
            int d = (int) (diff / (seconds * 60));
            return String.format(context.getString(R.string.some_minute_ago), String.valueOf(d));
        } else if (diff < seconds * 60 * 60 * 2) {
            return context.getString(R.string.one_hour_ago);
        } else if (diff < seconds * 60 * 60 * 12) {
            int d = (int) (diff / (seconds * 60 * 60));
            return String.format(context.getString(R.string.some_hour_ago), String.valueOf(d));
        } else if (diff > seconds * 60 * 60 * 24 * 365) {
            Date date = new Date(created * 1000L);
            SimpleDateFormat format = new SimpleDateFormat("dd MMM, yy");
            return format.format(date);
        }
        Date date = new Date(created * 1000L);
        Date curr = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar now = Calendar.getInstance();
        calendar.setTime(curr);
        if (now.get(Calendar.YEAR) > calendar.get(Calendar.YEAR)) {
            SimpleDateFormat format = new SimpleDateFormat("dd MMM, yy");
            return format.format(date);
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd MMM");
            return format.format(date);
        }
    }

    public static boolean checkEmailValid(String email) {
        return Pattern.compile(".+@.+\\.[a-z]+").matcher(email).matches();
    }

    public static void showKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
