package com.ogaga.flash.adapters;

import android.content.Context;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.extra.TimeStamp;
import com.ogaga.flash.models.Order;
import com.ogaga.flash.models.Product;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Kanet on 5/5/2016.
 */
public class OrderSellAdapter  extends FirebaseRecyclerAdapter<Order,OrderBuyViewHolder> {
    Context context;

    public OrderSellAdapter(Query query, Context parentContext) {
        super(Order.class, R.layout.order_item, OrderBuyViewHolder.class, query);
        context = parentContext;
    }

    @Override
    protected void populateViewHolder(final OrderBuyViewHolder viewHolder, Order order, int i) {
        Product product=order.getProduct();
        Picasso.with(context).load(product.getUrl()).into(viewHolder.ivProductImage);
        Picasso.with(context).load(order.getUserBuy().getProfile_image()).placeholder(R.drawable.ic_action_user).into(viewHolder.ivAvatar);
        viewHolder.tvProductName.setText(product.getName());
        viewHolder.tvProducer.setText(order.getUserBuy().getFullname());
        viewHolder.tvProductOrigin.setText(order.getUserBuy().getAddress_user());
        viewHolder.tvPhonenumber.setText(order.getUserBuy().getPhonenumber());
        viewHolder.tvProductOrder.setText(order.getCount().toString());
        viewHolder.tvDescription.setText(order.getDescription());
        Date date=new Date(order.getCreated_at());
        String timeDate= TimeStamp.getDistanceTime(order.getCreated_at(),TimeStamp.FULL_TIME);
        String myFormat = "dd/MM/yyyy"; // your format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        viewHolder.tvOrderDate.setText(sdf.format(order.getCreated_at()));
    }
}
