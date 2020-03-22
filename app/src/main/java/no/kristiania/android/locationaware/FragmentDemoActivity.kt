package no.kristiania.android.locationaware

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FragmentDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frgment_demo)

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
