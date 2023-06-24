package com.wavey.api.user.business

import com.wavey.api.user.data.Wave
import com.wavey.api.user.data.WaveRepository
import com.wavey.api.user.exceptions.WaveNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class WaveService_k {
    @Autowired
    private val waveRepository: WaveRepository? = null
    private fun getWaveOptional(userId: Long, waveId: Long): Optional<Wave> {
        return waveRepository!!.findByUserId(userId).stream()
            .filter { wave: Wave -> waveId == wave.id }.findAny()
    }

    fun getAllWaves(userId: Long?): List<Wave> {
        return ArrayList(waveRepository!!.findByUserId(userId))
    }

    fun getWave(userId: Long, waveId: Long): Wave {
        return getWaveOptional(userId, waveId).orElseThrow { WaveNotFoundException(userId, waveId) }
    }

    fun createWave(wave: Wave): Wave {
        return waveRepository!!.save(wave)
    }

    fun updateWave(userId: Long, waveId: Long, newWave: Wave): Wave {
        return getWaveOptional(userId, waveId).map { wave: Wave ->
            wave.description = newWave.description
            waveRepository!!.save(wave)
        }.orElseGet {
            newWave.id = waveId
            waveRepository!!.save(newWave)
        }
    }

    fun deleteWave(userId: Long, waveId: Long) {
        val wave = getWaveOptional(userId, waveId).orElseThrow { WaveNotFoundException(userId, waveId) }
        waveRepository!!.delete(wave)
    }
}
