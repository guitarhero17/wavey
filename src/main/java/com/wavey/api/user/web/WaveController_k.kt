package com.wavey.api.user.web

import com.wavey.api.user.business.WaveService
import com.wavey.api.user.data.User
import com.wavey.api.user.data.Wave
import com.wavey.api.user.web.hateoas.WaveModelAssembler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

/**
 * Created in compliance with the "Building REST services" guide
 *
 * [][<a href=]//spring.io/guides/tutorials/rest/">Rest tutorials">&lt;a href=&quot;https://spring.io/guides/tutorials/rest/&quot;&gt;Rest tutorials&lt;/a&gt;
 * @author guitarhero17
 */
@RestController
@CrossOrigin
class WaveController_k {
    @Autowired
    private val waveService: WaveService? = null

    @Autowired
    private val assembler: WaveModelAssembler? = null
    @GetMapping("/users/{userId}/waves")
    fun getAllWaves(@PathVariable userId: Long?): CollectionModel<EntityModel<Wave>> {
        val waveModels = waveService!!.getAllWaves(userId)
            .map { wave: Wave? -> assembler!!.toModel(wave) }
        return CollectionModel.of(
            waveModels, WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                    WaveController_k::class.java
                ).getAllWaves(userId)
            ).withSelfRel()
        )
    }

    @GetMapping("/users/{userId}/waves/{waveId}")
    fun getWave(@PathVariable userId: Long?, @PathVariable waveId: Long?): EntityModel<Wave> {
        return assembler!!.toModel(waveService!!.getWave(userId, waveId))
    }

    @PostMapping("/users/{userId}/waves")
    fun createWave(@RequestBody wave: Wave, @PathVariable userId: Long?): ResponseEntity<*> {

//		wave.setUser(new User(userId, null, null, null, null, null, null));
        val user = User()
        user.id = userId
        wave.user = user
        val entityModel = assembler!!.toModel(waveService!!.createWave(wave))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    @PutMapping("/users/{userId}/waves/{waveId}")
    fun updateWave(
        @RequestBody wave: Wave,
        @PathVariable userId: Long?,
        @PathVariable waveId: Long?
    ): ResponseEntity<*> {
//		wave.setUser(new User(userId, null, null, null, null, null, null));
        val user = User()
        user.id = userId
        wave.user = user
        val entityModel = assembler!!.toModel(waveService!!.updateWave(userId, waveId, wave))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel)
    }

    @DeleteMapping("/users/{userId}/waves/{waveId}")
    fun deleteWave(@PathVariable userId: Long?, @PathVariable waveId: Long?): ResponseEntity<*> {
        waveService!!.deleteWave(userId, waveId)
        return ResponseEntity.noContent().build<Any>()
    }
}
