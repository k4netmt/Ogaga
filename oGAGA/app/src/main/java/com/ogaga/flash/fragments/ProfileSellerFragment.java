package com.ogaga.flash.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.client.annotations.Nullable;
import com.ogaga.flash.adapters.ProductAdapter;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

/**
 * Created by carot on 4/21/2016.
 */
public class ProfileSellerFragment extends ProfileFragment {

    private ProductAdapter pAdapter;
    public static ProfileSellerFragment newInstance(User user) {
        ProfileSellerFragment fragment = new ProfileSellerFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("user", Parcels.wrap(user));
        fragment.setArguments(bundle);
        return fragment;
    }

    Firebase mFirebase;
    User mUser;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View root=super.onCreateView(inflater, parent, savedInstanceState);
        setView();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mUser=Parcels.unwrap(getArguments().getParcelable("user"));
        super.onCreate(savedInstanceState);
        addAll();
    }

    @Override
    public void setView() {
        super.rvProduct.setAdapter(pAdapter);
        super.setView();
    }

    @Override
    public void addAll() {
        mFirebase= FirebaseClient.getProduct();
        Query query =mFirebase.orderByChild("userSell/id_user").equalTo(mUser.getId_user());
        pAdapter= new ProductAdapter(query,getContext());
    }
}
