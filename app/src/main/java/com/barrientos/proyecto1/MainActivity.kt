package com.barrientos.proyecto1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barrientos.proyecto1.DetalleShowActivity2.Companion.SHOW
import com.barrientos.proyecto1.Modelos.ContenedorShow
import com.barrientos.proyecto1.Modelos.show
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.show_item.view.*
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private val peliculas: ArrayList<show> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tipContenedor = object  : TypeToken<List<ContenedorShow>>(){}.type
        val  contenedorShows = Gson().fromJson<List<ContenedorShow>>(loadJSONFromAsset(this),tipContenedor)
        for (contenedor in contenedorShows){
            peliculas.add((contenedor.show))
        }
        lista_Shows.layoutManager = GridLayoutManager(this,3)
        lista_Shows.adapter = RecyclerShowAdapter(peliculas)


        text_buscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, R.string.buscando, Toast.LENGTH_LONG).show()
                (lista_Shows.adapter as RecyclerShowAdapter).filter.filter(query)

                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                 (lista_Shows.adapter as RecyclerShowAdapter).filter.filter(newText)
                return false            }
        })
    }

    inner class RecyclerShowAdapter(private val peliculas: ArrayList<show>):
            RecyclerView.Adapter<RecyclerShowAdapter.ShowHolder>(),
            Filterable {

        var peliculasFiltradas : MutableList<show> = arrayListOf()
        init{
            peliculasFiltradas = peliculas
        }
        inner class ShowHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bind(Show: show) = with(itemView) {
                titulo_Show.text = Show.name
                if (Show.image != null){
                    val url = Show.image.medium
                    Glide.with(this)
                            .load(url)
                            .placeholder(R.drawable.ic_baseline_image_24)
                            .into(portada_Show);
                }
                itemView.setOnClickListener {
                    val intent = Intent(this@MainActivity, DetalleShowActivity2:: class.java)
                    intent.putExtra(SHOW , Show )
                    startActivity(intent)
                }

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.show_item, parent, false)
            return ShowHolder(view)        }

        override fun onBindViewHolder(holder: ShowHolder, position: Int) {
            holder.bind(peliculasFiltradas.get(position))
        }

        override fun getItemCount(): Int {
            return peliculasFiltradas.size
        }

       override fun getFilter(): Filter {
           return object : Filter(){
               override fun performFiltering(constraint: CharSequence?): FilterResults {
                   val palabraBuscar = constraint.toString()
                   val resultadoFiltro = ArrayList<show>()
                   for(show in peliculas){

                       if (show.name.toLowerCase().contains(palabraBuscar.toLowerCase())){
                           resultadoFiltro.add(show)
                       }
                   }
                   val filterResults = FilterResults()
                   filterResults.values = resultadoFiltro
                   return filterResults
               }

               override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                   peliculasFiltradas = filterResults?.values as MutableList<show>
                   notifyDataSetChanged()

                   if(peliculasFiltradas.isEmpty()){
                       text_no_encontrado.visibility = View.VISIBLE
                   }else{
                       text_no_encontrado.visibility = View.GONE
                   }

               }

           }        }
    }

    fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.getAssets().open("shows.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}