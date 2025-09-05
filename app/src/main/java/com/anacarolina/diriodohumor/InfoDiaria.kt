package com.anacarolina.diriodohumor

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton


class InfoDiaria : AppCompatActivity() {

    lateinit var buttonVoltar: AppCompatButton
    lateinit var tvEntradaInfoData: TextView
    lateinit var tvEntradaInfoEmocao: TextView
    lateinit var tvEntradaInfoCategoria: TextView
    lateinit var tvEntradaInfoSentindo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_diaria)

        //Recuperando dados da Intent
        val data = intent.getStringExtra("data")
        val emocao = intent.getStringExtra("emocao")
        val categoria = intent.getStringExtra("categoria")
        val sentido = intent.getStringExtra("sentindo")

        //Preenchendo os campos da tela
        tvEntradaInfoData = findViewById(R.id.tv_entrada_info_data)
        tvEntradaInfoData.text = data

        tvEntradaInfoEmocao = findViewById(R.id.tv_entrada_info_emocao)
        tvEntradaInfoEmocao.text = emocao

        tvEntradaInfoCategoria = findViewById(R.id.tv_entrada_info_categoria)
        tvEntradaInfoCategoria.text = categoria

        tvEntradaInfoSentindo = findViewById(R.id.tv_entrada_info_sentindo)
        tvEntradaInfoSentindo.text = sentido


        //Evento Click do botão Voltar
        buttonVoltar = findViewById(R.id.button_voltar)
        buttonVoltar.setOnClickListener {
            finish()
        }

    }
}