package sdu.project.cinemaapp.presentation.ui.galleryPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen
import sdu.project.cinemaapp.domain.model.MovieImage
import sdu.project.cinemaapp.presentation.ui.details.MovieDetailsViewModel

@Composable
fun GalleryScreen(
    navController: NavHostController,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val images by viewModel.gallery.collectAsStateWithLifecycle()

    when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Success -> {
            Log.i("GalleryScreen", "Images: $images")
            Column {
//                ListImages(images)
                GalleryScreen(images, navController, viewModel)
            }

        }

        is ScreenState.Error -> ErrorScreen()
    }
}

@Composable
fun GalleryScreen(
    images: List<MovieImage>,
    navController: NavHostController,
    viewModel: GalleryViewModel
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)

    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(R.drawable.caret_left), contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        //todo navigation back to movie details
//                        viewModel.event(navController, GalleryViewModel.OnBackClick)
                    }
            )
            Text(
                text = "Галерея",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.graphiksemibold)),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(images.chunked(2)) { index, imageRow ->
                if (index % 2 == 0) {
                    AsyncImage(
                        model = imageRow[0].imageUrl,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (image in imageRow) {
                            AsyncImage(
                                model = image.imageUrl,
                                contentDescription = "",
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }


//        var index = 1
//        LazyColumn (verticalArrangement = Arrangement.spacedBy(10.dp)){
//            items(images) {
//                if (index % 3 == 0) {
//                    AsyncImage(
//                        model = it.imageUrl,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        contentScale = ContentScale.Crop
//                    )
//                }
//                else{
//                    AsyncImage(
//                        model = it.imageUrl,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxWidth(0.45f),
//                        contentScale = ContentScale.Crop
//                    )
//                }
//                index++
//            }
//        }

//        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
//            items(images) {
//                AsyncImage(
//                    model = it.imageUrl,
//                    contentDescription = null,
//                    modifier = Modifier.height(108.dp).fillMaxWidth(),
//                    contentScale = ContentScale.Crop
//                )
//            }
//        }
    }
}
