package util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static util.ContainsMatches.*;

// text courtesy of Herman Melville (Moby Dick) from
// http://www.gutenberg.org/cache/epub/2701/pg2701.txt
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import org.junit.Test;

public class SearchTest {
    private static final String ANY_TITLE = "1";
    @Test
    public void returnsMatchesShowingContextWhenSearchStringInContent() throws Exception {
        InputStream stream = streamOn("There are certain queer times and occasions "
                + "in this strange mixed affair we call life when a man "
                + "takes this whole universe for a vast practical joke, "
                + "though the wit thereof he but dimly discerns, and more "
                + "than suspects that the joke is at nobody's expense but " + "his own.");

        Search search = new Search(stream, "practical joke", ANY_TITLE);
        Search.LOGGER.setLevel(Level.OFF);
        search.setSurroundingCharacterCount(10);
        search.execute();
        assertFalse(search.errored());
        assertThat(search.getMatches(), is(containsMatches(
                new Match[] { new Match(ANY_TITLE, "practical joke", "or a vast practical joke, though t") })));
        stream.close();
    }

    private InputStream streamOn(String pageContent) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(pageContent.getBytes("UTF-8"));
    }

    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() throws Exception {
        URLConnection connection = new URL("http://bit.ly/15sYPA7").openConnection();
        InputStream stream = connection.getInputStream();
        Search search = new Search(stream, "smelt", ANY_TITLE);
        search.execute();
        assertTrue(search.getMatches().isEmpty());
        stream.close();
    }
}
