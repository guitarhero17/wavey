package com.wavey.api.init;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import com.wavey.api.data.Instrument;
import com.wavey.api.data.ReactionType;
import com.wavey.api.data.User;
import com.wavey.api.data.Wave;
import com.wavey.api.data.WaveReaction;

import lombok.Getter;

public class InitialData {

	private static String getDateInISO8601() {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		return df.format(new Date());
	}

	private static final ArrayList<Instrument> exampleSecondaryInstruments1 = new ArrayList<>(Arrays.asList(Instrument.KEYS, Instrument.TRUMPET));
	private static final ArrayList<Instrument> exampleSecondaryInstruments2 = new ArrayList<>(Arrays.asList(Instrument.GUITAR, Instrument.VIOLIN));
	private static final ArrayList<Instrument> exampleSecondaryInstruments3 = new ArrayList<>(Arrays.asList(Instrument.KEYS, Instrument.TRUMPET));
	private static final ArrayList<Instrument> exampleSecondaryInstruments4 = new ArrayList<>(Arrays.asList(Instrument.GUITAR, Instrument.VIOLIN));
	private static final ArrayList<Instrument> exampleSecondaryInstruments5 = new ArrayList<>(Arrays.asList(Instrument.KEYS, Instrument.TRUMPET));
	private static final ArrayList<Instrument> exampleSecondaryInstruments6 = new ArrayList<>(Arrays.asList(Instrument.GUITAR, Instrument.VIOLIN));
	private static final ArrayList<Instrument> exampleSecondaryInstruments7 = new ArrayList<>(Arrays.asList(Instrument.KEYS, Instrument.TRUMPET));
	private static final ArrayList<Instrument> exampleSecondaryInstruments8 = new ArrayList<>(Arrays.asList(Instrument.GUITAR, Instrument.VIOLIN));
	
	private static final ArrayList<Instrument> exampleLookingFor1 = new ArrayList<>(Arrays.asList(Instrument.DRUMS, Instrument.BASS));
	private static final ArrayList<Instrument> exampleLookingFor2 = new ArrayList<>(Arrays.asList(Instrument.TRUMPET, Instrument.SAXOPHONE));
	private static final ArrayList<Instrument> exampleLookingFor3 = new ArrayList<>(Arrays.asList(Instrument.DRUMS, Instrument.BASS));
	private static final ArrayList<Instrument> exampleLookingFor4 = new ArrayList<>(Arrays.asList(Instrument.TRUMPET, Instrument.SAXOPHONE));
	private static final ArrayList<Instrument> exampleLookingFor5 = new ArrayList<>(Arrays.asList(Instrument.DRUMS, Instrument.BASS));
	private static final ArrayList<Instrument> exampleLookingFor6 = new ArrayList<>(Arrays.asList(Instrument.TRUMPET, Instrument.SAXOPHONE));
	private static final ArrayList<Instrument> exampleLookingFor7 = new ArrayList<>(Arrays.asList(Instrument.DRUMS, Instrument.BASS));
	private static final ArrayList<Instrument> exampleLookingFor8 = new ArrayList<>(Arrays.asList(Instrument.TRUMPET, Instrument.SAXOPHONE));
	
	
	
	private static final User johny = new User(
			"johny1",
			"johny1johny1",
			"Johny Aquarian",
			"Berlin",
			Instrument.GUITAR, 
			exampleSecondaryInstruments1, 
			exampleLookingFor1, 
			"johny@johny.com");
	
	private static final User tammy = new User(
			"tammy2",
			"tammy2tammy2",
			"Tammy Fire",
			"Berlin",
			Instrument.DRUMS,
			exampleSecondaryInstruments2,
			exampleLookingFor2,
			"tammy@tammy.com");
	
	private static final User dave = new User(
			"dave3",
			"dave3dave3",
			"Dave Thunder",
			"Berlin",
			Instrument.BASS,
			exampleSecondaryInstruments3,
			exampleLookingFor3,
			"dave@dave.com");
	
