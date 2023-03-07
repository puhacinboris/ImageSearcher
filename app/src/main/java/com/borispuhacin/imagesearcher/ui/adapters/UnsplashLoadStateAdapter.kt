package com.borispuhacin.imagesearcher.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.borispuhacin.imagesearcher.databinding.LoadStateFooterBinding

class UnsplashLoadStateAdapter(private val retry: () -> Unit ) :
    LoadStateAdapter<UnsplashLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: LoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetryLoadFooter.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBarLoadFooter.isVisible = loadState is LoadState.Loading
                textViewErrorMsg.isVisible = loadState !is LoadState.Loading
                btnRetryLoadFooter.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(LoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}