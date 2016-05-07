package com.ogaga.flash.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Query;
import com.firebase.client.annotations.Nullable;
import com.ogaga.flash.R;
import com.ogaga.flash.adapters.ProductAdapter;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by carot on 4/21/2016.
 */
public class ProfileFragment extends Fragment {

    @Bind(R.id.rvProduct)RecyclerView rvProduct;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_list, parent, false);
        ButterKnife.bind(this, v);

        /*rvProduct.setAdapter(pAdapter);
        //rvTweets.setLayoutManager(new LinearLayoutManager(getActivity()));
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        //enable optimizations if all item views are of the same height and width for significantly smoother scrolling
        rvProduct.setHasFixedSize(true);
        //reflow item
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        // Attach the layout manager to the recycler view
        rvProduct.setLayoutManager(gridLayoutManager);

        // Add the scroll listener
        rvProduct.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //populateTimeLine(page);
            }
        });*/
        return v;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mFirebase= FirebaseClient.getProduct();

    }

    public void setView(){
        //rvTweets.setLayoutManager(new LinearLayoutManager(getActivity()));
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        //enable optimizations if all item views are of the same height and width for significantly smoother scrolling
        rvProduct.setHasFixedSize(true);
        //reflow item
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        // Attach the layout manager to the recycler view
        rvProduct.setLayoutManager(gridLayoutManager);

        // Add the scroll listener
        rvProduct.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //populateTimeLine(page);
            }
        });
    }
    public void addAll() {
   /*     rvProduct.setAdapter(pAdapter);*/

       // pAdapter= new ProductAdapter(query,getContext());
    }
}
