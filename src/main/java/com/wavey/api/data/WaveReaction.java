package com.wavey.api.data;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WaveReaction {
	
	// A constructor for DBInitializer
	public WaveReaction(String date, ReactionType reaction, Wave wave) {
		this.date = date;
		this.reaction = reaction;
		this.wave = wave;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;

	// Date in ISO 8601
//	@Pattern(regexp = " /^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$/")
	private String date;
	private ReactionType reaction;
	
	@JsonIgnore
	@ManyToOne
	private Wave wave;

}
