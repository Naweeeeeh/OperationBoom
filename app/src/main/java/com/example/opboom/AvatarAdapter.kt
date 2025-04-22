package com.example.opboom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat

class AvatarAdapter(
    private val context: Context,
    private val avatarIcons: List<Int>,
    private val onAvatarSelected: (Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = avatarIcons.size
    override fun getItem(position: Int): Int = avatarIcons[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_avatar, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.avatarItem)
        imageView.setImageResource(avatarIcons[position])

        view.setOnClickListener {
            onAvatarSelected(avatarIcons[position])
        }

        return view
    }
}