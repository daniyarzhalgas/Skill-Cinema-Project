package sdu.project.cinemaapp.presentation.ui.galleryPage

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import sdu.project.cinemaapp.domain.model.MovieImage
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val state = _state.asStateFlow()

    private val _gallery = MutableStateFlow<List<MovieImage>>(emptyList())
    val gallery = _gallery.asStateFlow()

    init {
        fetchGallery()
    }

    private fun fetchGallery() {
        _state.value = ScreenState.Loading

        try {

            val gallery = sharedViewModel.getDataOfType<MovieImage>()

            if(gallery.isEmpty()){
                Log.e("GalleryViewModel", "Gallery is empty")
                _state.value = ScreenState.Error
                return
            }

            _gallery.value = gallery
            _state.value = ScreenState.Success

        }catch (e: Exception){
            Log.e("GalleryViewModel", "Error fetching gallery, $e")
            _state.value = ScreenState.Error
        }
    }

}