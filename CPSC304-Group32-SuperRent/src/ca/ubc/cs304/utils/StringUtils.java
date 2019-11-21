package ca.ubc.cs304.utils;

import java.sql.Timestamp;

public class StringUtils {
    public static String formatTimeStamp(Timestamp timestamp) {
        StringBuffer formatted = new StringBuffer(timestamp.toString());
        int len = formatted.length();
        formatted.delete(len-2,len);
        return formatted.toString();
    }
}
