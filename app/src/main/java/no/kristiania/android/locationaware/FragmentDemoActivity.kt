package no.kristiania.android.locationaware

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = UPDATE_INTERVAL_IN_MILLISECONDS / 2

class FragmentDemoActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var mapFragment: SupportMapFragment

    /*private lateinit var mLocationRequest: LocationRequest
    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            onNewLocation(locationResult.lastLocation)
        }
    }
    private lateinit var mFusedLocationClient: FusedLocationProviderClient*/



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frgment_demo)


        if (permissionCheck()) {
            setMapFragment()
        }
    }

    /*private fun initLocationAware() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationRequest = createLocationRequest()
    }*/

    /**
     * Sets the location request parameters.
     */
    /*private fun createLocationRequest() : LocationRequest {
        return LocationRequest().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }*/

    /*private fun startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
            mLocationCallback,
            Looper.getMainLooper())
    }*/

    /*private fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }*/

    private fun setMapFragment() {
        mapFragment = SupportMapFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.first_frame, mapFragment, "First")
            .commit()
        mapFragment.getMapAsync(this)
    }


    private fun permissionCheck(): Boolean =
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            promptForPermission()
            false
        } else {
            true
        }

    // Request for permission  show the dialog window to approve the usage of location feature
    private fun promptForPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1111
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1111 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    setMapFragment()
                } else {
                    Toast.makeText(
                        this,
                        "Sorry, the app cant do anything with out the location permission.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney and move the camera
        this.mMap = googleMap
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

    }

    /*private fun getDeviceLocation() {
        val locationResult: Task<*> =
            mFusedLocationClient.lastLocation
        locationResult.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val mLastKnownLocation : Location = task.result as Location
                mMap!!.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()
                        ), 13.0f
                    )
                )
            }
        }
    }*/

    /*private fun onNewLocation(location: Location) {
        mMap!!.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location.latitude,
                    location.longitude
                ), 13.0f
            )
        )
    }*/

    private fun updateLocationUI() {
        mMap?.apply {
            isMyLocationEnabled = true;
            uiSettings.isMyLocationButtonEnabled = true;

            moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        59.9112,
                        10.7449
                    ), 13.0f
                )
            )
        }
    }

}
