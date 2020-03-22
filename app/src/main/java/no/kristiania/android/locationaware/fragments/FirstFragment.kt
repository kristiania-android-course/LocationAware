package no.kristiania.android.locationaware.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.view.*
import no.kristiania.android.locationaware.R


/**
 *
 *
 * Created by:  Arun Pillai
 * Email: arun.vijayan.pillai@shortcut.no
 *
 * Date: 22 March 2020
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_first, container, false)
        rootView.next.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame, SecondFragment(), "second")
                    .addToBackStack("second")
                    .commit()
            }
        }
        return rootView
    }
}