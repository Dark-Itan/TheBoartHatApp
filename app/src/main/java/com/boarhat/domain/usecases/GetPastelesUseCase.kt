package com.boarhat.domain.usecases.pastel

import com.boarhat.domain.entities.Pastel
import com.boarhat.domain.repositories.PastelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPastelesUseCase @Inject constructor(
    private val repository: PastelRepository
) {
    operator fun invoke(): Flow<List<Pastel>> = repository.getPasteles()
}