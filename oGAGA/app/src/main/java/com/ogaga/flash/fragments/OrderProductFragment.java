package com.ogaga.flash.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ogaga.flash.R;
import com.ogaga.flash.acitivies.ProductDetailActivity;
import com.ogaga.flash.extra.ValiDate;
import com.ogaga.flash.helpers.NotificationHelper;
import com.ogaga.flash.models.Order;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class OrderProductFragment extends DialogFragment{
    public interface OnOrderListener{
        void onOrder(Order count);
    }
    private OnOrderListener orderListener;
    public void setOnOrderListener(OnOrderListener onSaveListener){

        this.orderListener=onSaveListener;
    }
    public OrderProductFragment() {
        // Required empty public constructor
    }

    @Bind(R.id.tvPrices)TextView tvPrices;
    @Bind(R.id.tvTotal)TextView tvTotal;
    @Bind(R.id.tvUnit)TextView tvUnit;
    @Bind(R.id.tvCountDescription)TextView tvCountDescription;
    @Bind(R.id.etOrderCount)EditText etOrderCount;
    @Bind(R.id.etDesciption)EditText etDesciption;
    @Bind(R.id.btnOrder)Button btnOder;
    @Bind(R.id.rbtnAddressCurrent)RadioButton rbtnAddressCurrent;
    @Bind(R.id.rbtnAddressOthers)RadioButton rbtnAddressOthers;
    @Bind(R.id.rbtnPhoneCurrent)RadioButton rbtnPhoneCurrent;
    @Bind(R.id.rbtnPhoneOthers)RadioButton rbtnPhoneOthers;
    @Bind(R.id.etAdressUser)EditText etAddressUser;
    @Bind(R.id.etPhoneNumber)EditText etPhoneNumber;
    @Bind(R.id.rbtnShippingCOD)RadioButton rbtnShippingCOD;
    @Bind(R.id.tilAddressUser)TextInputLayout tilAddressUser;
    @Bind(R.id.tilPhonenumber)TextInputLayout tilPhonenumber;
    @Bind(R.id.tilCountProduct)TextInputLayout tilCountProduct;
    //@Bind(R.id.rGrpPhone)RadioGroup rGrpPhone;
    TextWatcher twetCountProduc;
    TextWatcher twetCountDesciption;
    Product mProduct;
    User mUser;
    ValiDate mValiDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_order_product, container, false);
        ButterKnife.bind(this, rootView);
        mProduct= Parcels.unwrap(getArguments().getParcelable("product"));
        mUser=Parcels.unwrap(getArguments().getParcelable("user"));
        CharSequence titles=mProduct.getName();
        getDialog().setTitle(mProduct.getName() + " - " + mProduct.getUserSell().getFullname());
        getActivity().setTitle(titles);
        mValiDate=new ValiDate(getActivity());
        setupOrder();
        return rootView;
    }

  @OnCheckedChanged(R.id.rbtnPhoneOthers)
    public void onCheckedChangedPhoneOthers(boolean ischecked){
      etPhoneNumber.setEnabled(rbtnPhoneOthers.isChecked());
      if (rbtnPhoneCurrent.isChecked()==true){
          etPhoneNumber.setText(mUser.getPhonenumber());
      }else{
          etPhoneNumber.setText("");

      }
    }

    @OnCheckedChanged(R.id.rbtnAddressOthers)
    public void onCheckedChangedAddressOthers(boolean ischecked){
        etAddressUser.setEnabled(rbtnAddressOthers.isChecked());
        if (rbtnAddressCurrent.isChecked()==true){
            etAddressUser.setText(mUser.getAddress_user().toString());
        }else {
            etAddressUser.setText("");
        }
    }

    private void setupOrder() {
        tvPrices.setText(String.valueOf(mProduct.getPrices()));
        etAddressUser.setText(mUser.getAddress_user().toString());
        etPhoneNumber.setText(mUser.getPhonenumber());
        etOrderCount.setText("1");
        long count= etOrderCount.getText().length()==0 ? 0: Long.parseLong(etOrderCount.getText().toString());
        long total = mProduct.getPrices()*count;
        tvTotal.setText(String.valueOf(total));
        etAddressUser.setEnabled(rbtnAddressOthers.isChecked());
        etPhoneNumber.setEnabled(rbtnPhoneOthers.isChecked());
        String[] mTestArray = getResources().getStringArray(R.array.product_unit);;
        tvUnit.setText(mTestArray[(int)mProduct.getId_unit()-1]);
        twetCountProduc=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                long countProduct=0;
                if(etOrderCount.getText().length()==0)
                    countProduct=0;
                else
                    countProduct=Long.parseLong(etOrderCount.getText().toString());
                long total = mProduct.getPrices()*countProduct;
                tvTotal.setText(String.valueOf(total));
            }
        };
        twetCountDesciption=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                long countDescription= etDesciption.getText().length();
                long total = 350-countDescription;
                tvCountDescription.setText(String.valueOf(total));
            }
        };

        etOrderCount.addTextChangedListener(twetCountProduc);
        etDesciption.addTextChangedListener(twetCountDesciption);
    }

    @OnClick(R.id.btnOrder)
    public void onOrderClick(View view){
        if (mValiDate.validateCountProduct(tilCountProduct, etOrderCount)==false)
            return;
        if(mValiDate.validateAddress(tilAddressUser, etAddressUser) == false)
            return;
        if (mValiDate.validatePhonenumber(tilPhonenumber, etPhoneNumber)==false)
            return;
        if (mValiDate.validateDescription(tilPhonenumber, etPhoneNumber, 350,false)==false)
            return;
        Order order=new Order();
        order.setCount(Integer.parseInt(etOrderCount.getText().toString()));
        order.setAddress_Shipping(etAddressUser.getText().toString());
        order.setPhonenumber_Shipping(etPhoneNumber.getText().toString());
        order.setId_shipping_status(1);
        order.setPrices(mProduct.getPrices());
        order.setDescription(etDesciption.getText().toString());
        order.setbFlag_push(true);
        orderListener.onOrder(order);
        getDialog().dismiss();
    }

}
