package com.mumanit.bontestapp.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
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
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Subscription;
import rx.functions.Action1;

@RuntimePermissions
public class MainActivity extends AppCompatActivity implements VenuesContract.VenuesListView {

    @Bind(R.id.rvVenuesList)
    RecyclerView rvVenuesList;

    @Inject
    VenuesListPresenter presenter;

    @Inject
    ReactiveLocationProvider locationProvider;

    @Inject
    LocationRequest locationUpdateConfig;

    VenuesListAdapter venuesListAdapter;

    LinearLayoutManager venuesListLayoutManager;

    Subscription locationSubscription;

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

        MainActivityPermissionsDispatcher.startReceivingLocationUpdatesWithCheck(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (null != locationSubscription) {
            locationSubscription.unsubscribe();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void startReceivingLocationUpdates() {

        locationSubscription = locationProvider
                .getUpdatedLocation(locationUpdateConfig)
                .subscribe(new Action1<Location>() {
                    @Override
                    public void call(Location location) {
                        presenter.loadVenues();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    void showRationaleForLocation(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("location rationale")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForLocation() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForLocation() {
        Toast.makeText(this, "never ask again", Toast.LENGTH_SHORT).show();
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
