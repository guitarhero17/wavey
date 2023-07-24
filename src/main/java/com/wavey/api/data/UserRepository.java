package com.wavey.api.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	List<User> findByName(String name);
	List<User> findByCity(String city);
	List<User> findByInstrumentPrimary(Instrument instrumentPrimary);
	List<User> findByInstrumentsSecondaryIn(ArrayList<Instrument> instrumentsSecondary);
	List<User> findByLookingForIn(ArrayList<Instrument> lookingFor);
	void deleteByUsername(String username);
}
