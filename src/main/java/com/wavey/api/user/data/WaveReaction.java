package com.wavey.api.user.data;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WaveReaction {
	
	// A constructor for DBInitializer
	public WaveReaction(Date date, ReactionType reaction, Wave wave, Long reactorId) {
		this.date = date;
		this.reaction = reaction;
		this.wave = wave;
		this.reactorId = reactorId;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Date date;
	private ReactionType reaction;
	
	private Long reactorId;
	
	@JsonIgnore
	@OneToOne
	private Wave wave;

}
