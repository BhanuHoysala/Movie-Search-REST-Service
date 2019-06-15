package consulting.forster.mss.service;

import consulting.forster.mss.utils.MovieLabels;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MovieSearchServiceImplTest {

    @Autowired
    private MovieSearchService movieSearchService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Positive Scenario 1
     */
    @Test
    public void findMoviesByLabel() {

        Assert.assertNotNull(movieSearchService.findMoviesByLabel(MovieLabels.valueOf("FSK12")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findMoviesByLabelNegative() {

        movieSearchService.findMoviesByLabel(MovieLabels.valueOf("FSK10"));
    }
}