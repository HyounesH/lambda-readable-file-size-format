package com.codewithyaho.lambda.service;

import com.codewithyaho.lambda.model.RequestInput;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class FileSizeFormatService {

    private FileSizeFormatService() {
    }

    public static String convertFileSizeToReadableFormat(final RequestInput requestInput) {
        return switch (requestInput.getType()) {
            case SI -> humanReadableByteCountSI(requestInput.getSize());
            case BINARY -> humanReadableByteCountBinary(requestInput.getSize());
            default -> throw new Error("FileSizeFormatService:: Unsupported file size type :::" + requestInput.getSize());
        };
    }


    public static String humanReadableByteCountSI(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }

    public static String humanReadableByteCountBinary(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }
}
