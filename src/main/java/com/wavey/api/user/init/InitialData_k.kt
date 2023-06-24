package com.wavey.api.user.init

import com.wavey.api.user.data.*
import lombok.Getter
import java.util.*

object InitialData_k {
    private val exampleSecondaryInstruments1 = ArrayList(listOf(Instrument.KEYS, Instrument.TRUMPET))
    private val exampleSecondaryInstruments2 = ArrayList(listOf(Instrument.GUITAR, Instrument.VIOLIN))
    private val exampleSecondaryInstruments3 = ArrayList(listOf(Instrument.KEYS, Instrument.TRUMPET))
    private val exampleSecondaryInstruments4 = ArrayList(listOf(Instrument.GUITAR, Instrument.VIOLIN))
    private val exampleSecondaryInstruments5 = ArrayList(listOf(Instrument.KEYS, Instrument.TRUMPET))
    private val exampleSecondaryInstruments6 = ArrayList(listOf(Instrument.GUITAR, Instrument.VIOLIN))
    private val exampleSecondaryInstruments7 = ArrayList(listOf(Instrument.KEYS, Instrument.TRUMPET))
    private val exampleSecondaryInstruments8 = ArrayList(listOf(Instrument.GUITAR, Instrument.VIOLIN))
    private val exampleLookingFor1 = ArrayList(listOf(Instrument.DRUMS, Instrument.BASS))
    private val exampleLookingFor2 = ArrayList(listOf(Instrument.TRUMPET, Instrument.SAXOPHONE))
    private val exampleLookingFor3 = ArrayList(listOf(Instrument.DRUMS, Instrument.BASS))
    private val exampleLookingFor4 = ArrayList(listOf(Instrument.TRUMPET, Instrument.SAXOPHONE))
    private val exampleLookingFor5 = ArrayList(listOf(Instrument.DRUMS, Instrument.BASS))
    private val exampleLookingFor6 = ArrayList(listOf(Instrument.TRUMPET, Instrument.SAXOPHONE))
    private val exampleLookingFor7 = ArrayList(listOf(Instrument.DRUMS, Instrument.BASS))
    private val exampleLookingFor8 = ArrayList(listOf(Instrument.TRUMPET, Instrument.SAXOPHONE))

    private val johny = User(
        1,
        "johny1",
        "johny1",
        "Johny Aquarian",
        "Berlin",
        Instrument.GUITAR,
        exampleSecondaryInstruments1,
        exampleLookingFor1,
        "+4921414124"
    )
    private val tammy = User(
        2,
        "tammy2",
        "tammy2",
        "Tammy Fire",
        "Berlin",
        Instrument.DRUMS,
        exampleSecondaryInstruments2,
        exampleLookingFor2,
        "+4921414124"
    )
    private val dave = User(
        3,
        "dave3",
        "dave3",
        "Dave Thunder",
        "Berlin",
        Instrument.BASS,
        exampleSecondaryInstruments3,
        exampleLookingFor3,
        "+4921414124"
    )
    private val jessie = User(
        4,
        "jessie4",
        "jessie4",
        "Jessie Bomb",
        "Berlin",
        Instrument.VOCALS,
        exampleSecondaryInstruments4,
        exampleLookingFor4,
        "+4921414124"
    )
    private val monica = User(
        5,
        "monica5",
        "monica5",
        "Monica Wham",
        "Berlin",
        Instrument.KEYS,
        exampleSecondaryInstruments5,
        exampleLookingFor5,
        "+4921414124"
    )
    private val max = User(
        6,
        "max6",
        "max6",
        "Max Wind",
        "Berlin",
        Instrument.DRUMS,
        exampleSecondaryInstruments6,
        exampleLookingFor6,
        "+4921414124"
    )
    private val king = User(
        7,
        "king7",
        "king7",
        "King V",
        "Berlin",
        Instrument.KEYS,
        exampleSecondaryInstruments7,
        exampleLookingFor7,
        "+4921414124"
    )
    private val ceric = User(
        8,
        "ceric8",
        "ceric8",
        "Ceric Lepton",
        "Frankfurt",
        Instrument.BASS,
        exampleSecondaryInstruments8,
        exampleLookingFor8,
        "+4921414124"
    )
    private val deyonce = User(
        9,
        "deyonce9",
        "deyonce9",
        "Deyonce",
        "Frankfurt",
        Instrument.VOCALS,
        exampleSecondaryInstruments1,
        exampleLookingFor1,
        "+4921414124"
    )

    // example waves
    private val johnyWave1 = Wave("Guitar Dreams", johny)
    private val johnyWave2 = Wave("Tasty tones", johny)
    private val tammyWave1 = Wave("Bam-bam", tammy)
    private val daveWave1 = Wave("A simple bass groove", dave)

    // example wave reactions
    private val johnyWave1Reaction1 = WaveReaction(Date(), ReactionType.CLAP, johnyWave1, tammy.id)
    private val johnyWave1Reaction2 = WaveReaction(Date(), ReactionType.LOVE, johnyWave1, dave.id)
    private val johnyWave2Reaction1 = WaveReaction(Date(), ReactionType.LIKE, johnyWave2, king.id)
    private val tammyWave1Reaction1 = WaveReaction(Date(), ReactionType.CLAP, tammyWave1, johny.id)
    private val tammyWave1Reaction2 = WaveReaction(Date(), ReactionType.CLAP, tammyWave1, deyonce.id)
    private val tammyWave1Reaction3 = WaveReaction(Date(), ReactionType.LOVE, tammyWave1, max.id)
    private val daveWave1Reaction1 = WaveReaction(Date(), ReactionType.LOVE, daveWave1, monica.id)
    private val daveWave1Reaction2 = WaveReaction(Date(), ReactionType.LOVE, daveWave1, ceric.id)
    private val daveWave1Reaction3 = WaveReaction(Date(), ReactionType.CLAP, daveWave1, tammy.id)

    val initUsers = arrayOf(johny, tammy, dave, jessie, monica, max, king, ceric, deyonce)

    val initWaves = arrayOf(johnyWave1, johnyWave2, tammyWave1, daveWave1)

    val initReactions = arrayOf(
        johnyWave1Reaction1,
        johnyWave1Reaction2,
        johnyWave2Reaction1,
        tammyWave1Reaction1,
        tammyWave1Reaction2,
        tammyWave1Reaction3,
        daveWave1Reaction1,
        daveWave1Reaction2,
        daveWave1Reaction3
    )
}
