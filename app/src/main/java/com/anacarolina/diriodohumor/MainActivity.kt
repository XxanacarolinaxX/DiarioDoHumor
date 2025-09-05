package com.anacarolina.diriodohumor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var adaptador: AdaptadorRecycler
    private val listaEntrada = mutableListOf<Registro>()


    private val relacionarEntrada =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                val data = resultado.data
                if (data != null) {
                    val dataString = data.getStringExtra(NovaEntrada.EXTRA_DATA) ?: ""
                    val emocaoString = data.getStringExtra(NovaEntrada.EXTRA_EMOCAO) ?: ""
                    val categoriaString = data.getStringExtra(NovaEntrada.EXTRA_CATEGORIA) ?: ""
                    val resumoString = data.getStringExtra(NovaEntrada.EXTRA_RESUMO) ?: ""

                    val registro = Registro(
                        dataString,
                        emocaoString,
                        categoriaString,
                        resumoString
                    )
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

        //Passar as informações do card para a InfoDiaria
        adaptador = AdaptadorRecycler(listaEntrada) { registro ->
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


    }

}