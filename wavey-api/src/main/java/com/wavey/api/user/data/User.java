package com.wavey.api.user.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
		
	// A constructor for AuthenticatedUser
    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }
    
    // A constructor for DBInitializer
    public User(int id, String username, String password, String name, String city,
    		Instrument instrumentPrimary, ArrayList<Instrument> instrumentsSecondary,
    		ArrayList<Instrument> lookingFor, String telephoneNumber) {
    	this.id = Long.valueOf(id);
    	this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.name = name;
        this.city = city;
        this.instrumentPrimary = instrumentPrimary;
        this.instrumentsSecondary = instrumentsSecondary;
        this.lookingFor = lookingFor;
        this.telephoneNumber = telephoneNumber;
    }
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="username is required")
	@Column(unique=true)
	private String username;
	
	@NotEmpty(message="password is required")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@NotEmpty(message="name is required")
	private String name;
	
	private String city;
	private String telephoneNumber;
	private Instrument instrumentPrimary;
	private ArrayList<Instrument> instrumentsSecondary = new ArrayList<Instrument>();
	private ArrayList<Instrument> lookingFor = new ArrayList<Instrument>();
	
	/**
	 * <p>{@link https://github.com/spring-projects/spring-hateoas-examples/tree/master/hypermedia}</p>
	 * Not serializing {@literal waves} to break the recursive, bi-directional relationship.
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Wave> waves = new ArrayList<>();
}
