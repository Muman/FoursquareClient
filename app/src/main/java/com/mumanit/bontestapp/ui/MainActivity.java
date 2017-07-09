package com.mumanit.bontestapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mumanit.bontestapp.R;
import com.mumanit.bontestapp.app.App;
import com.mumanit.bontestapp.ui.venues.VenuesContract;
import com.mumanit.bontestapp.ui.venues.VenuesListPresenter;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements VenuesContract.VenuesListView {

    @Inject
    VenuesListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        injectDependencies();

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

        presenter.detach();
    }

    private void injectDependencies() {
        App.getAppComponent(this).inject(this);
    }

    @Override
    public void showVenuesList() {
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
