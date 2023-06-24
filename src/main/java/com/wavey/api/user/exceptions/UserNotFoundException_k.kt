package com.wavey.api.user.exceptions

class UserNotFoundException_k(userId: Long) : RuntimeException("The person with id $userId was not found!")
