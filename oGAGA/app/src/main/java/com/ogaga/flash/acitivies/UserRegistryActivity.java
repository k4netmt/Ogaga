package com.ogaga.flash.acitivies;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.clients.ImgurClient;
import com.ogaga.flash.extra.Constant;
import com.ogaga.flash.helpers.AuthorHelper;
import com.ogaga.flash.helpers.DocumentHelper;
import com.ogaga.flash.imgurmodel.ImageResponse;
import com.ogaga.flash.imgurmodel.Upload;
import com.ogaga.flash.models.UiCallback;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class UserRegistryActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    @Bind(R.id.toolbarHeader)
    Toolbar toolbar;
    @Bind(R.id.btnChooseImage)
    ImageView ivChooseImage;
    @Bind(R.id.btnRegistry)
    Button btnRegistry;
    @Bind(R.id.etAdressUser)
    EditText etAddressuser;
    @Bind(R.id.etFullname)
    EditText etFullname;
    @Bind(R.id.etPhoneNumber)
    EditText etPhonenumber;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    protected LocationManager locationManager;
    protected LocationListener locationListenerNetwork;
    protected LocationListener locationListenerGPS;
    private Location currentLocation;
    private Firebase mFirebaseClient;
    private Uri mPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registry);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getMyLocation();
        Firebase.setAndroidContext(this);
        mFirebaseClient = FirebaseClient.getUsers();
        locationListenerGPS = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
            /*    Uri gmmIntentUri = Uri.parse("geo:"+currentLocation.getLatitude()+","+currentLocation.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);*/
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationListenerNetwork = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location);
            /*    Uri gmmIntentUri = Uri.parse("geo:"+currentLocation.getLatitude()+","+currentLocation.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);*/
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @OnClick(R.id.btnChooseImage)
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, getResources().getInteger(R.integer.PICK_PHOTO_CODE));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    void getMyLocation() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
       /*     Toast.makeText(getApplicationContext(),"not load location",Toast.LENGTH_LONG).show();*/
            return;
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @OnClick(R.id.btnRegistry)
    public void registryUser() {


        final ProgressDialog progressDialog = new ProgressDialog(UserRegistryActivity.this,
                R.style.AppThemeProgress);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        btnRegistry.setVisibility(View.INVISIBLE);
        final User user = new User();
        if (etFullname.getText() == null) {
            btnRegistry.setVisibility(View.VISIBLE);
            return;
        }
        if (etAddressuser.getText() == null) {
            btnRegistry.setVisibility(View.VISIBLE);
            return;
        }
        if (etPhonenumber.getText() == null) {
            btnRegistry.setVisibility(View.VISIBLE);
            return;
        }
        if (mPhotoUri == null) {
            btnRegistry.setVisibility(View.VISIBLE);
            return;
        }
        user.setAddress_user(etAddressuser.getText().toString());
        user.setCreated_at(System.currentTimeMillis());
        user.setFullname(etFullname.getText().toString());
        user.setPhonenumber(etPhonenumber.getText().toString());

        getCurrentLocation();
        Upload upload = new Upload();
        upload.image = new File(DocumentHelper.getPath(this, mPhotoUri));
        upload.description = user.getFullname();


        new ImgurClient(this, new ImgurClient.ImgurClientListener() {
            @Override
            public void postUploadImage(final ImageResponse imageResponse) {

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                FirebaseClient.getRoot().createUser(user.getEmail(), etPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                                    @Override
                                    public void onSuccess(final Map<String, Object> stringObjectMap) {
                                        User user = new User();
                                        mFirebaseClient.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(MutableData currentData) {
                                                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
                                            }

                                            @Override
                                            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                                                //This method will be called once with the results of the transaction.
                                                if (firebaseError != null) {
                                                    System.out.println("Firebase counter increment failed.");
                                                } else {
                                                    User user = new User();
                                                    long id = currentData.getChildrenCount();
                                                    user.setId_user(id + 1);
                                                    user.setAddress_user(etAddressuser.getText().toString());
                                                    user.setCreated_at(System.currentTimeMillis());
                                                    user.setFullname(etFullname.getText().toString());
                                                    String location = "";
                                                    if (currentLocation != null)
                                                        location = String.valueOf(currentLocation.getLatitude()) + "," + String.valueOf(currentLocation.getLongitude());
                                                    user.setLocation(location);
                                                    user.setPhonenumber(etPhonenumber.getText().toString());
                                                    user.setProfile_image(imageResponse.data.link);
                                                    Firebase mUserFirebase = mFirebaseClient.child(stringObjectMap.get("uid").toString());
                                                    mUserFirebase.setValue(user);
                                                    AuthorHelper.writeString(getApplicationContext(), "uid", stringObjectMap.get("uid").toString());
                                                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.registry_success), Toast.LENGTH_LONG).show();
                                                    //
                                                    Intent intent = new Intent();
                                                    intent.putExtra("user", Parcels.wrap(user));
                                                    setResult(Constant.REGISTRY_SUCCESS_CODE, intent);
                                                    finish();//finishing activity
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(FirebaseError firebaseError) {
                                        btnRegistry.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(), "Email have been registered", Toast.LENGTH_LONG);
                                    }
                                });
                                progressDialog.dismiss();
                            }
                        }, 3000);

            }
        }).Execute(upload, new UiCallback());



    }

    void getCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network_enabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (network_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0, 0,
                    locationListenerNetwork);
            Log.i("Network", "Network Provider");
        } else {
      /*      Toast.makeText(getApplicationContext(),
                    "Network provider is not enabled", Toast.LENGTH_LONG);*/
        }

        if (gps_enabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
        } else {
     /*       Toast.makeText(getApplicationContext(), "GPS provider is not enabled",
                    Toast.LENGTH_LONG);*/
        }


    }

    void updateLocation(Location location) {
        currentLocation = location;
       /* Toast.makeText(
                UserRegistryActivity.this,
                "GPS Location \n" + String.valueOf(currentLocation.getLongitude()) + "\n"
                        + String.valueOf(currentLocation.getLongitude()), Toast.LENGTH_LONG).show();*/
    }

    protected void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    protected void onStop() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getResources().getInteger(R.integer.PICK_PHOTO_CODE)) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        mPhotoUri = data.getData();
                        // Do something with the photo based on Uri
                        Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mPhotoUri);

                        ivAvatar.setImageBitmap(selectedImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
