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


    private lateinit var mMap: GoogleMap

    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frgment_demo)

        mapFragment = SupportMapFragment()
        mapFragment.getMapAsync(this)

        if (permissionCheck()) {
            supportFragmentManager.beginTransaction().add(R.id.frame, mapFragment, "map").commit()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
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
                    supportFragmentManager.beginTransaction().add(R.id.frame, mapFragment, "map")
                        .commit()
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

    override fun onMapReady(googleMap: GoogleMap) {
        this.mMap = googleMap
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        val update = CameraUpdateFactory.newLatLngZoom(LatLng(59.9812, 10.742), 13.0f)
        mMap.moveCamera(update)

    }

}
