package com.ogaga.flash.acitivies;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.clients.ImgurClient;
import com.ogaga.flash.extra.Constant;
import com.ogaga.flash.extra.ValiDate;
import com.ogaga.flash.helpers.DocumentHelper;
import com.ogaga.flash.imgurmodel.ImageResponse;
import com.ogaga.flash.imgurmodel.Upload;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.UiCallback;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SellActivity extends AppCompatActivity{

    @Bind(R.id.btnChooseImage)ImageView btnChoseAvatar;
    @Bind(R.id.btnSubmit)Button btnSubmit;
    @Bind(R.id.etProductname)EditText etProductname;
    @Bind(R.id.spProductType)Spinner spProductType;
    @Bind(R.id.etProductPrice)EditText etProductPrice;
    @Bind(R.id.spProductUnit)Spinner spProductUnit;
    @Bind(R.id.spProductStatus)Spinner spProductStatus;
    @Bind(R.id.etDateStart)EditText etDateStart;
    @Bind(R.id.tilProductname)TextInputLayout tilProductname;
    @Bind(R.id.tilProductPrice)TextInputLayout tilProductPrice;
    @Bind(R.id.tilDateStart)TextInputLayout tilDateStart;
    @Bind(R.id.tilDateFinished)TextInputLayout tilDateFinished;
    @Bind(R.id.tilDescription)TextInputLayout tilDescription;
    @Bind(R.id.etDesciption)EditText etDesciption;
    @Bind(R.id.etDateFinished)EditText etDateFinished;
    @Bind(R.id.tvErrorAvatar)TextView tvErrorAvatar;
    @Bind(R.id.toolbarHeader)Toolbar toolbar;
   // @Bind(R.id.trDateFinished)TableRow trDateFinished;
    ValiDate mValiDate;
    @Bind(R.id.ivAvatar)ImageView ivAvatar;
    private long mStartDate;
    private long mFinishedDate;
    User mUser;
    private Uri mPhotoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mUser= Parcels.unwrap(getIntent().getParcelableExtra("user"));
        mValiDate=new ValiDate(this);
        setupProductType();
        setupProdcutStatus();
        setupUnit();
    }

    private void setupUnit() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_unit, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spProductUnit.setAdapter(adapter);
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
    private void setupProductType() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spProductType.setAdapter(adapter);
    }

    private void setupProdcutStatus() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_status_new, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spProductStatus.setAdapter(adapter);
        spProductStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // trDateFinished.setVisibility(View.VISIBLE);
                //  trDateStart.setVisibility(View.VISIBLE);
                //etDateFinished.setVisibility(View.VISIBLE);
                //etDateStart.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0: {
                        //trDateFinished.setVisibility(View.VISIBLE);
                        //trDateStart.setVisibility(View.VISIBLE);
                        tilDateFinished.setVisibility(View.VISIBLE);
                        tilDateStart.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1: {
                        String myFormat = "dd/MM/yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                        etDateStart.setText(sdf.format(System.currentTimeMillis()));

                        mStartDate = System.currentTimeMillis();
                        tilDateFinished.setVisibility(View.VISIBLE);
                        tilDateStart.setVisibility(View.INVISIBLE);
                        break;
                        // trDateFinished.setVisibility(View.VISIBLE);

                    }
                    case 2: {
                        String myFormat = "dd/MM/yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                        etDateStart.setText(sdf.format(System.currentTimeMillis()));
                        etDateFinished.setText(sdf.format(System.currentTimeMillis()));
                        mStartDate = System.currentTimeMillis();
                        mFinishedDate = System.currentTimeMillis();
                        tilDateFinished.setVisibility(View.INVISIBLE);
                        tilDateStart.setVisibility(View.INVISIBLE);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getResources().getInteger(R.integer.PICK_PHOTO_CODE)) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try{
                        mPhotoUri = data.getData();
                        // Do something with the photo based on Uri
                        Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mPhotoUri);

                        ivAvatar.setImageBitmap(selectedImage);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @OnClick(R.id.etDateStart)
    public void onClickStartDate(View view){
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mStartDate=myCalendar.getTimeInMillis();
                String myFormat = "dd/MM/yyyy"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                etDateStart.setText(sdf.format(myCalendar.getTime()));
            }

        };
        DatePickerDialog datePickerDialog;
        datePickerDialog=new DatePickerDialog(this,dateSetListener,myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        return;
    }

    @OnClick(R.id.etDateFinished)
    public void onClickFinishedDate(View view){
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mFinishedDate=myCalendar.getTimeInMillis();
                String myFormat = "dd/MM/yyyy"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                etDateFinished.setText(sdf.format(myCalendar.getTime()));
            }

        };
        DatePickerDialog datePickerDialog;
        datePickerDialog=new DatePickerDialog(this,dateSetListener,myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        return;
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmit(View view){

        if (mValiDate.validateImage(tvErrorAvatar, mPhotoUri) == false) {
            return;
        }
        if (mValiDate.validateProductName(tilProductname, etProductname) == false) {
            return;
        }
        if (mValiDate.validateProductPrice(tilProductPrice,etProductPrice)==false){
            return;
        }
        if (mValiDate.validateDate(tilDateStart,etDateStart)==false){
            return;
        }
        if (mValiDate.validateDate(tilDateFinished,etDateFinished)==false){
            return;
        }
        if (mValiDate.validateCompareDate(tilDateStart,mStartDate,mFinishedDate)==false){
            return;
        }
        if (mValiDate.validateDescription(tilDescription,etDesciption,350,true)==false){
            return;
        }
        showProgressBar();
        final Firebase firebaseProducts= FirebaseClient.getProduct();
        Upload upload = new Upload();
        upload.image = new File(DocumentHelper.getPath(this, mPhotoUri));
        upload.description = etProductname.getText().toString();

        new ImgurClient(this, new ImgurClient.ImgurClientListener() {
            @Override
            public void postUploadImage(final ImageResponse imageResponse) {
                if (imageResponse!=null){
                    firebaseProducts.runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            return Transaction.success(mutableData); //we can also abort by calling Transaction.abort()
                        }

                        @Override
                        public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                            if (firebaseError != null) {
                                hideProgressBar();
                                Toast.makeText(getApplicationContext(),getString(R.string.not_connected),Toast.LENGTH_LONG).show();
                            } else {
                                Product product = new Product();
                                long id = dataSnapshot.getChildrenCount();
                                product.setId_product(id + 1);
                                product.setUrl(imageResponse.data.link);
                                product.setName(etProductname.getText().toString());
                                product.setId_productType(spProductType.getSelectedItemPosition() + 1);
                                product.setId_productStatus(spProductStatus.getSelectedItemPosition() + 1);
                                product.setId_unit(spProductUnit.getSelectedItemPosition() + 1);
                                product.setId_shipping(0);//Define
                                product.setCreate_at(System.currentTimeMillis());
                                product.setStart_date(mStartDate);
                                product.setFinished_date(mFinishedDate);
                                product.setDescription(etDesciption.getText().toString());
                                long prices = Long.parseLong(etProductPrice.getText().toString());
                                product.setPrices(prices);
                                product.setUserSell(mUser);
                                Firebase firebaseProduct=firebaseProducts.child(String.valueOf(product.getId_product()));

                                firebaseProduct.setValue(product);
                                hideProgressBar();
                                Intent intent = new Intent();
                                setResult(Constant.SELLPRODUCT_SUCCESS_CODE, intent);
                                finish();//finishing activity
                            }
                        }
                    });
                }else{
                    hideProgressBar();
                    Toast.makeText(getApplicationContext(),getString(R.string.not_connected),Toast.LENGTH_LONG).show();
                }
            }
        }).Execute(upload, new UiCallback());

    }

    MenuItem miActionProgressItem;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sell_activity, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
        btnSubmit.setEnabled(false);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
        btnSubmit.setEnabled(true);
    }
}
