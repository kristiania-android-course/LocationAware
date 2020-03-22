package no.kristiania.android.locationaware

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class FragmentDemoActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frgment_demo)


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

}
