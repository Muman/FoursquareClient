package com.mumanit.foursquareclient.presentation.venues;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mumanit.foursquareclient.R;
import com.mumanit.foursquareclient.domain.model.VenueDomainModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VenuesListAdapter extends RecyclerView.Adapter<VenuesListAdapter.VenueItemViewHolder> {

    private List<VenueDomainModel> mDataset;
    private Context mContext;

    public VenuesListAdapter(List<VenueDomainModel> mDataset, Context mContext) {
        this.mDataset = mDataset;
        this.mContext = mContext;
    }

    public void setData(List<VenueDomainModel> venueDatas) {
        this.mDataset = venueDatas;
    }

    public List<VenueDomainModel> getData() {
        return mDataset;
    }

    @NotNull
    @Override
    public VenueItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_venue_item, parent, false);
        return new VenueItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull final VenueItemViewHolder holder, int position) {
        bindViews(holder, position);
    }

    private void bindViews(final VenueItemViewHolder holder, int position) {

        VenueDomainModel venue = mDataset.get(position);

        holder.tvVenueCheckins.setText(String.valueOf(venue.checkinsCount));
        holder.tvVenueName.setText(venue.name);

        if (null != venue.photoUrl) {
            Picasso.with(mContext).load(venue.photoUrl).into(holder.ivVenueImage);
        } else {
            Picasso.with(mContext).load(android.R.drawable.ic_dialog_map).into(holder.ivVenueImage);
        }

    }

    @Override
    public int getItemCount() {
        if (null == mDataset) {
            return 0;
        }

        return mDataset.size();
    }

    static class VenueItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivVenueImage)
        ImageView ivVenueImage;
        @BindView(R.id.tvVenueName)
        TextView tvVenueName;
        @BindView(R.id.tvVenueCheckins)
        TextView tvVenueCheckins;

        public VenueItemViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
