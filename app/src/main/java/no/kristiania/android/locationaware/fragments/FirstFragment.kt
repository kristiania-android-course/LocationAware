package no.kristiania.android.locationaware.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import no.kristiania.android.locationaware.R


/**
 *
 *
 * Created by:  Arun Pillai
 * Email: arun.vijayan.pillai@shortcut.no
 *
 * Date: 23 March 2020
 */
class FirstFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, viewGroup, false)
        view.next.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.frame, SecondFragment(), "Second")
                ?.addToBackStack("firstinsttack")?.commit()
        }
        return view
    }
}