package com.borispuhacin.imagesearcher.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.borispuhacin.imagesearcher.R
import com.borispuhacin.imagesearcher.data.UnsplashPhoto
import com.borispuhacin.imagesearcher.databinding.ItemPhotoBinding

class PhotoAdapter(private val listener: OnPhotoClickListener) :
    PagingDataAdapter<UnsplashPhoto, PhotoAdapter.PhotoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) listener.onItemClick(item)
                }
            }
        }

        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                val photoUrl = photo.urls.regular.toUri().buildUpon().scheme("https").build()
                imageViewPhoto.load(photoUrl) {
                    crossfade(true)
                    error(R.drawable.ic_broken_image)
                }
                val avatarPhoto =
                    photo.user.profile_image.small.toUri().buildUpon().scheme("https").build()
                imageViewUserAvatar.load(avatarPhoto) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    error(R.drawable.ic_broken_image)
                }
                textViewUserName.text = photo.user.username
            }
        }
    }

    interface OnPhotoClickListener {
        fun onItemClick(photo: UnsplashPhoto)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
        override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem == newItem
        }
    }

}