//package com.ogaga.flash.models;
//
//import android.net.Uri;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by IceStone on 4/19/2016.
// */
//public class GetProductJsonData extends GetRawData {
//
//    private String LOG_TAG = GetProductJsonData.class.getSimpleName();
//    private List<Product> mProducts;
//    private Uri mDestinationUri;
//
//    public GetProductJsonData(String searchCriteria, boolean matchAll) {
//        super(null);
//        createAndUpdateUri(searchCriteria, matchAll);
//        mProducts = new ArrayList<Product>();
//    }
//
//    public List<Product> getMPhotos() {
//        return mProducts;
//    }
//
//    public void execute() {
//        super.setmRawUrl(mDestinationUri.toString());
//        DownloadJsonData downloadJsonData = new DownloadJsonData();
//        Log.v(LOG_TAG, "Built URI = " + mDestinationUri.toString());
//        downloadJsonData.execute(mDestinationUri.toString());
//    }
//
//    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {
//        final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
//        final String TAGS_PARAM = "tags";
//        final String TAGMODE_PARAM = "tagmode";
//        final String FORMAT_PARAM = "format";
//        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";
//
//        mDestinationUri = Uri.parse(FLICKR_API_BASE_URL).buildUpon()
//                .appendQueryParameter(TAGS_PARAM, searchCriteria)
//                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "ALL" : "ANY")
//                .appendQueryParameter(FORMAT_PARAM, "json")
//                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
//                .build();
//
//        return mDestinationUri != null;
//    }
//
//
//    public void processResult() {
//
//        if (getmDownloadStatus() != DownloadStatus.OK) {
//            Log.e(LOG_TAG, "Error downloading raw file");
//            return;
//        }
//
//        final String FLICKR_ITEMS = "items";
//        final String FLICKR_TITLE = "title";
//        final String FLICKR_MEDIA = "media";
//        final String FLICKR_PHOTO_URL = "m";
//        final String FLICKR_AUTHOR = "author";
//        final String FLICKR_AUTHOR_ID = "author_id";
//        final String FLICKR_LINK = "link";
//        final String FLICKR_TAGS = "tags";
//
//        try {
//
//            JSONObject jsonData = new JSONObject(getmData());
//            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);
//            for (int i = 0; i < itemsArray.length(); i++) {
//
//                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
//                String title = jsonPhoto.getString(FLICKR_TITLE);
//                String author = jsonPhoto.getString(FLICKR_AUTHOR);
//                String authorId = jsonPhoto.getString(FLICKR_AUTHOR_ID);
//                String link = jsonPhoto.getString(FLICKR_LINK);
//                String tags = jsonPhoto.getString(FLICKR_TAGS);
//
//                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
//                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);
//
//                Product photoObject = new Product(title, author, authorId, link, tags, photoUrl);
//
//                this.mProducts.add(photoObject);
//            }
//
//            for (Product singlePhoto : mProducts) {
//                Log.v(LOG_TAG, singlePhoto.toString());
//            }
//
//        } catch (JSONException jsone) {
//            jsone.printStackTrace();
//            Log.e(LOG_TAG, "Error processing Json data");
//        }
//
//    }
//
//    public class DownloadJsonData extends DownloadRawData {
//
//        protected void onPostExecute(String webData) {
//            super.onPostExecute(webData);
//            processResult();
//
//        }
//
//        protected String doInBackground(String... params) {
//            String[] par = {mDestinationUri.toString()};
//            return super.doInBackground(par);
//        }
//
//    }
//
//}