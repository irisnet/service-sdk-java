package iservice.sdk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {

    public static byte[] gzip(byte[] data) throws IOException {
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                GZIPOutputStream gzip = new GZIPOutputStream(bos);
        ) {
            gzip.write(data);
            gzip.finish();
            return bos.toByteArray();
        }
    }

    public static byte[] unGzip(byte[] data) throws Exception {
        try (
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                GZIPInputStream gzip = new GZIPInputStream(bis);
                ByteArrayOutputStream bos = new ByteArrayOutputStream()
        ) {
            byte[] buf = new byte[1024];
            int num;
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                bos.write(buf, 0, num);
            }
            byte[] ret = bos.toByteArray();
            bos.flush();
            return ret;
        }
    }
}