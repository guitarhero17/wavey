package com.wavey.api.user.data

import org.springframework.data.repository.CrudRepository

interface WaveRepository_k : CrudRepository<Wave?, Long?> {
    fun findByUserId(userId: Long?): List<Wave?>?
}
