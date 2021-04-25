package com.barrientos.proyecto1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.barrientos.proyecto1.DetalleShowActivity2.Companion.SHOW
import com.barrientos.proyecto1.Modelos.show
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detalle_show.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [DetalleShowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetalleShowFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: show? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(SHOW) as show
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.titulo.text = param1?.name
        view.descripcion.text = param1?.summary

        val url = param1?.image?.original
        Glide
            .with(activity!!)
            .load((url))
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(view.portada)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetalleShowFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: show) =
            DetalleShowFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SHOW, param1)
                }
            }
    }
}