package com.anacarolina.diriodohumor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorEntrada(private val listaEntrada: MutableList<Registro>) :
    RecyclerView.Adapter<AdaptadorEntrada.RegistroViewHolder>() {

    //Guarda as referências dos TextViews de um item da RecyclerView.
    class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewData: TextView = itemView.findViewById(R.id.text_CardData)
        val textViewEmocao: TextView = itemView.findViewById(R.id.text_CardEmocao)
        val textViewCategoria: TextView = itemView.findViewById(R.id.text_CardCategoria)
        val textViewResumo: TextView = itemView.findViewById(R.id.text_CardResumo)
    }

    //Cria a View para cada item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_registro, parent, false)
        return RegistroViewHolder(view)
    }

    //Preenche a View com os dados
    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val entrada = listaEntrada[position]
        holder.textViewData.text = entrada.data.toString()
        holder.textViewEmocao.text = entrada.emocao.toString()
        holder.textViewCategoria.text = entrada.categoria.toString()
        holder.textViewResumo.text = entrada.resumo.toString()
    }
    //Adiciona um novo item à lista
    override fun getItemCount(): Int = listaEntrada.size

    //Adiciona um novo registro à lista e atualiza a RecyclerView.
    fun adicionarEntrada(entrada: Registro){
        listaEntrada.add(0, entrada)
        notifyItemInserted(0)
    }


}
