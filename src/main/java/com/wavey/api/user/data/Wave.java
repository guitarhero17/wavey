package com.wavey.api.user.data;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Wave {
	
	// A constructor for DBInitializer
	public Wave(String description, User user) {
		this.description = description;
		this.user = user;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	// mp3 file
	private String description;
	
	/**
	 * <p>{@link <a href="https://github.com/spring-projects/spring-hateoas-examples/tree/master/hypermedia">Hypermedia</a>}</p>
	 * Not serializing {@literal user} to break the recursive, bidirectional relationship.
	 */
	@JsonIgnore
	@OneToOne
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "wave", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<WaveReaction> reactions = new ArrayList<>();
}
