package com.ogaga.flash.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaga.flash.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kanet on 5/5/2016.
 */
public class OrderBuyViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tvProductName)TextView tvProductName;
    @Bind(R.id.ivAvatar)ImageView ivAvatar;
    @Bind(R.id.tvProducer)TextView tvProducer;
    @Bind(R.id.tvPhonenumber)TextView tvPhonenumber;
    @Bind(R.id.tvProductOrigin)TextView tvProductOrigin;
    @Bind(R.id.ivProductImage)ImageView ivProductImage;
    @Bind(R.id.tvOrderDate)TextView tvOrderDate;
    @Bind(R.id.tvProductOrder)TextView tvProductOrder;
    @Bind(R.id.tvDescription)TextView tvDescription;
    @Bind(R.id.btnAccept)Button btnAccept;
    @Bind(R.id.btnNotAccept)Button btnNotAccept;
    public OrderBuyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


    }

    @OnClick(R.id.btnAccept)
    public void onAccepnt(View view){
        Toast.makeText(view.getContext(),view.getContext().getString(R.string.comming_soon),Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnNotAccept)
    public void onNotAccepnt(View view){
        Toast.makeText(view.getContext(),view.getContext().getString(R.string.comming_soon),Toast.LENGTH_SHORT).show();
    }
}
