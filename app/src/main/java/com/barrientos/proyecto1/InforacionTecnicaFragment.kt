package com.barrientos.proyecto1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.barrientos.proyecto1.DetalleShowActivity2.Companion.SHOW
import com.barrientos.proyecto1.Modelos.show
import kotlinx.android.synthetic.main.fragment_inforacion_tecnica.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [InforacionTecnicaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InforacionTecnicaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: show? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(SHOW) as show?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inforacion_tecnica, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.titulo_informacion.text = param1?.name
        view.Runtime.text = param1?.runtime.toString()
        view.Genero.text = param1?.genres.toString()
        view.Premier.text = param1?.premiered

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InforacionTecnicaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: show) =
            InforacionTecnicaFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SHOW, param1)

                }
            }
    }
}