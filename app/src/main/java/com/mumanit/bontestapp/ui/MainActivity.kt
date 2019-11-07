package com.mumanit.bontestapp.ui

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.location.LocationRequest
import com.mumanit.bontestapp.R
import com.mumanit.bontestapp.app.App
import com.mumanit.bontestapp.domain.model.VenueData
import com.mumanit.bontestapp.ui.venues.VenuesContract
import com.mumanit.bontestapp.ui.venues.VenuesListAdapter
import com.mumanit.bontestapp.ui.venues.VenuesListPresenter
import permissions.dispatcher.*
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import rx.Subscription
import java.util.*
import javax.inject.Inject

@RuntimePermissions
class MainActivity : AppCompatActivity(), VenuesContract.VenuesListView {

    @BindView(R.id.rvVenuesList)
    internal lateinit var rvVenuesList: RecyclerView

    @Inject
    internal lateinit var presenter: VenuesListPresenter

    @Inject
    internal lateinit var locationProvider: ReactiveLocationProvider

    @Inject
    internal lateinit var locationUpdateConfig: LocationRequest

    internal lateinit var venuesListAdapter: VenuesListAdapter
    internal lateinit var venuesListLayoutManager: LinearLayoutManager
    internal var locationSubscription: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        injectDependencies()
        initAdapter()
        initRecycler()
        presenter.attach(this)
    }

    private fun initAdapter() {
        venuesListAdapter = VenuesListAdapter(ArrayList(), this)
    }

    private fun initRecycler() {
        venuesListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvVenuesList.layoutManager = venuesListLayoutManager
        rvVenuesList.adapter = venuesListAdapter
    }

    override fun onStart() {
        super.onStart()
        startReceivingLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        locationSubscription?.unsubscribe()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    fun startReceivingLocationUpdates() {
        locationSubscription = locationProvider
                .getUpdatedLocation(locationUpdateConfig)
                .subscribe({ presenter.loadVenues() }, { })
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    internal fun showRationaleForLocation(request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setMessage("location rationale")
                .setPositiveButton("ok") { dialog, which -> request.proceed() }
                .setNegativeButton("no") { dialog, which -> request.cancel() }
                .show()
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    internal fun showDeniedForLocation() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    internal fun showNeverAskForLocation() {
        Toast.makeText(this, "never ask again", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    private fun injectDependencies() {
        App.getAppComponent(this).inject(this)
    }

    override fun showVenuesList(venueDataList: List<VenueData>?) {
        venuesListAdapter.data = venueDataList
        venuesListAdapter.notifyDataSetChanged()
        Toast.makeText(this, "data loaded", Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError() {
        Toast.makeText(this, "loading error", Toast.LENGTH_LONG).show()
    }
}
