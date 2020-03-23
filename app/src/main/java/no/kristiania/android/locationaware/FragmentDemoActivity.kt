package no.kristiania.android.locationaware

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import no.kristiania.android.locationaware.fragments.FirstFragment

class FragmentDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frgment_demo)
        val firstFragment = FirstFragment.newInstance("bundle data")
        FirstFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame, firstFragment, "first").commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
