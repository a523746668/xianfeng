package com.qingyii.hxtz.base.callback;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import okio.Buffer;

/**
 * Created by Administrator on 2016/7/4.
 * 重写FormEncodingBuilder
 * 可设置请求编码
 */
public final class MyFormEncodingBuilder {
    private static final MediaType CONTENT_TYPE =
            MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8");

    private final Buffer content = new Buffer();

    /** Add new key-value pair. */
    public MyFormEncodingBuilder add(String name, String value) {
        if (content.size() > 0) {
            content.writeByte('&');
        }
        HttpUrl.canonicalize(content, name, 0, name.length(),
                HttpUrl.FORM_ENCODE_SET, false, true, true);
        content.writeByte('=');
        HttpUrl.canonicalize(content, value, 0, value.length(),
                HttpUrl.FORM_ENCODE_SET, false, true, true);
        return this;
    }

    /** Add new key-value pair. */
    public MyFormEncodingBuilder addEncoded(String name, String value) {
        if (content.size() > 0) {
            content.writeByte('&');
        }
        HttpUrl.canonicalize(content, name, 0, name.length(),
                HttpUrl.FORM_ENCODE_SET, true, true, true);
        content.writeByte('=');
        HttpUrl.canonicalize(content, value, 0, value.length(),
                HttpUrl.FORM_ENCODE_SET, true, true, true);
        return this;
    }

    public RequestBody build() {
        return RequestBody.create(CONTENT_TYPE, content.snapshot());
    }
    private static class HttpUrl{
        private static final char[] HEX_DIGITS =
                { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        static final String USERNAME_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
        static final String PASSWORD_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#";
        static final String PATH_SEGMENT_ENCODE_SET = " \"<>^`{}|/\\?#";
        static final String PATH_SEGMENT_ENCODE_SET_URI = "[]";
        static final String QUERY_ENCODE_SET = " \"'<>#";
        static final String QUERY_COMPONENT_ENCODE_SET = " \"'<>#&=";
        static final String QUERY_COMPONENT_ENCODE_SET_URI = "\\^`{|}";
        static final String FORM_ENCODE_SET = " \"':;<=>@[]^`{}|/\\?#&!$(),~";
        static final String FRAGMENT_ENCODE_SET = "";
        static final String FRAGMENT_ENCODE_SET_URI = " \"#<>\\^`{|}";
        static void canonicalize(Buffer out, String input, int pos, int limit,
                                 String encodeSet, boolean alreadyEncoded, boolean plusIsSpace, boolean asciiOnly) {
            Buffer utf8Buffer = null; // Lazily allocated.
            int codePoint;
            for (int i = pos; i < limit; i += Character.charCount(codePoint)) {
                codePoint = input.codePointAt(i);
                if (alreadyEncoded
                        && (codePoint == '\t' || codePoint == '\n' || codePoint == '\f' || codePoint == '\r')) {
                    // Skip this character.
                } else if (codePoint == '+' && plusIsSpace) {
                    // Encode '+' as '%2B' since we permit ' ' to be encoded as either '+' or '%20'.
                    out.writeUtf8(alreadyEncoded ? "+" : "%2B");
                } else if (codePoint < 0x20
                        || codePoint == 0x7f
                        || (codePoint >= 0x80 && asciiOnly)
                        || encodeSet.indexOf(codePoint) != -1
                        || (codePoint == '%' && !alreadyEncoded)) {
                    // Percent encode this character.
                    if (utf8Buffer == null) {
                        utf8Buffer = new Buffer();
                    }
                    utf8Buffer.writeUtf8CodePoint(codePoint);
                    while (!utf8Buffer.exhausted()) {
                        int b = utf8Buffer.readByte() & 0xff;
                        out.writeByte('%');
                        out.writeByte(HEX_DIGITS[(b >> 4) & 0xf]);
                        out.writeByte(HEX_DIGITS[b & 0xf]);
                    }
                } else {
                    // This character doesn't need encoding. Just copy it over.
                    out.writeUtf8CodePoint(codePoint);
                }
            }
        }
    }
}
