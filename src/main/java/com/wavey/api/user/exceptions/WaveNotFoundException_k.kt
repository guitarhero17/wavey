package com.wavey.api.user.exceptions

class WaveNotFoundException_k(userId: Long, waveId: Long) :
    RuntimeException("The wave with id $waveId belonging to user with id $userId was not found!")
