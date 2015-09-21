package util;

import java.io.IOException;

public interface Http {
    String get(String url) throws IOException;
}
