package com.borispuhacin.imagesearcher.ui.fragments.gallery

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.borispuhacin.imagesearcher.R
import com.borispuhacin.imagesearcher.data.UnsplashPhoto
import com.borispuhacin.imagesearcher.databinding.FragmentGalleryBinding
import com.borispuhacin.imagesearcher.ui.adapters.PhotoAdapter
import com.borispuhacin.imagesearcher.ui.adapters.UnsplashLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(), PhotoAdapter.OnPhotoClickListener, MenuProvider {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.addMenuProvider(this, viewLifecycleOwner)

        val adapter = PhotoAdapter(this)

        binding.apply {
            recyclerViewGalleryList.setHasFixedSize(true)
            recyclerViewGalleryList.itemAnimator = null
        }

        binding.recyclerViewGalleryList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = UnsplashLoadStateAdapter(adapter::retry),
            footer = UnsplashLoadStateAdapter(adapter::retry)
        )

        binding.btnRetry.setOnClickListener { adapter.retry() }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewGalleryList.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewResultsNotLoaded.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    recyclerViewGalleryList.isVisible = false
                    textViewNoResultsFound.isVisible = true
                } else textViewNoResultsFound.isVisible = false
            }
        }
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search_gallery, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerViewGalleryList.scrollToPosition(0)
                    viewModel.searchPhoto(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return menuItem.itemId == R.id.action_search
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}