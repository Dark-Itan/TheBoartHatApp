package com.boarhat.domain.usecases.pastel

import com.boarhat.domain.repositories.PastelRepository

class DeletePastelUseCase(
    private val repository: PastelRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return if (id > 0) repository.deletePastel(id) else false
    }
}