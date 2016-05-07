package com.ogaga.flash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.extra.CalculatProgressProduct;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.User;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by carot on 4/23/2016.
 */
public class ProductAdapter extends FirebaseRecyclerAdapter<Product,ProductItemViewHolder> {


    private Context context;
    public ProductAdapter(Query ref, Context parentContext ) {
        super(Product.class, R.layout.product_item, ProductItemViewHolder.class, ref);
        context = parentContext;
    }
    @Override
    protected void populateViewHolder(final ProductItemViewHolder viewHolder, Product product, int i) {
        Picasso.with(context).load(product.getUrl()).placeholder(R.drawable.im_placeholder).into(viewHolder.ivProductImage);
        Picasso.with(context).load(product.getUserSell().getProfile_image()).placeholder(R.drawable.ic_action_user).into(viewHolder.ivAvatar);
        viewHolder.tvProductName.setText(product.getName());
        viewHolder.tvProducer.setText(product.getUserSell().getFullname());
        viewHolder.tvProductOrigin.setText(product.getUserSell().getAddress_user());
        String[] arrUnits = context.getResources().getStringArray(R.array.product_unit);
        String unit=arrUnits[(int)product.getId_unit()-1];
        String price=product.getPrices()+" VND /"+unit;
        viewHolder.tvProductPrice.setText(price);
        CalculatProgressProduct.CalculatProgress(context, product);
        String status=CalculatProgressProduct.disDate+ " "+CalculatProgressProduct.nextStatus;
        viewHolder.tvProductStatus.setText(status);
        Firebase firebaseOrder= FirebaseClient.getOrders();
        Query query =firebaseOrder.orderByChild("product/id_product").equalTo(product.getId_product());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                viewHolder.tvProductOrder.setText(String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
