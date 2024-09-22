package net.yafw.forum.utils;

import net.yafw.forum.dataStore.PostJPARepository;
import net.yafw.forum.dataStore.UserJPARepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ForumAppBaseTest {

    protected AutoCloseable closeable;
    @BeforeEach
    public void setUp() throws Exception{
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception{
        closeable.close();
    }
}
