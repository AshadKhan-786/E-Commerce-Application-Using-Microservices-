package in.ashokit.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.Users;

public interface UserRepo extends JpaRepository<Users, Integer>{

	public Optional<Users> findByEmail(String Email);
}
