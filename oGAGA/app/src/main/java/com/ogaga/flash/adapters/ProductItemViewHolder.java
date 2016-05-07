package com.ogaga.flash.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaga.flash.R;
import com.ogaga.flash.acitivies.ProductDetailActivity;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by IceStone on 4/24/2016.
 */
public class ProductItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
   @Bind(R.id.ivProductImage)
    ImageView ivProductImage;
    @Bind(R.id.idAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvProductName)
    TextView tvProductName;
    @Bind(R.id.tvProducer)
    TextView tvProducer;
    @Bind(R.id.tvProductOrigin)
    TextView tvProductOrigin;
    @Bind(R.id.tvProductPrice)
    TextView tvProductPrice;
    @Bind(R.id.tvProductStatus)
    TextView tvProductStatus;
    @Bind(R.id.tvProductOrder)
    TextView tvProductOrder;
    Product mProduct;
    User mUser;

    public ProductItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Product product) {
        this.mProduct = product;
    }

    public void bindUser(User user) {
        mUser = user;
    }

    @Override
    public void onClick(View v) {
        if(mUser==null){
            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
            intent.putExtra("product", Parcels.wrap(mProduct));
            v.getContext().startActivity(intent);
        }
        if ( mUser!=null && mProduct!=null){
            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
            intent.putExtra("product", Parcels.wrap(mProduct));
            intent.putExtra("user", Parcels.wrap(mUser));
            v.getContext().startActivity(intent);
        }
    }

}
