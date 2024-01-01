/**
 * 
 */
package net.yafw.forum.dataStore;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.yafw.forum.model.Post;

/**
 * @author Ashutosh
 *
 */
@Repository()
public interface PostJPARepository extends JpaRepository<Post, UUID> {

}
