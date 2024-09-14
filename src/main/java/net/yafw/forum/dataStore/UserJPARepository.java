/**
 * 
 */
package net.yafw.forum.dataStore;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.yafw.forum.model.User;

/**
 * @author Ashutosh
 *
 */
@Repository()
public interface UserJPARepository extends JpaRepository<User, UUID> {

	/**
	 * Returns a user with given userName, if exists
	 * @param userName username of the User to search
	 * @return {@link User}
	 */
    User findByUserName(String userName);
	
	/**
	 * Returns a user with given userEmailAddress, if exists
	 * @param userEmailAddress email Address of the user to search
	 * @return {@link User}
	 */
    User findByUserEmailAddress(String userEmailAddress);
	
	/**
	 * Returns a user matching with either given userName or given userEmailAddress
	 * @param userName username of the User to search
	 * @param userEmailAddress email Address of the user to search
	 * @return return the User if exists
	 */
    User findByUserNameOrUserEmailAddress(String userName, String userEmailAddress);
}
