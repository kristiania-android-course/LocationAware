package no.kristiania.android.locationaware

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.util.*

private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = UPDATE_INTERVAL_IN_MILLISECONDS / 2

class FragmentDemoActivity : AppCompatActivity(), OnMapReadyCallback {


    private var mMap: GoogleMap? = null
    private lateinit var mapFragment: SupportMapFragment

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            getAddressFromLocation(locationResult.lastLocation)
            moveToLocation(
                LatLng(
                    locationResult.lastLocation.latitude,
                    locationResult.lastLocation.longitude
                )
            )
        }
    }
    private val mLocationRequest = LocationRequest().apply {
        interval = UPDATE_INTERVAL_IN_MILLISECONDS
        fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun getAddressFromLocation(lastLocation: Location) {
        // GeoCoder api to ge the address from the location data.
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)
        val returnedAddress: Address = addresses[0]

        Toast.makeText(
            this,
            "${lastLocation.latitude.toString()} and ${lastLocation.longitude} ${returnedAddress.createStringAddress()}",
            Toast.LENGTH_SHORT
        ).show()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frgment_demo)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (permissionCheck()) {
            setMapFragment()
            startLocationUpdates()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }


    private fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    private fun startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.getMainLooper()
        )
    }


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

        moveToLocation(
            LatLng(
                59.9112,
                10.7449
            )
        )

    }

    private fun moveToLocation(latLng: LatLng) {
        mMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng, 13.0f
            )
        )
    }

    private fun updateLocationUI() {
        mMap?.apply {
            isMyLocationEnabled = true;
            uiSettings.isMyLocationButtonEnabled = true;


        }
    }

}

fun Address.createStringAddress(): String {
    val strReturnedAddress = StringBuilder()
    for (i in 0..maxAddressLineIndex) {
        strReturnedAddress.append(getAddressLine(i)).append("\n")
    }
    return strReturnedAddress.toString()
}
