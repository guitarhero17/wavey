package com.wavey.api.user.web

import com.wavey.api.user.business.WaveReactionService
import com.wavey.api.user.data.User
import com.wavey.api.user.data.Wave
import com.wavey.api.user.data.WaveReaction
import com.wavey.api.user.web.hateoas.WaveReactionModelAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@CrossOrigin
class WaveReactionController_k {
    @Autowired
    private val reactionService: WaveReactionService? = null

    @Autowired
    private val assembler: WaveReactionModelAssembler? = null
    @GetMapping("/users/{userId}/waves/{waveId}/reactions")
    @CrossOrigin(allowedHeaders = ["*"])
    fun getAllWaveReactions(
        @PathVariable userId: Long?,
        @PathVariable waveId: Long?
    ): CollectionModel<EntityModel<WaveReaction>> {
        val reactionModels = reactionService!!.getAllWaveReactions(userId, waveId)
            .map { reaction: WaveReaction? -> assembler!!.toModel(reaction) }
        return CollectionModel.of(
            reactionModels, WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                    WaveReactionController_k::class.java
                ).getAllWaveReactions(userId, waveId)
            ).withSelfRel()
        )
    }

    @GetMapping("/users/{userId}/waves/{waveId}/reactions/{reactionId}")
    @CrossOrigin(allowedHeaders = ["*"])
    fun getWaveReaction(
        @PathVariable userId: Long?,
        @PathVariable waveId: Long?,
        @PathVariable reactionId: Long?
    ): EntityModel<WaveReaction> {
        return assembler!!.toModel(reactionService!!.getWaveReaction(userId, waveId, reactionId))
    }

    @PostMapping("/users/{userId}/waves/{waveId}/reactions")
    @CrossOrigin(allowedHeaders = ["*"])
    fun createWaveReaction(
        @RequestBody reaction: WaveReaction,
        @PathVariable userId: Long?,
        @PathVariable waveId: Long?
    ): ResponseEntity<*> {
        val user = User()
        user.id = userId
        val wave = Wave()
        wave.id = waveId
        wave.user = user
        reaction.wave = wave
        val entityModel = assembler!!.toModel(reactionService!!.createWaveReaction(reaction))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    @PutMapping("/users/{userId}/waves/{waveId}/reactions/{reacionId}")
    @CrossOrigin(allowedHeaders = ["*"])
    fun updateWaveReaction(
        @RequestBody reaction: WaveReaction,
        @PathVariable userId: Long?,
        @PathVariable waveId: Long?,
        @PathVariable reactionId: Long?
    ): ResponseEntity<*> {
//		reaction.setWave(new Wave(waveId, null, null, null, null, null));
        val user = User()
        user.id = userId
        val wave = Wave()
        wave.id = waveId
        wave.user = user
        reaction.wave = wave
        val entityModel =
            assembler!!.toModel(reactionService!!.updateWaveReaction(userId, waveId, reactionId, reaction))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    @DeleteMapping("/users/{userId}/waves/{waveId}/reactions/{reacionId}")
    @CrossOrigin(allowedHeaders = ["*"])
    fun deleteWave(
        @PathVariable userId: Long?,
        @PathVariable waveId: Long?,
        @PathVariable reactionId: Long?
    ): ResponseEntity<*> {
//		reactionService.deleteWaveReaction(id);
        reactionService!!.deleteWaveReaction(userId, waveId, reactionId)
        return ResponseEntity.noContent().build<Any>()
    }
}
