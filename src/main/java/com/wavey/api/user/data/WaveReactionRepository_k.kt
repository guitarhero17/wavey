package com.wavey.api.user.data

import org.springframework.data.repository.CrudRepository
import java.util.*

interface WaveReactionRepository_k : CrudRepository<WaveReaction?, Long?> {
    fun findByDate(date: Date?): List<WaveReaction?>?
    fun findByWaveUserId(userId: Long?): List<WaveReaction?>?
    fun findByReactorId(reactorId: Long?): List<WaveReaction?>?
}
