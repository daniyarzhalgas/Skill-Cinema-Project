package sdu.project.cinemaapp.data.DTO

import sdu.project.cinemaapp.domain.model.Image
import sdu.project.cinemaapp.domain.model.ImageList

class ImageListDto(
    override val total: Int,
    override val totalPages: Int,
    override val items: List<Image>
):ImageList