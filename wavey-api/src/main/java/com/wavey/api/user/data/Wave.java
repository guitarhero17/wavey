package com.wavey.api.user.data;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	 * <p>{@link https://github.com/spring-projects/spring-hateoas-examples/tree/master/hypermedia}</p>
	 * Not serializing {@literal user} to break the recursive, bi-directional relationship.
	 */
	@JsonIgnore
	@OneToOne
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "wave", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<WaveReaction> reactions = new ArrayList<>();
}
