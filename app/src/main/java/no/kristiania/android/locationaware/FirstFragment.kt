package no.kristiania.android.locationaware

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.view.*

/**
 * A simple [Fragment] subclass.
 */
class FirstFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_first, container, false)
        rootView.next.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.first_frame, SecondFragment(), "Second")
                ?.addToBackStack("Second")
                ?.commit()
        }
        return rootView
    }

}
