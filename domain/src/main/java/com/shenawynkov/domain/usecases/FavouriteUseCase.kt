package com.shenawynkov.domain.usecases

import com.shenawynkov.domain.repositories.SectionsRepo
import javax.inject.Inject


class FavouriteUseCase @Inject constructor(private val repo: SectionsRepo) {
    suspend operator fun invoke(fav: Boolean, id: String) = repo.updateFav(fav, id)
    suspend fun getFavSections() = repo.getFavSections()
}