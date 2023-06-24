package com.wavey.api.user.exceptions

class WaveReactionNotFoundException_k(userId: Long, articleId: Long, reactionId: Long) :
    RuntimeException("Reaction with id $reactionId belonging to article with id $articleId of user with id $userId was not found!")
