package com.mumanit.bontestapp.ui

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.location.LocationRequest
import com.mumanit.bontestapp.R
import com.mumanit.bontestapp.app.App
import com.mumanit.bontestapp.domain.model.VenueData
import com.mumanit.bontestapp.ui.venues.VenuesListAdapter
import com.mumanit.bontestapp.ui.venues.VenuesViewModel
import com.mumanit.bontestapp.ui.viewmodel.ViewModelFactory
import permissions.dispatcher.*
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import rx.Subscription
import java.util.*
import javax.inject.Inject

@RuntimePermissions
class MainActivity : AppCompatActivity() {

    @BindView(R.id.rvVenuesList)
    internal lateinit var rvVenuesList: RecyclerView

    @Inject
    internal lateinit var locationProvider: ReactiveLocationProvider

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    @Inject
    internal lateinit var locationUpdateConfig: LocationRequest

    internal lateinit var venuesListAdapter: VenuesListAdapter
    internal lateinit var venuesListLayoutManager: LinearLayoutManager
    internal var locationSubscription: Subscription? = null

    private lateinit var viewModel: VenuesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        injectDependencies()
        initAdapter()
        initRecycler()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(VenuesViewModel::class.java)
    }

    private fun startObservingViewModel() {
        viewModel.venues.observe(this, this.venuesObserver)
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
                .subscribe({
                    viewModel.loadVenues()
                }, { })
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

    private fun injectDependencies() {
        App.getAppComponent(this)?.inject(this)
    }

    private fun showVenuesList(venueDataList: List<VenueData>?) {
        venuesListAdapter.data = venueDataList
        venuesListAdapter.notifyDataSetChanged()
        Toast.makeText(this, "data loaded", Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun showError() {
        Toast.makeText(this, "loading error", Toast.LENGTH_LONG).show()
    }

    private val venuesObserver = Observer<List<VenueData>> { t -> showVenuesList(t) }

}
