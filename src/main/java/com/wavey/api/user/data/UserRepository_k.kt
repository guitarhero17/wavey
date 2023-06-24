package com.wavey.api.user.data

import org.springframework.data.repository.CrudRepository

interface UserRepository_k : CrudRepository<User?, Long?> {
    fun findByUsername(username: String?): User?
    fun findByName(name: String?): List<User?>?
    fun findByCity(city: String?): List<User?>?
    fun findByInstrumentPrimary(instrumentPrimary: Instrument?): List<User?>?
    fun findByInstrumentsSecondaryIn(instrumentsSecondary: ArrayList<Instrument?>?): List<User?>?
    fun findByLookingForIn(lookingFor: ArrayList<Instrument?>?): List<User?>?
}
