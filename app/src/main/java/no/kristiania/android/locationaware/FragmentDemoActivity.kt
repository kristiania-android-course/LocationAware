package no.kristiania.android.locationaware

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FragmentDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frgment_demo)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.first_frame, FirstFragment(), "First")
            .commit()
    }
}
