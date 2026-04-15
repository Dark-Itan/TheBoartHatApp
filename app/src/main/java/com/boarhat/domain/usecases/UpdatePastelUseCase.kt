package com.boarhat.domain.usecases.pastel

import com.boarhat.domain.entities.Pastel
import com.boarhat.domain.repositories.PastelRepository
import javax.inject.Inject

class UpdatePastelUseCase @Inject constructor(
    private val repository: PastelRepository
) {
    suspend operator fun invoke(pastel: Pastel): Boolean {
        return if (pastel.id > 0 && validarPastel(pastel)) {
            repository.updatePastel(pastel)
        } else false
    }

    private fun validarPastel(pastel: Pastel): Boolean {
        return pastel.nombre.isNotBlank() &&
                pastel.precio > 0 &&
                pastel.stock >= 0
    }
}