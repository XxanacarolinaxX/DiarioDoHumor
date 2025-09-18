package com.anacarolina.diriodohumor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.Normalizer
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var adaptador: AdaptadorRecycler
    private val listaEntrada = mutableListOf<Registro>()
    private val listaInteira = mutableListOf<Registro>()
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    //Recebe os dados da nova entrada
    private val relacionarEntrada =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                val data = resultado.data
                if (data != null) {
                    //Pega os dados da nova entrada
                    val dataString = data.getStringExtra(NovaEntrada.EXTRA_DATA) ?: ""
                    val emocaoString = data.getStringExtra(NovaEntrada.EXTRA_EMOCAO) ?: ""
                    val categoriaString = data.getStringExtra(NovaEntrada.EXTRA_CATEGORIA) ?: ""
                    val resumoString = data.getStringExtra(NovaEntrada.EXTRA_RESUMO) ?: ""

                    //Cria um novo registro com os dados da nova entrada
                    val registro = Registro(
                        dataString,
                        emocaoString,
                        categoriaString,
                        resumoString
                    )

                    //Adiciona o novo registro à lista e ao adaptador
                    adaptador.adicionarEntrada(registro)

                }
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Apagar um registro da RecyclerView
        val swipeHandler = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                listaEntrada.removeAt(position)
                adaptador.notifyItemRemoved(position)
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //Passar as informações do card para a InfoDiaria
        adaptador = AdaptadorRecycler(listaEntrada, listaInteira) { registro ->
            val intent = Intent(this, InfoDiaria::class.java).apply {
                putExtra("data", registro.data)
                putExtra("emocao", registro.emocao)
                putExtra("categoria", registro.categoria)
                putExtra("sentindo", registro.resumo)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adaptador

        //Botão para adicionar nova entrada
        val buttonAdd = findViewById<Button>(R.id.button_add)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, NovaEntrada::class.java)
            relacionarEntrada.launch(intent)
        }

        //SearchView
        searchView = findViewById(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(novoTexto: String?): Boolean {

                //Tirar acentos
                fun tirarAcento(texto: String): String {
                    val semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
                    return semAcento.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
                }

                //SearchView
                val searchText = tirarAcento(novoTexto?.lowercase(Locale.getDefault()) ?: "")

                //Filtra os registros
                val searchViewLista = if (searchText.isNotEmpty()) {

                    listaInteira.filter { registro ->
                        tirarAcento(registro.data.lowercase(Locale.getDefault())).contains(searchText) ||
                        tirarAcento(registro.emocao.lowercase(Locale.getDefault())).contains(searchText) ||
                        tirarAcento(registro.categoria.lowercase(Locale.getDefault())).contains(searchText) ||
                        tirarAcento(registro.resumo.lowercase(Locale.getDefault())).contains(searchText)
                    }
                } else {
                    listaInteira
                }
                listaEntrada.clear()
                listaEntrada.addAll(searchViewLista)
                adaptador.notifyDataSetChanged()
                return true


            }

        })


    }
}