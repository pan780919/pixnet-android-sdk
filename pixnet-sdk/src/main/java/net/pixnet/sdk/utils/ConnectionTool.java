package net.pixnet.sdk.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface ConnectionTool {

    String performRequest(Request request) throws IOException;
}
