package com.anacarolina.diriodohumor

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import com.google.android.flexbox.FlexboxLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NovaEntrada : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "data"
        const val EXTRA_EMOCAO = "emocao"
        const val EXTRA_CATEGORIA = "categoria"
        const val EXTRA_RESUMO = "resumo"
    }

    //Data e Calendário
    private lateinit var editData: EditText
    private val calendario = Calendar.getInstance()

    private lateinit var editResumo: EditText
    private lateinit var flexBoxEmocao: FlexboxLayout
    private lateinit var spinnerCategoria: Spinner
    private lateinit var buttonAdicionar: Button
    private lateinit var buttonCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_entrada)

        //Exibe o Calendario do EditData
        fun showDatePicker() {
            val datePickerDialog = DatePickerDialog(
                this, { datePicker, ano: Int, mes: Int, diaDoMes: Int ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, diaDoMes)
                    val dataFormatada =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val dataFormatadaa = dataFormatada.format(dataSelecionada.time)
                    editData.setText(dataFormatadaa)

                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        editData = findViewById(R.id.Edit_data)
        editData.setOnClickListener {
            showDatePicker()
        }



        //Verifica qual emoção foi selecionada
        flexBoxEmocao = findViewById(R.id.flexBox_emocao)
        var emocaoString = " "
        for (i in 0 until flexBoxEmocao.childCount) {
            val view = flexBoxEmocao.getChildAt(i)
            if (view is RadioButton && view.isChecked) {
                emocaoString = view.text.toString().trim()
                break
            }
        }

        //Adiciona as opções ao Spinner
        spinnerCategoria = findViewById(R.id.Spinner_categoria)
        val opcoesSpinner = listOf("Trabalho", "Saúde", "Amor", "Cotidiano", "Financeiro")
        val adaptador = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, opcoesSpinner
        )
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adaptador


        //Evento Click do botão Cancelar
        buttonCancelar = findViewById(R.id.button_cancelar)
        buttonCancelar.setOnClickListener {
            finish()
        }

        //Evento Click do botão Adicionar
        buttonAdicionar = findViewById(R.id.button_adicionar)
        buttonAdicionar.setOnClickListener {

            //Pegar a data no DatePicker
            val dataString = editData.text.toString().trim()

            //Verifica qual emoção foi selecionada
            flexBoxEmocao = findViewById(R.id.flexBox_emocao)
            var emocaoString = " "
            for (i in 0 until flexBoxEmocao.childCount) {
                val view = flexBoxEmocao.getChildAt(i)
                if (view is RadioButton && view.isChecked) {
                    emocaoString = view.text.toString().trim()
                    break
                }
            }

            //Pegar a categoria selecionada no Spinner
           val categoriaSelecionada = spinnerCategoria.selectedItem
            val categoriaString = if (categoriaSelecionada != null) {
                categoriaSelecionada.toString().trim()
            } else{
                " "
            }

            //Pegar o texto do resumo
            editResumo = findViewById(R.id.Edit_resumo)
            val resumoString = editResumo.text.toString().trim()


            //Verificando validade das entradas
            if (dataString.isBlank() || emocaoString.isBlank() || resumoString.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show()
            }

            //Quando clicar no botão as informações irem pro CardView na Recycler
            val result = Intent().apply {
                putExtra(EXTRA_DATA, dataString)
                putExtra(EXTRA_EMOCAO, emocaoString)
                putExtra(EXTRA_CATEGORIA, categoriaString)
                putExtra(EXTRA_RESUMO, resumoString)

            }
            setResult(RESULT_OK, result)
            finish()
        }

    }
}