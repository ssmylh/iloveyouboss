package util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static util.ContainsMatches.*;

// text courtesy of Herman Melville (Moby Dick) from
// http://www.gutenberg.org/cache/epub/2701/pg2701.txt
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SearchTest {
    private static final String ANY_TITLE = "1";
    private InputStream stream;

    @Before
    public void turnOffLogging() {
        Search.LOGGER.setLevel(Level.OFF);
    }

    @After
    public void closeResources() throws Exception {
        stream.close();
    }

    @Test
    public void returnsMatchesShowingContextWhenSearchStringInContent() throws Exception {
        stream = streamOn("rest of text here"
                + "1234567890search term1234567890"
                + "more rest of text");
        Search search = new Search(stream, "search term", ANY_TITLE);
        search.setSurroundingCharacterCount(10);

        search.execute();

        assertThat(search.getMatches(), is(containsMatches(
                new Match[] { new Match(ANY_TITLE, "search term", "1234567890search term1234567890") })));
    }

    private InputStream streamOn(String pageContent) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(pageContent.getBytes("UTF-8"));
    }

    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() throws Exception {
        stream = streamOn("any text");
        Search search = new Search(stream, "text that does not match", ANY_TITLE);

        search.execute();

        assertTrue(search.getMatches().isEmpty());
    }

    @Test
    public void returnsErroredWhenUnableToReadStream() {
        stream = createStreamThrowingErrorWhenRead();
        Search search = new Search(stream, "", "");

        search.execute();

        assertTrue(search.errored());
    }

    @Test
    public void erroredReturnsFalseWhenReadSucceeds() throws Exception {
        stream = streamOn("");
        Search search = new Search(stream, "", "");

        search.execute();

        assertFalse(search.errored());
    }

    private InputStream createStreamThrowingErrorWhenRead() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
    }
}
