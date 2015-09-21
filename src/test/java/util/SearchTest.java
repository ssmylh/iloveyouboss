package util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

// text courtesy of Herman Melville (Moby Dick) from
// http://www.gutenberg.org/cache/epub/2701/pg2701.txt
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;

import org.junit.Test;

public class SearchTest {
    @Test
    public void testSearch() {
        try {
            String pageContent = "There are certain queer times and occasions "
                    + "in this strange mixed affair we call life when a man "
                    + "takes this whole universe for a vast practical joke, "
                    + "though the wit thereof he but dimly discerns, and more "
                    + "than suspects that the joke is at nobody's expense but " + "his own.";
            byte[] bytes = pageContent.getBytes();
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            // search
            Search search = new Search(stream, "practical joke", "1");
            Search.LOGGER.setLevel(Level.OFF);
            search.setSurroundingCharacterCount(10);
            search.execute();
            assertFalse(search.errored());
            List<Match> matches = search.getMatches();
            assertThat(matches, is(notNullValue()));
            assertTrue(matches.size() >= 1);
            Match match = matches.get(0);
            assertThat(match.searchString, equalTo("practical joke"));
            assertThat(match.surroundingContext, equalTo("or a vast practical joke, though t"));
            stream.close();

            // negative
            URLConnection connection = new URL("http://bit.ly/15sYPA7").openConnection();
            InputStream inputStream = connection.getInputStream();
            search = new Search(inputStream, "smelt", "http://bit.ly/15sYPA7");
            search.execute();
            assertThat(search.getMatches().size(), equalTo(0));
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception thrown in test" + e.getMessage());
        }
    }
}
