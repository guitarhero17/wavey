package com.wavey.api.user.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long>{
	public User findByUsername(String username);
	public List<User> findByName(String name);
	public List<User> findByCity(String city);
	public List<User> findByInstrumentPrimary(Instrument instrumentPrimary);
	public List<User> findByInstrumentsSecondaryIn(ArrayList<Instrument> instrumentsSecondary);
	public List<User> findByLookingForIn(ArrayList<Instrument> lookingFor);
}
