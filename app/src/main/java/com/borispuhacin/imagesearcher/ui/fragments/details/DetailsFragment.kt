package com.borispuhacin.imagesearcher.ui.fragments.details

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.borispuhacin.imagesearcher.R
import com.borispuhacin.imagesearcher.databinding.FragmentDetailsBinding
import com.borispuhacin.imagesearcher.utils.Utils
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {
    private var readPermissionGranted = false
    private var writePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            readPermissionGranted =
                permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: readPermissionGranted
            writePermissionGranted =
                permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: writePermissionGranted
        }

        bindingElements()
    }

    private fun bindingElements() {
        binding.apply {
            val photoUrl = args.photo.urls.regular.toUri().buildUpon().scheme("https").build()
            imageViewPhoto.load(photoUrl) {
                crossfade(true)
                error(R.drawable.ic_broken_image)
            }

            val avatarPhoto =
                args.photo.user.profile_image.small.toUri().buildUpon().scheme("https").build()
            imageViewUserDetailsAvatar.load(avatarPhoto) {
                crossfade(true)
                transformations(CircleCropTransformation())
                error(R.drawable.ic_broken_image)
            }

            textViewPhotoDescription.text = args.photo.description

            val attributionUri = Uri.parse(args.photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, attributionUri)

            textViewUserNameDetails.apply {
                text = getString(R.string.photo_by, args.photo.user.username)
                setOnClickListener { context.startActivity(intent) }
                paint.isUnderlineText = true
            }

            imageViewDownload.setOnClickListener {
                updateOrRequestPermissions()

                val bitmap: Bitmap = Utils.imageViewToBitmap(imageViewPhoto)!!

                val isSaved = when {
                    writePermissionGranted ->
                        Utils.saveImageToExternalStorage(
                            "${System.currentTimeMillis()}.jpg", bitmap, requireActivity()
                        )
                    else -> false
                }

                if (isSaved) {
                    Snackbar.make(it, "Photo saved", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(it, "Photo couldn't be saved", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateOrRequestPermissions() {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val hasWritePermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || minSdk29

        val permissionsRequest = mutableListOf<String>()

        if (!writePermissionGranted) {
            permissionsRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!readPermissionGranted) {
            permissionsRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionsRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsRequest.toTypedArray())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}