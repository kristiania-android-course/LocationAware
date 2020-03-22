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

class FragmentDemoActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var mapFragment: SupportMapFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frgment_demo)
        if (permissionCheck()) {
            setMapFragment()
        }
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

    }

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
