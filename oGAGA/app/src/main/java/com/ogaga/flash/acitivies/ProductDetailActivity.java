package com.ogaga.flash.acitivies;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Query;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.extra.CalculatProgressProduct;
import com.ogaga.flash.fragments.OrderProductFragment;
import com.ogaga.flash.fragments.OrderViewFragment;
import com.ogaga.flash.models.DontLike;
import com.ogaga.flash.models.Like;
import com.ogaga.flash.models.Order;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {
    Product mProduct;
    User mUser;

    @Bind(R.id.ivDontLike)ImageView ivDontLike;
    @Bind(R.id.ivLike)ImageView ivLike;
    @Bind(R.id.ivProductDetailImage)
    ImageView ivProductDetailImage;
    @Bind(R.id.tvProductName)TextView tvProductName;
    @Bind(R.id.tvProductDetailDesciption)
    TextView tvProductDetailDescription;
    @Bind(R.id.tvProductUnit)TextView tvProductUnit;
    @Bind(R.id.tvProductPrice)
    TextView tvProductDetailPrice;
    @Bind(R.id.ivProducerAvatar)
    ImageView ivProducerPhoto;
    @Bind(R.id.tvAddressProduct)TextView tvAddressProduct;
    @Bind(R.id.tvFullName)TextView tvFullName;
    @Bind(R.id.toolbarHeader)
    Toolbar mToolbar;
    @Bind(R.id.tvCountDownStatus)TextView tvCountDownStatus;
    @Bind(R.id.tvGotoStatus)TextView tvGotoStatus;
    @Bind(R.id.tvCountOrder)TextView tvCountOrder;
    @Bind(R.id.tvCountLike)TextView tvCountLike;
    @Bind(R.id.tvCountDontLike)TextView tvCountDontLike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        mProduct = Parcels.unwrap(getIntent().getParcelableExtra("product"));
        mUser = Parcels.unwrap(getIntent().getParcelableExtra("user"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupContent();
    }

    private void setupContent() {
        Picasso.with(getApplicationContext()).load(mProduct.getUrl()).placeholder(R.drawable.im_placeholder).into(ivProductDetailImage);
        Picasso.with(getApplicationContext()).load(mProduct.getUserSell().getProfile_image()).placeholder(R.drawable.im_placeholder).into(ivProducerPhoto);
        String[] arrUnits = getResources().getStringArray(R.array.product_unit);
        tvProductUnit.setText(arrUnits[(int) mProduct.getId_unit() - 1]);
        tvProductName.setText(mProduct.getName());
        tvFullName.setText(mProduct.getUserSell().getFullname());
        tvAddressProduct.setText(mProduct.getUserSell().getAddress_user());
        tvProductDetailPrice.setText(String.valueOf(mProduct.getPrices())+ " VND");

        CalculatProgressProduct.CalculatProgress(getApplicationContext(), mProduct);
        tvCountDownStatus.setText(CalculatProgressProduct.disDate);
        tvGotoStatus.setText(CalculatProgressProduct.nextStatus);

        Firebase firebaseOrder= FirebaseClient.getOrders();
        Query query =firebaseOrder.orderByChild("product/id_product").equalTo(mProduct.getId_product());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvCountOrder.setText(String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase firebaseLikes=FirebaseClient.getLikes();
        Query queryLike=firebaseLikes.orderByChild("product/id_product").equalTo(mProduct.getId_product());
        queryLike.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvCountLike.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Like like = postSnapshot.getValue(Like.class);
                    if (like.getUserLike().getId_user() == mUser.getId_user()) {
                        ivLike.setImageResource(R.drawable.ic_action_heart);
                        return;
                    }
                }
                ivLike.setImageResource(R.drawable.ic_action_heart_white);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Firebase firebaseDontLikes=FirebaseClient.getDontLikes();
        Query queryDontLike=firebaseDontLikes.orderByChild("product/id_product").equalTo(mProduct.getId_product());
        queryDontLike.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvCountLike.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DontLike like = postSnapshot.getValue(DontLike.class);
                    if (like.getUserDontLike().getId_user() == mUser.getId_user()) {
                        ivDontLike.setImageResource(R.drawable.ic_action_dontlike);
                        return;
                    }
                }
                ivDontLike.setImageResource(R.drawable.ic_action_dontlike_white);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        tvProductDetailDescription.setText(mProduct.getDescription());
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmit(View view){
        if (mUser.getId_user()==mProduct.getUserSell().getId_user()){
            viewOrderList();
        }else{
            orderProduct();
        }
    }
    public void viewOrderList() {
        final OrderViewFragment settingFragment = new OrderViewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", Parcels.wrap(mProduct));
        settingFragment.setArguments(bundle);
        settingFragment.show(getFragmentManager(), "orderView");
    }

    public void orderProduct() {
        final OrderProductFragment settingFragment = new OrderProductFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("product", Parcels.wrap(mProduct));
        bundle.putParcelable("user", Parcels.wrap(mUser));
        settingFragment.setArguments(bundle);
        final Firebase firebaseOrders = FirebaseClient.getOrders();
        settingFragment.setOnOrderListener(new OrderProductFragment.OnOrderListener() {
                                               @Override
                                               public void onOrder(final Order order) {
                                                   firebaseOrders.runTransaction(new Transaction.Handler() {

                                                       @Override
                                                       public Transaction.Result doTransaction(MutableData mutableData) {
                                                           return Transaction.success(mutableData); //we can also abort by calling Transaction.abort()
                                                       }

                                                       @Override
                                                       public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                                                           order.setUserBuy(mUser);
                                                           order.setCreated_at(System.currentTimeMillis());
                                                           order.setProduct(mProduct);
                                                           order.setId_order(dataSnapshot.getChildrenCount() + 1);

                                                           Firebase firebaseOrder = firebaseOrders.child(String.valueOf(dataSnapshot.getChildrenCount() + 1));
                                                           firebaseOrder.setValue(order);
                                                           Toast.makeText(getApplicationContext(), getResources().getString(R.string.order_successful), Toast.LENGTH_LONG).show();
                                                       }
                                                   });

                                               }
                                           }
        );
        settingFragment.show(getFragmentManager(), "orderProduct");
    }

    @OnClick(R.id.ivLike)
     public void onLike(View view){
        final Firebase firebaseLikes=FirebaseClient.getLikes();
        Query queryLike=firebaseLikes.orderByChild("product/id_product").equalTo(mProduct.getId_product());
        queryLike.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Like like = postSnapshot.getValue(Like.class);
                    if (like.getUserLike().getId_user() == mUser.getId_user()) {
                        firebaseLikes.child(postSnapshot.getKey()).removeValue();
                        ivLike.setImageResource(R.drawable.ic_action_heart_white);
                        int countLike = (int) dataSnapshot.getChildrenCount() - 1;
                        tvCountLike.setText(String.valueOf(countLike));
                        return;
                    }
                }
                int countLike = (int) dataSnapshot.getChildrenCount() + 1;
                tvCountLike.setText(String.valueOf(countLike));
                Like like = new Like();
                like.setbFlag_push(true);
                like.setUserLike(mUser);
                like.setCreated_at(System.currentTimeMillis());
                like.setProduct(mProduct);
                ivLike.setImageResource(R.drawable.ic_action_heart);
                firebaseLikes.push().setValue(like);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @OnClick(R.id.ivDontLike)
    public void onDontLike(View view){
        final Firebase firebaseDontLikes=FirebaseClient.getDontLikes();
        Query queryDontLike=firebaseDontLikes.orderByChild("product/id_product").equalTo(mProduct.getId_product());
        queryDontLike.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DontLike dontLike = postSnapshot.getValue(DontLike.class);
                    if (dontLike.getUserDontLike().getId_user()==mUser.getId_user()){
                        firebaseDontLikes.child(postSnapshot.getKey()).removeValue();
                        ivDontLike.setImageResource(R.drawable.ic_action_dontlike_white);
                        int countLike=(int)dataSnapshot.getChildrenCount()-1;
                        tvCountDontLike.setText(String.valueOf(countLike));
                        return;
                    }
                }
                int countLike=(int)dataSnapshot.getChildrenCount()+1;
                tvCountDontLike.setText(String.valueOf(countLike));
                DontLike like=new DontLike();
                like.setbFlag_push(true);
                like.setUserDontLike(mUser);
                like.setCreated_at(System.currentTimeMillis());
                like.setProduct(mProduct);
                ivDontLike.setImageResource(R.drawable.ic_action_dontlike);
                firebaseDontLikes.push().setValue(like);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}