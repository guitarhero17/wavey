package com.wavey.api.user.data;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WaveReaction {
	
	// A constructor for DBInitializer
	public WaveReaction(Date date, ReactionType reaction, Wave wave) {
		this.date = date;
		this.reaction = reaction;
		this.wave = wave;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;

	private Date date;
	private ReactionType reaction;
	
	@JsonIgnore
	@ManyToOne
	private Wave wave;

}
