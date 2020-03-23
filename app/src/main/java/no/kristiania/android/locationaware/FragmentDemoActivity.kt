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
import com.google.android.gms.maps.model.MarkerOptions

class FragmentDemoActivity : AppCompatActivity(), OnMapReadyCallback {

    val mapFragment = SupportMapFragment()

    lateinit var mMaps: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frgment_demo)

        // Create supportmapFragment
        // set async listener
        mapFragment.getMapAsync(this)

        if (permissionCheck()) {
            openMaps()
        }
        // Permission check
        // load map fragment
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
                    openMaps()
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

    private fun openMaps() {
        supportFragmentManager.beginTransaction()
            .add(R.id.frame, mapFragment, "map")
            .commit()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.mMaps = googleMap
        mMaps.isMyLocationEnabled = true
        mMaps.uiSettings.isMyLocationButtonEnabled = true

        val marker = MarkerOptions()
            .position(LatLng(59.92, 10.71))
            .title("Majorsuen")
        mMaps.addMarker(marker)
        val camera = CameraUpdateFactory.newLatLngZoom(LatLng(59.92, 10.71), 14.0f)
        mMaps.moveCamera(camera)

    }

}
