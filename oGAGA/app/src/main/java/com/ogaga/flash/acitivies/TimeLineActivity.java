package com.ogaga.flash.acitivies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.ogaga.flash.R;
import com.ogaga.flash.adapters.ProductRecyclerViewAdapter;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.extra.Constant;
import com.ogaga.flash.models.Catalogies;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimeLineActivity extends AppCompatActivity {
    private Firebase fbProduct;
    @Bind(R.id.toolbarHeader)
    Toolbar mToolbar;
    @Bind(R.id.rvProductList)
    RecyclerView rvProductList;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    Catalogies mCatalogies;
    private ProductRecyclerViewAdapter mProductRecyclerViewAdapter;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        ButterKnife.bind(this);
        mCatalogies= Parcels.unwrap(getIntent().getParcelableExtra("catalogies"));
        mUser= Parcels.unwrap(getIntent().getParcelableExtra("user"));

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mCatalogies.getName());

        populateProductListView();
      /*  onClickSellFAB();*/

    }

    @Override
    public Context createDisplayContext(Display display) {
        return super.createDisplayContext(display);
    }

    public void populateProductListView() {
        Firebase firebaseProduct  = FirebaseClient.getProduct();
        Query query=firebaseProduct.orderByChild("id_productType").equalTo(mCatalogies.getId());
        mProductRecyclerViewAdapter = new ProductRecyclerViewAdapter(query, this,mUser);
        rvProductList.setHasFixedSize(true);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvProductList.setAdapter(mProductRecyclerViewAdapter);
    }

    public void onClickSellFAB() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeLineActivity.this, SellActivity.class);
                intent.putExtra("user", Parcels.wrap(mUser));
                startActivityForResult(intent, Constant.SELLPRODUCT_SUCCESS_CODE);
            }
        });
    }

    // on Product post result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.SELLPRODUCT_SUCCESS_CODE) {
            Toast.makeText(this, getString(R.string.product_created_successful), Toast.LENGTH_SHORT).show();
        }
    }
}
