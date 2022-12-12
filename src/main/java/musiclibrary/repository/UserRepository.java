package musiclibrary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import musiclibrary.beans.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	//these functions are auto created by the repo based on the given name
	
	List<User> findByUsername(String username);
	
	List<User> findByUsernameAndPassword(String username, String password);
	
	

}
