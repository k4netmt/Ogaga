package com.ogaga.flash.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.acitivies.TimeLineActivity;
import com.ogaga.flash.models.Catalogies;
import com.ogaga.flash.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by carot on 4/18/2016.
 */
public class CategoryAdapter extends FirebaseRecyclerAdapter<Catalogies,CategoryAdapter.ViewHolder> {

    private Context context;
    final private User mUser;
    public CategoryAdapter(Firebase ref, Context parentContext,User user ) {
        super(Catalogies.class, R.layout.catalogies_item, CategoryAdapter.ViewHolder.class, ref);
        context = parentContext;
        mUser=user;
    }
    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Catalogies catalogies, int i) {
        viewHolder.bind(catalogies);
        viewHolder.bindUser(mUser);
         viewHolder.tvName.setText(catalogies.getName());
        //holder.ivImage.setImageResource(cate.getLocalImage());
        Picasso.with(context)
                .load(catalogies.getUrl())
                .fit().centerCrop()
                .into(viewHolder.ivImage);
    }

   public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Catalogies catalogies;
        User mUser;
        @Bind(R.id.ivPicture)
        ImageView ivImage;
        @Bind(R.id.tvName)
        TextView tvName;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

       public void bind(Catalogies catalogies){
           this.catalogies=catalogies;
       }
       public void bindUser(User user){
           this.mUser=user;
       }
       @Override
       public void onClick(View v) {
           Intent intent = new Intent(v.getContext(), TimeLineActivity.class);
           intent.putExtra("catalogies", Parcels.wrap(catalogies));
           intent.putExtra("user", Parcels.wrap(mUser));
           v.getContext().startActivity(intent);
       }
   }
}