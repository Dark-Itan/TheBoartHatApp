package com.boarhat.domain.usecases.pastel

import com.boarhat.features.admin.domain.repositories.PastelRepository
import javax.inject.Inject // IMPORTANTE

class DeletePastelUseCase @Inject constructor( // <-- Añade @Inject constructor
    private val repository: PastelRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return try {
            if (id > 0) repository.deletePastel(id) else false
        } catch (e: Exception) {
            false // Si Room falla, devolvemos false en lugar de cerrar la app
        }
    }
}