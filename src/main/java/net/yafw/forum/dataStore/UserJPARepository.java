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
	 * @param userName
	 * @return {@link User}
	 */
	public User findByUserName(String userName);
	
	/**
	 * Returns a user with given userEmailAddress, if exists
	 * @param userEmailAddress
	 * @return {@link User}
	 */
	public User findByUserEmailAddress(String userEmailAddress);
	
	/**
	 * Returns a user matching with either given userName or given userEmailAddress
	 * @param userName
	 * @param userEmailAddress
	 * @return
	 */
	public User findByUserNameOrUserEmailAddress(String userName, String userEmailAddress);
}
