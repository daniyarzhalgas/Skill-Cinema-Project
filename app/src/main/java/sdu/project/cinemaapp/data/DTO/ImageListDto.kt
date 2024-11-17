package sdu.project.cinemaapp.data.DTO

import sdu.project.cinemaapp.domain.model.MovieImage
import sdu.project.cinemaapp.domain.model.ImageList

class ImageListDto(
    override val total: Int,
    override val totalPages: Int,
    override val items: List<MovieImage>
):ImageList