package com.ogaga.flash.acitivies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.core.AuthProviderType;
import com.ogaga.flash.helpers.NotificationHelper;
import com.ogaga.flash.models.Order;
import com.ogaga.flash.utils.SpacesItemDecoration;
import com.firebase.client.Firebase;
import com.ogaga.flash.R;
import com.ogaga.flash.adapters.CategoryAdapter;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.extra.Constant;
import com.ogaga.flash.helpers.AuthorHelper;
import com.ogaga.flash.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CatalogiesActivity extends AppCompatActivity {


    // PICK_PHOTO_CODE is a constant integer
    public final static int PICK_PHOTO_CODE = 1046;
    Firebase firebase;
    @Bind(R.id.rvCate)
    RecyclerView rvCate;
    @Bind(R.id.toolbarHeader)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.nvView)
    NavigationView nvDrawer;
    @Bind(R.id.fabBuy)
    FloatingActionButton fabSell;
    /////////////////
    TextView tvNavPhonenumber;
    CircleImageView ivNavAvatar;
    TextView tvNavFullname;
    MenuItem mnSignout;
    /////////////////
    private CategoryAdapter cateAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private User mUser;
    NotificationHelper mNotificationHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogies);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mUser=Parcels.unwrap(getIntent().getParcelableExtra("user"));
        Firebase.setAndroidContext(this);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //selectDrawerItem(menuItem);
                        switch (menuItem.getItemId()) {
                            case R.id.navSetting:
                                Intent intent = new Intent(CatalogiesActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.navUserProfile:
                                if(mUser!=null){
                                    Intent intentUserProfile = new Intent(CatalogiesActivity.this, UserProfileActivity.class);
                                    intentUserProfile.putExtra("user",Parcels.wrap(mUser));
                                    startActivity(intentUserProfile);
                                }else{
                                    Intent intentLogin = new Intent(CatalogiesActivity.this, LoginActivity.class);
                                    startActivityForResult(intentLogin, Constant.LOGIN_SUCCESS_CODE);
                                }
                                break;
                            case R.id.navSignOut:{
                                AuthorHelper.clearUser(getApplicationContext());
                                mUser=null;
                                setupNaivgion();
                                break;
                            }
                        }
                        return true;
                    }
                });
        firebase = FirebaseClient.getCatalogies();
        setupViewNavigation();
        setupNaivgion();
        popularView();
        mNotificationHelper=new NotificationHelper(getApplicationContext());
        onOrderPushListener();
        onClickSellFAB();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onOrderPushListener(){
        if (mUser==null)
            return;
        final Firebase mFirebaseOrders=FirebaseClient.getOrders();
        Query query=mFirebaseOrders.orderByChild("product/userSell/id_user").equalTo(mUser.getId_user());
        query.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                    Order order = snapshot.getValue(Order.class);
                    if (order.isbFlag_push() == true) {
                        mNotificationHelper.createUploadedNotification(order);
                        order.setbFlag_push(false);
                        mFirebaseOrders.child(snapshot.getKey()).setValue(order);
                    }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    private void setupViewNavigation() {
        View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);
        tvNavPhonenumber=(TextView)headerLayout.findViewById(R.id.tvNavPhonenumber);
        ivNavAvatar=(CircleImageView)headerLayout.findViewById(R.id.ivNavAvatar);
        tvNavFullname=(TextView)headerLayout.findViewById(R.id.tvNavFullName);

        mnSignout= nvDrawer.getMenu().findItem(R.id.navSignOut);
    }

    private void setupNaivgion() {
        if(mUser==null){
            tvNavFullname.setText("Ogaga Founder");
            tvNavPhonenumber.setText("0988663129");
            Picasso.with(getApplicationContext()).load(R.drawable.vegetable).into(ivNavAvatar);
            mnSignout.setVisible(false);
        }else{
            tvNavFullname.setText(mUser.getFullname());
            tvNavPhonenumber.setText(mUser.getPhonenumber());
            Picasso.with(getApplicationContext()).load(mUser.getProfile_image()).placeholder(R.drawable.im_placeholder).into(ivNavAvatar);
            mnSignout.setVisible(true);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cateAdapter.cleanup();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // on Product post result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.LOGIN_SUCCESS_CODE) {
            if (data != null) {
                    mUser=Parcels.unwrap(data.getParcelableExtra("user"));
                    setupNaivgion();
                }
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == Constant.SELLPRODUCT_SUCCESS_CODE) {
            Toast.makeText(this, getString(R.string.product_created_successful), Toast.LENGTH_SHORT).show();
        }
    }

    /*Functions*/
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


    public void popularView() {
        cateAdapter = new CategoryAdapter(firebase, this,mUser);
        rvCate.setHasFixedSize(true);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvCate.setLayoutManager(gridLayoutManager);
        rvCate.setItemAnimator(new SlideInUpAnimator());
        //RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //set space for each photo in recycleview
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(8);
        rvCate.addItemDecoration(itemDecoration);
        rvCate.setAdapter(cateAdapter);
    }

    public void onClickSellFAB() {
        fabSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser != null) {
                    Intent intent = new Intent(CatalogiesActivity.this, SellActivity.class);
                    intent.putExtra("user", Parcels.wrap(mUser));
                    startActivityForResult(intent, Constant.SELLPRODUCT_SUCCESS_CODE);
                } else {
                    Intent intent = new Intent(CatalogiesActivity.this, LoginActivity.class);
                    startActivityForResult(intent, Constant.LOGIN_SUCCESS_CODE);
                }
            }
        });
    }

    public void onClickCategoryItem(){

    }
}