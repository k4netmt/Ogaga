package com.ogaga.flash.extra;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaga.flash.R;

/**
 * Created by Kanet on 5/1/2016.
 */
public class ValiDate {

    Activity mActivity;
    public ValiDate(Activity activity){
        mActivity=activity;
    }
    public boolean validateAddress(TextInputLayout input,EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            input.setError(mActivity.getString(R.string.error_address_empty));
            requestFocus(input);
            return false;
        } else {
            input.setErrorEnabled(false);
        }
        return true;
    }
    public boolean validateImage(TextView input,Uri uri) {
        if (uri==null) {
            input.setVisibility(View.VISIBLE);
            input.setText(mActivity.getString(R.string.error_image_empty));
            requestFocus(input);
            return false;
        } else {
            input.setVisibility(View.INVISIBLE);
        }
        return true;
    }
    public boolean validatePhonenumber(TextInputLayout input,EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            input.setError(mActivity.getString(R.string.error_phonenumber_empty));
            requestFocus(input);
            return false;
        } else {
            input.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateCountProduct(TextInputLayout input,EditText editText) {
        long count=0;
        if(editText.getText().length()==0)
            count=0;
        else
            count=Long.parseLong(editText.getText().toString());
        if (count==0) {
            input.setError(mActivity.getString(R.string.error_countProduct_empty));
            requestFocus(input);
            return false;
        } else {
            input.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateDescription(TextInputLayout input,EditText editText,int limit,boolean checkEmpty) {
        if (checkEmpty==true){
            if (editText.getText().toString().trim().isEmpty()) {
                input.setError(mActivity.getString(R.string.error_description_empty));
                requestFocus(input);
                return false;
            }
        }
        if (editText.getText().length()>limit){
            input.setError(mActivity.getString(R.string.error_description_limited));
            requestFocus(input);
            return false;
        }
        input.setErrorEnabled(false);
        return true;
    }
    public boolean validateProductName(TextInputLayout input,EditText editText ) {
        if (editText.getText().toString().trim().isEmpty()) {
            input.setError(mActivity.getString(R.string.error_productname_empty));
            requestFocus(input);
            return false;
        } else {
            input.setErrorEnabled(false);
        }
        return true;
    }
    public boolean validateProductPrice(TextInputLayout input,EditText editText ) {
        if (editText.getText().toString().trim().isEmpty()) {
            input.setError(mActivity.getString(R.string.error_productprice_empty));
            requestFocus(input);
            return false;
        } else {
            input.setErrorEnabled(false);
        }
        return true;
    }
    public boolean validateCompareDate(TextInputLayout input,long startDate,long finishedDate) {
        if (startDate>finishedDate) {
            input.setError(mActivity.getString(R.string.error_datestart_than));
            requestFocus(input);
            return false;
        } else {
            input.setErrorEnabled(false);
        }
        return true;
    }
    public boolean validateDate(TextInputLayout input,EditText editText ) {
        if (editText.getText().toString().trim().isEmpty()) {
            input.setError(mActivity.getString(R.string.error_date_empty));
            requestFocus(input);
            return false;
        } else {
            input.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validatePassword(TextInputLayout input,EditText editText ) {
        if (editText.getText().toString().trim().isEmpty()) {
            input.setError(mActivity.getString(R.string.error_password_empty));
            requestFocus(input);
            return false;
        } else {
            input.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
