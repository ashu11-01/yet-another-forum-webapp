package net.yafw.forum.repository;

import net.yafw.forum.dataStore.UserJPARepository;
import net.yafw.forum.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    UserJPARepository userJPARepository;

    User testUser;

    @BeforeEach
    public void setUp(){
        testUser = new User(UUID.randomUUID(),
                "Test","Name",
                12,"userId001",
                "abc@xyz.com",new ArrayList<>(),"pswd11");

    }

    @AfterEach
    public void tearDown(){
        testUser = null;
    }

    @Test
    @DisplayName("should return one user with given user name")
    public void testFindByUserName(){
        userJPARepository.save(testUser);
        User actualUser = userJPARepository.findByUserName("userId001");
        assertNotNull(actualUser);
        assertEquals("Test",actualUser.getFirstName());
    }

    @Test
    @DisplayName("should return null if no user present with given user name")
    public void testFindByUserName01(){
        userJPARepository.save(testUser);
        User actualUser = userJPARepository.findByUserName("userId002");
        assertNull(actualUser);
    }

    @Test
    @DisplayName("should return one user with given user emailAddress")
    public void testFindByUserEmailAddress(){
        userJPARepository.save(testUser);
        User actualUser = userJPARepository.findByUserEmailAddress("abc@xyz.com");
        assertNotNull(actualUser);
        assertEquals("Test",actualUser.getFirstName());
    }

    @Test
    @DisplayName("should return null if no user present with given user emailAddress")
    public void testFindByUserEmailAddress01(){
        userJPARepository.save(testUser);
        User actualUser = userJPARepository.findByUserEmailAddress("test@xyz.com");
        assertNull(actualUser);
    }
}
