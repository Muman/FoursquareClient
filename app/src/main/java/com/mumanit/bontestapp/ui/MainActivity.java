package com.mumanit.bontestapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mumanit.bontestapp.R;
import com.mumanit.bontestapp.app.App;
import com.mumanit.bontestapp.domain.model.VenueData;
import com.mumanit.bontestapp.ui.venues.VenuesContract;
import com.mumanit.bontestapp.ui.venues.VenuesListAdapter;
import com.mumanit.bontestapp.ui.venues.VenuesListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements VenuesContract.VenuesListView {

    @Bind(R.id.rvVenuesList)
    RecyclerView rvVenuesList;

    @Inject
    VenuesListPresenter presenter;

    VenuesListAdapter venuesListAdapter;

    LinearLayoutManager venuesListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        injectDependencies();

        venuesListLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        venuesListAdapter = new VenuesListAdapter(new ArrayList<VenueData>(), this);
        rvVenuesList.setLayoutManager(venuesListLayoutManager);
        rvVenuesList.setAdapter(venuesListAdapter);

        presenter.attach(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.loadVenues();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
        presenter.detach();
    }

    private void injectDependencies() {
        App.getAppComponent(this).inject(this);
    }

    @Override
    public void showVenuesList(List<VenueData> venueDataList) {
        venuesListAdapter.setData(venueDataList);
        venuesListAdapter.notifyDataSetChanged();

        Toast.makeText(this, "data loaded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {
        Toast.makeText(this, "loading error", Toast.LENGTH_LONG).show();
    }
}
