package no.kristiania.android.locationaware

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import java.util.*


private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = UPDATE_INTERVAL_IN_MILLISECONDS/2

class MainActivity : AppCompatActivity() {


    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                onNewLocation(locationResult.lastLocation)
            }
        }
        mLocationRequest = createLocationRequest()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    private fun onNewLocation(lastLocation: Location) {
        // GeoCoder api to ge the address from the location data.
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)
        val returnedAddress: Address = addresses[0]

        Toast.makeText(
            this,
            "${lastLocation.latitude.toString()} and ${lastLocation.longitude} ${returnedAddress.createStringAddress()}",
            Toast.LENGTH_SHORT ).show()

    }

    private fun startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                Looper.getMainLooper())
    }

    /**
     * Sets the location request parameters.
     */
    private fun createLocationRequest() : LocationRequest {
        return LocationRequest().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    // Extension function to create string address from the address object.
    fun Address.createStringAddress() : String {
        val strReturnedAddress = StringBuilder()
        for (i in 0..maxAddressLineIndex) {
            strReturnedAddress.append(getAddressLine(i)).append("\n")
        }
        return strReturnedAddress.toString()
    }


}
