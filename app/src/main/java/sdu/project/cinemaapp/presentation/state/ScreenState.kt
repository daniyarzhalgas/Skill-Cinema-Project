package sdu.project.cinemaapp.presentation.state


sealed interface ScreenState{
    object Initial: ScreenState
    object Loading: ScreenState
    object Success: ScreenState
    object Error: ScreenState
}