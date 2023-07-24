package com.wavey.api.data;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Wave {
	
	// A constructor for DBInitializer
	public Wave(String title, User user) {
		this.title = title;
		this.user = user;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;

	@NotEmpty
	@Size(min = 2, message = "The wave title should be at least 2 characters long")
	@Pattern(regexp = "[a-zA-Z0-9_.,#-]+(\\s[a-zA-Z0-9_.,#-]+)*", message = "The title can only contain letters, numbers, whitespaces and the characters \"_\" \".\" \",\" \"#\" and \"-\". The title can not begin or end on a whitespace.")
	private String title;
	
	/**
	 * <p>{@link <a href="https://github.com/spring-projects/spring-hateoas-examples/tree/master/hypermedia">Hypermedia</a>}</p>
	 * Not serializing {@literal user} to break the recursive, bi-directional relationship.
	 */
	@JsonIgnore
	@ManyToOne
	private User user;

	private String waveURL;
	
	@JsonIgnore
	@OneToMany(mappedBy = "wave", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<WaveReaction> reactions = new ArrayList<>();

	@JsonIgnore
	public void addReaction(WaveReaction reaction) {
		reactions.add(reaction);
	}
}
