package com.wavey.api.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name = "WAVEY_USER")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
		
	// A constructor for AuthenticatedUser
    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }
    
    // A constructor for DBInitializer
    public User(String username, String password, String name, String city,
    		Instrument instrumentPrimary, ArrayList<Instrument> instrumentsSecondary,
    		ArrayList<Instrument> lookingFor, String telephoneNumber) {
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
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@NotEmpty(message="Username is required")
	@Column(unique=true)
	@Size(min = 4, message = "The username should be at least 4 characters")
	@Pattern(regexp = "^[a-z]+\\d*$", message = "The username needs to consist only of non-capital letters and numbers")
	private String username;
	
	@NotEmpty(message="Password is required")
	@Size(min = 6, message = "Password should be at least 6 characters")
//	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "The password should consist of minimum 6 characters, at least one letter and one number")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@NotEmpty(message="Name is required")
	@Size(min = 2, message = "The name of the user should be at least 2 characters")
	@Pattern(regexp = "[a-zA-Z0-9_.,#-]+(\\s[a-zA-Z0-9_.,#-]+)*", message = "The name of the user can only contain letters, numbers, whitespaces and the characters \"_\" \".\" \",\" \"#\" and \"-\". The name can not begin or end on a whitespace.")
	private String name;
	
	private String city;
	private String telephoneNumber;
	private Instrument instrumentPrimary;
	private ArrayList<Instrument> instrumentsSecondary = new ArrayList<Instrument>();
	private ArrayList<Instrument> lookingFor = new ArrayList<Instrument>();
	
	/**
	 * <p>{@link <a href="https://github.com/spring-projects/spring-hateoas-examples/tree/master/hypermedia">Hypermedia</a>}</p>
	 * Not serializing {@literal waves} to break the recursive, bi-directional relationship.
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Wave> waves = new ArrayList<>();

	@JsonIgnore
	public void addWave(Wave wave) {
		waves.add(wave);
	}
}
