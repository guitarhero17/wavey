package com.wavey.api.user.business

import com.wavey.api.user.data.User
import com.wavey.api.user.data.Wave
import com.wavey.api.user.data.WaveReaction
import com.wavey.api.user.data.WaveReactionRepository
import com.wavey.api.user.exceptions.WaveReactionNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class WaveReactionService_k {
    @Autowired
    private val reactionRepository: WaveReactionRepository? = null
    private fun getWaveReactionOptional(userId: Long, articleId: Long, reactionId: Long): Optional<WaveReaction> {
        return reactionRepository!!.findByWaveUserId(userId).stream()
            .filter { reaction: WaveReaction -> articleId == reaction.wave.id }
            .filter { reaction: WaveReaction -> reactionId == reaction.id }.findAny()
    }

    fun getAllWaveReactions(userId: Long?, articleId: Long): List<WaveReaction> {
        return reactionRepository!!.findByWaveUserId(userId)
            .filter { reaction: WaveReaction -> articleId == reaction.wave.id }
    }

    fun getWaveReaction(userId: Long, articleId: Long, reactionId: Long): WaveReaction {
        return getWaveReactionOptional(
            userId,
            articleId,
            reactionId
        ).orElseThrow { WaveReactionNotFoundException(userId, articleId, reactionId) }
    }

    fun createWaveReaction(reaction: WaveReaction): WaveReaction {
        return reactionRepository!!.save(reaction)
    }

    fun updateWaveReaction(userId: Long, articleId: Long, reactionId: Long, newReaction: WaveReaction): WaveReaction {
//		reactionRepository.save(reaction);
        return getWaveReactionOptional(userId, articleId, reactionId).map { reaction: WaveReaction ->
            reaction.date = newReaction.date
            reaction.reaction = newReaction.reaction
            reaction.reactorId = newReaction.reactorId
            reactionRepository!!.save(reaction)
        }.orElseGet {
            val user = User()
            user.id = userId
            val article = Wave()
            article.id = articleId
            article.user = user
            newReaction.id = reactionId
            newReaction.wave = article
            reactionRepository!!.save(newReaction)
        }
    }

    fun deleteWaveReaction(userId: Long, articleId: Long, reactionId: Long) {
//		reactionRepository.deleteById(reactionId);
        val reaction = getWaveReactionOptional(userId, articleId, reactionId).orElseThrow {
            WaveReactionNotFoundException(
                userId,
                articleId,
                reactionId
            )
        }
        reactionRepository!!.delete(reaction)
    }
}
