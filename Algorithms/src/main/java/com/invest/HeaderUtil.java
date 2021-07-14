package com.invest;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author bo6.zhang
 * @date 2021/7/14
 */
public class HeaderUtil {

    public static Map<String, String> userAgent() {
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        return header;
    }

    public static Map<String, String> headers(Header[] headers) {
        Map<String, String> header = userAgent();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        putHeader(header, filter(headers, "Host"));
        putHeader(header, filter(headers, "Cookie"));
        return header;
    }

    private static void putHeader(Map<String, String> headers, Header header) {
        headers.put(header.getName(), header.getValue());
    }

    private static Header filter(Header[] headers, String key) {
        return Stream.of(headers).filter(header -> header.getName().equalsIgnoreCase(key)).findFirst().orElseGet(() -> new BasicHeader(key, ""));
    }

}
