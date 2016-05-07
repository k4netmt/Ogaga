package com.ogaga.flash.clients;

import android.content.Context;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.ogaga.flash.helpers.AuthorHelper;
import com.ogaga.flash.models.User;

/**
 * Created by Kanet on 4/12/2016.
 */
public class FirebaseClient {
    public interface LoginUser{
        public void onLoginSuccess(User user);
    }
    public static LoginUser mListener;
    public static Firebase getRoot(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com");
        return  mFirebaseRef;
    }

    public static Firebase getCatalogies(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("catalogies");
        return  mFirebaseRef;
    }

    public static Firebase getOrders(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("orders");
        return  mFirebaseRef;
    }

    public static Firebase getUsers(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("users");
        return  mFirebaseRef;
    }

    public static Firebase getLikes(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("likes");
        return  mFirebaseRef;
    }

    public static Firebase getDontLikes(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("dontlikes");
        return  mFirebaseRef;
    }

    public static Firebase getComments(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("comments");
        return  mFirebaseRef;
    }

    public static Firebase getProduct(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("products");
        return  mFirebaseRef;
    }

    public static User getUserLogin(Context context, LoginUser listener) {
        String uid=AuthorHelper.readString(context, "uid");
        mListener=listener;
        if (uid==null){
            mListener.onLoginSuccess(null);
            return null;
        }
        Firebase mFirebaseUser=FirebaseClient.getUsers();
        Query queryRef = mFirebaseUser.child(uid);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mListener.onLoginSuccess(user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return null;
    }
}