	private static final User jessie = new User(
			"jessie4",
			"jessie4jessie4",
			"Jessie Bomb",
			"Berlin",
			Instrument.VOCALS,
			exampleSecondaryInstruments4,
			exampleLookingFor4,
			"jessie@jessie.com");
	
	private static final User monica = new User(
			"monica5",
			"monica5monica5",
			"Monica Wham",
			"Berlin",
			Instrument.KEYS,
			exampleSecondaryInstruments5,
			exampleLookingFor5,
			"monica@monica.com");
	
	private static final User max = new User(
			"max6",
			"max6max6",
			"Max Wind",
			"Berlin",
			Instrument.DRUMS,
			exampleSecondaryInstruments6,
			exampleLookingFor6,
			"max@max.com");
	
	private static final User king = new User(
			"king7",
			"king7king7",
			"King V",
			"Berlin",
			Instrument.KEYS,
			exampleSecondaryInstruments7,
			exampleLookingFor7,
			"king@king.com");
	
	private static final User ceric  = new User(
			"ceric8",
			"ceric8ceric8",
			"Ceric Lepton",
			"Frankfurt",
			Instrument.BASS,
			exampleSecondaryInstruments8,
			exampleLookingFor8,
			"ceric@ceric.com");
	
	private static final User deyonce = new User(
			"deyonce9",
			"deyonce9deyonce9",
			"Deyonce",
			"Frankfurt",
			Instrument.VOCALS,
			exampleSecondaryInstruments1,
			exampleLookingFor1,
			"deyonce@deyonce.com");
	
	// example waves
	private static final Wave johnyWave1 = new Wave("Guitar Dreams", "guitar-dreams.mp3" , johny);
	private static final Wave johnyWave2 = new Wave("Tasty tones", "tasty-tones.mp3", johny);
	private static final Wave tammyWave1 = new Wave("Bam-bam", "bam-bam.mp3", tammy);
	private static final Wave daveWave1 = new Wave("A simple bass groove","a-simple-bass-groove.mp3" , dave);
	
	// example wave reactions
	private static final WaveReaction johnyWave1Reaction1 = new WaveReaction(getDateInISO8601(), ReactionType.CLAP, johnyWave1);
	private static final WaveReaction johnyWave1Reaction2 = new WaveReaction(getDateInISO8601(), ReactionType.LOVE, johnyWave1);
	private static final WaveReaction johnyWave2Reaction1 = new WaveReaction(getDateInISO8601(), ReactionType.LIKE, johnyWave2);
	
	private static final WaveReaction tammyWave1Reaction1 = new WaveReaction(getDateInISO8601(), ReactionType.CLAP, tammyWave1);
	private static final WaveReaction tammyWave1Reaction2 = new WaveReaction(getDateInISO8601(), ReactionType.CLAP, tammyWave1);
	private static final WaveReaction tammyWave1Reaction3 = new WaveReaction(getDateInISO8601(), ReactionType.LOVE, tammyWave1);
	
	private static final WaveReaction daveWave1Reaction1 = new WaveReaction(getDateInISO8601(), ReactionType.LOVE, daveWave1);
	private static final WaveReaction daveWave1Reaction2 = new WaveReaction(getDateInISO8601(), ReactionType.LOVE, daveWave1);
	private static final WaveReaction daveWave1Reaction3 = new WaveReaction(getDateInISO8601(), ReactionType.CLAP, daveWave1);

	
	@Getter
	private static final User[] initUsers = {johny, tammy, dave, jessie, monica, max, king, ceric, deyonce};
	
	@Getter
	private static final Wave[] initWaves = {johnyWave1, johnyWave2, tammyWave1, daveWave1};
	
	@Getter
	private static final WaveReaction[] initReactions = {johnyWave1Reaction1, johnyWave1Reaction2, johnyWave2Reaction1, tammyWave1Reaction1, tammyWave1Reaction2, tammyWave1Reaction3, daveWave1Reaction1, daveWave1Reaction2, daveWave1Reaction3 }; 

}
