package sdu.project.cinemaapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun SearchScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search") {
        composable("search") { SearchScreenContent(navController) }
        composable("filter") { FilterScreen(navController) }
        composable("country") { CountryScreen(navController) } // Added "country" destination
        composable("genre") { GenreScreen(navController) }
        composable("period") { PeriodScreen(navController) }
    }
}
//@Composable
//fun SearchScreen() {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "search") {
//        composable("search") { SearchScreenContent(navController) }
//        composable("filter") { FilterScreen(navController) }
//    }
//}

@Composable
fun SearchScreenContent(navController: NavHostController) {
    val searchQuery = remember { mutableStateOf("") }
    val movies = listOf(
        Movie("Example Movie 1", "2021, Thriller", "8.1", "https://via.placeholder.com/150"),
        Movie("Example Movie 2", "2022, Action", "7.5", "https://via.placeholder.com/150"),
        Movie("Example Movie 3", "2020, Drama", "9.0", "https://via.placeholder.com/150"),
        Movie("Example Movie 4", "2021, Sci-Fi", "6.8", "https://via.placeholder.com/150")
    )

    val filteredMovies = movies.filter {
        it.title.contains(searchQuery.value, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            query = searchQuery.value,
            onQueryChange = { searchQuery.value = it },
            onFilterClick = { navController.navigate("filter") } // Navigate to FilterScreen
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (filteredMovies.isEmpty()) {
            Text(
                text = "К сожалению, по вашему запросу ничего не найдено",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        } else {
            LazyColumn {
                items(filteredMovies) { movie ->
                    MovieItem(movie)
                }
            }
        }
    }
}

@Composable
fun FilterScreen(navController: NavHostController) {
    // State for the slider's value, initialized with a starting value of 5f (or any other value)
    var sliderValue by remember { mutableStateOf(5f) }
    var sliderLabel by remember { mutableStateOf("любой") }

    // Separate states for the "Показывать" and "Сортировать" button groups
    var selectedIndexShow by remember { mutableStateOf(0) } // State for "Показывать" button group
    var selectedIndexSort by remember { mutableStateOf(0) } // State for "Сортировать" button group

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Настройки поиска",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // "Показывать" Section
        Text(
            text = "Показывать",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Toggle Button Group for "Все, Фильмы, Сериалы"
        FilterToggleButtonGroup(
            options = listOf("Все", "Фильмы", "Сериалы"),
            selectedIndex = selectedIndexShow,
            onOptionSelected = { selectedIndexShow = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Filter Options
        FilterOptionRow(label = "Страна", value = "Россия", onClick = { navController.navigate("country") })
        Divider(modifier = Modifier.height(1.dp).fillMaxWidth(), color = Color.Gray)
        FilterOptionRow(label = "Жанр", value = "Комедия", onClick = { navController.navigate("genre") })
        Divider(modifier = Modifier.height(1.dp).fillMaxWidth(), color = Color.Gray)
        FilterOptionRow(label = "Год", value = "с 1998 до 2017", onClick = { navController.navigate("period") })
        Divider(modifier = Modifier.height(1.dp).fillMaxWidth(), color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // "Рейтинг" Section (Slider)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Рейтинг",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )

            // Display current value of the slider (initially "Anything", then dynamically update)
            Text(
                text = sliderLabel,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Slider for rating
        Slider(
            value = sliderValue,
            onValueChange = { value ->
                sliderValue = value
                sliderLabel = if (value == 5f) "Anything" else value.toInt().toString() // Change label dynamically
            },
            valueRange = 1f..10f,  // Range of 1 to 10
            steps = 8,             // Adds 8 steps between 1 and 10 (9 discrete values)
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "1",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "10",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(modifier = Modifier.height(1.dp).fillMaxWidth(), color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // "Сортировать" Section
        Text(
            text = "Сортировать",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Toggle Button Group for "Дата, Популярность, Рейтинг"
        FilterToggleButtonGroup(
            options = listOf("Дата", "Популярность", "Рейтинг"),
            selectedIndex = selectedIndexSort,
            onOptionSelected = { selectedIndexSort = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider(modifier = Modifier.height(1.dp).fillMaxWidth(), color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        // "Не просмотрен" Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.VisibilityOff,
                contentDescription = "Не просмотрен"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Не просмотрен", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun FilterToggleButtonGroup(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(50))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(50)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEachIndexed { index, text ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        when (index) {
                            0 -> RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                            options.size - 1 -> RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                            else -> RoundedCornerShape(0.dp)
                        }
                    )
                    .background(
                        if (index == selectedIndex) Color(0xFF6200EE) else Color.Transparent
                    )
                    .clickable { onOptionSelected(index) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = if (index == selectedIndex) Color.White else Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}





@Composable
fun CountryScreen(navController: NavHostController) {
    val countries = listOf("Россия", "Великобритания", "Германия", "США", "Франция")
    val searchQuery = remember { mutableStateOf("") }

    // Track selected country
    var selectedCountry by remember { mutableStateOf("Россия") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title and back button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            // Centered title
            Text(
                text = "Страна",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            // Back button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search bar with rounded design
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF0F0F0), // Light gray background
                    shape = RoundedCornerShape(24.dp) // Rounded corners
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                decorationBox = { innerTextField ->
                    if (searchQuery.value.isEmpty()) {
                        Text(
                            text = "Введите страну",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    innerTextField()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of countries with selectable items
        LazyColumn {
            items(countries.filter { it.contains(searchQuery.value, ignoreCase = true) }) { country ->
                Text(
                    text = country,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedCountry = country }
                        .padding(vertical = 8.dp)
                        .background(
                            if (selectedCountry == country)
                                Color(0xFFB5B5C9).copy(alpha = 0.3f) // Background color when selected
                            else
                                Color.Transparent, // Transparent when not selected
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    }
}


@Composable
fun GenreScreen(navController: NavHostController) {
    val genres = listOf("Комедия", "Мелодрама", "Боевик", "Вестерн", "Драма")
    val searchQuery = remember { mutableStateOf("") }

    // Track selected genre
    var selectedGenre by remember { mutableStateOf("Комедия") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title and back button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            // Centered title
            Text(
                text = "Жанр",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            // Back button
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search bar with rounded design
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF0F0F0), // Light gray background
                    shape = RoundedCornerShape(24.dp) // Rounded corners
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                decorationBox = { innerTextField ->
                    if (searchQuery.value.isEmpty()) {
                        Text(
                            text = "Введите жанр",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    innerTextField()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of genres with selectable items
        LazyColumn {
            items(genres.filter { it.contains(searchQuery.value, ignoreCase = true) }) { genre ->
                Text(
                    text = genre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedGenre = genre } // Handle genre selection
                        .padding(vertical = 8.dp)
                        .background(
                            if (selectedGenre == genre)
                                Color(0xFFB5B5C9).copy(alpha = 0.3f) // Background color when selected
                            else
                                Color.Transparent, // Transparent when not selected
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun PeriodScreen(navController: NavHostController) {
    val years = (1998..2009).toList()
    var selectedStartYear by remember { mutableStateOf(1998) }
    var selectedEndYear by remember { mutableStateOf(2009) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Back button and screen title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Период",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        // "Искать в период с" section
        Text(
            text = "Искать в период с",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 10.dp),
            color = Color.Gray
        )

        YearSelector(
            selectedYear = selectedStartYear,
            onYearSelected = { selectedStartYear = it },
            years = years,
        )


        // "Искать в период до" section
        Text(
            text = "Искать в период до",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 10.dp),
            color = Color.Gray
        )

        YearSelector(
            selectedYear = selectedEndYear,
            onYearSelected = { selectedEndYear = it },
            years = years
        )
        Spacer(modifier = Modifier.height(26.dp))

        // "Выбрать" button
        Button(
            onClick = {
                // Handle selection logic here
            },
            modifier = Modifier
                .width(125.dp)
                .height(48.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F4DFF))
        ) {
            Text(
                text = "Выбрать",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun YearSelector(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
    years: List<Int>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        // Header with selected range and navigation arrows
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${years.first()} - ${years.last()}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = Color(0xFF3D3BFF), // Blue color for the range
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = { /* Handle previous range */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous"
                )
            }
            IconButton(onClick = { /* Handle next range */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next"
                )
            }
        }


        // Grid of years
        val rows = years.chunked(3)
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                row.forEach { year ->
                    Text(
                        text = year.toString(),
                        modifier = Modifier
                            .clickable { onYearSelected(year) }
                            .padding(4.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (year == selectedYear) Color(0xFF4F4DFF) else Color.Black // Highlight selected year
                    )
                }
            }
        }
    }
}


@Composable
fun FilterOptionRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Text(value, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, onFilterClick: () -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = "Фильмы, актеры, режиссеры",
                color = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0xFFF2F2F2)),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.Gray
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Tune,
                contentDescription = "Filter",
                tint = Color.Gray,
                modifier = Modifier.clickable { onFilterClick() } // Navigate to FilterScreen
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun MovieItem(movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Box to hold image and rating
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            // Movie image using Coil
            Image(
                painter = rememberAsyncImagePainter(model = movie.imageUrl),
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize()
            )

            // Rating overlay
            Text(
                text = movie.rating,
                color = Color.Black,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(4.dp, 4.dp)
                    .background(Color.White.copy(alpha = 1f), RoundedCornerShape(4.dp)) // Reduced opacity
                    .padding(4.dp, 2.dp)

            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movie.details,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}


data class Movie(
    val title: String,
    val details: String,
    val rating: String,
    val imageUrl: String
)
