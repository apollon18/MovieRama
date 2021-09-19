package apo.mor.movierama.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import apo.mor.movierama.Models.Cast
import apo.mor.movierama.databinding.ListItemCastBinding

class CastAdapter(
    private var castList: ArrayList<Cast>?,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderCastList(
            ListItemCastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val actor = castList?.get(holder.absoluteAdapterPosition)
        val viewHolder = holder as ViewHolderCastList
        viewHolder.characterName.text = actor?.character
        viewHolder.name.text = actor?.name
    }

    override fun getItemCount(): Int {
        return castList?.size ?: 0
    }

    class ViewHolderCastList(binding: ListItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var name: TextView = binding.actorName
        var characterName: TextView = binding.characterName
    }
}