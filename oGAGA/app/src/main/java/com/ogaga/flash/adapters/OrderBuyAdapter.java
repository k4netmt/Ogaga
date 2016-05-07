package com.ogaga.flash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.extra.CalculatProgressProduct;
import com.ogaga.flash.extra.Constant;
import com.ogaga.flash.models.Order;
import com.ogaga.flash.models.Product;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.transform.Templates;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kanet on 5/1/2016.
 */
public class OrderBuyAdapter extends FirebaseRecyclerAdapter<Order,OrderBuyViewHolder> {
    Context context;

    public OrderBuyAdapter(Query query, Context parentContext) {
        super(Order.class, R.layout.order_item, OrderBuyViewHolder.class, query);
        context = parentContext;
    }

    @Override
    protected void populateViewHolder(final OrderBuyViewHolder viewHolder, Order order, int i) {
        Product product=order.getProduct();
        Picasso.with(context).load(product.getUrl()).into(viewHolder.ivProductImage);
        Picasso.with(context).load(product.getUserSell().getProfile_image()).placeholder(R.drawable.ic_action_user).into(viewHolder.ivAvatar);
        viewHolder.tvProductName.setText(product.getName());
        viewHolder.tvProducer.setText(product.getUserSell().getFullname());
        viewHolder.tvProductOrigin.setText(product.getUserSell().getAddress_user());
        viewHolder.tvPhonenumber.setText(product.getUserSell().getPhonenumber());
        viewHolder.tvProductOrder.setText(order.getCount().toString());
        viewHolder.tvDescription.setText(order.getDescription());
        String myFormat = "dd/MM/yyyy"; // your format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        viewHolder.tvOrderDate.setText(sdf.format(order.getCreated_at()));
    }
}