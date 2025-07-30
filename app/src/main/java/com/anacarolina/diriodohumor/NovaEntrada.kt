package com.anacarolina.diriodohumor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner

class NovaEntrada : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_entrada)

        val data = findViewById<EditText>(R.id.Edit_data)
        val emocao1 = findViewById<RadioGroup>(R.id.radioGroup_emocao1)
        val emocao2 = findViewById<RadioGroup>(R.id.radioGroup_emocao2)
        val categoria = findViewById<Spinner>(R.id.Spinner_categoria)
        val buttonSalvar = findViewById<Button>(R.id.button_salvar)

        buttonSalvar.setOnClickListener {

        }

    }
}