package com.barrientos.proyecto1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.barrientos.proyecto1.Modelos.show
import kotlinx.android.synthetic.main.activity_detalle_show2.*

class DetalleShowActivity2 : AppCompatActivity() {

    companion object {
        public val SHOW = "SHOW"
    }
    lateinit var showseleccionado : show
    lateinit var Shared: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_show2)

        showseleccionado = intent.getSerializableExtra(SHOW) as show
        Toast.makeText(this, showseleccionado.name, Toast.LENGTH_LONG).show()
        Shared = getSharedPreferences(getString(R.string.favoritos),Context.MODE_PRIVATE)

        val demoCollectionAdapter = DemoPageAdapter(supportFragmentManager)
        paginador.adapter = demoCollectionAdapter
        tabs_layout.setupWithViewPager(paginador)

        favorito.setOnClickListener{

            guardarFavoritos(showseleccionado.name)

            }
        cambiarIcono()
        }


    inner class DemoPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return DetalleShowFragment.newInstance(showseleccionado)
                1 -> return InforacionTecnicaFragment.newInstance(showseleccionado)
                else -> return DetalleShowFragment.newInstance(showseleccionado)
            }
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Resumen"
                1 -> "Detalle"
                else -> "Resumen"
            }
        }
        }
        fun guardarFavoritos(nombre:String){

            val esfavorito = !(Shared.getBoolean(nombre, false))
            with(Shared.edit()) {
                putBoolean(nombre, esfavorito)
                apply()
                cambiarIcono()
            }
        }

        fun cambiarIcono(){

                if(Shared.getBoolean(showseleccionado.name, false)){
                    favorito.setImageResource(R.drawable.ic_baseline_favorite_relleno_24)
                }else {
                    favorito.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
        }
}