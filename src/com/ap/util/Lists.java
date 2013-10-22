package com.ap.util;

import java.util.List;
import java.util.ArrayList;

public class Lists {

	public static List toList(Object[] array) {
		
		List list = new ArrayList(array.length);
		
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);						
		}
		
		return list;
	}

    public static String[] split(String s) {

        List l = csvSplit(s.toCharArray(), 0, s.length(), false);

        return (String[]) l.toArray(new String[l.size()]);
    }

    public static List csvSplit(String s) {
        return csvSplit(s.toCharArray(), 0, s.length(), false);
    }

    public static List csvSplit(String s, boolean emptyAsNull) {
        return csvSplit(s.toCharArray(), 0, s.length(), emptyAsNull);
    }

    public static List csvSplit(char[] ch, int start, int length) {
        return csvSplit(ch, start, length, false);
    }

    public static List csvSplit(char[] ch, int start, int length, boolean emptyAsNull) {

        ArrayList result = new ArrayList();

        int from = start;
        int n = from + length;
        boolean escaped = false;
        boolean waitingClose = false;
        boolean waitingSeparator = false;

        for (int i = start; i < n; i++) {

			if (ch[i] == '\\') {

				escaped = !escaped;

				continue;
			}

			if (!escaped && ch[i] == '"' && i + 1 < n && ch[i+1] == '"') {

				escaped = true;

				continue;
			}

            if (waitingClose) {
                if (!escaped && ch[i] == '"') {
                    add(result, create(ch, from, i - from), false);

                    from = i + 1;
                    waitingClose = false;
                    waitingSeparator = true;
                }
            } else  if (!escaped && ch[i] == '"') {
                from = i + 1;
                waitingClose = true;
            } else if (ch[i] == ',') {

                if (!waitingSeparator) {
                    add(result, create(ch, from, i - from).trim(), emptyAsNull);
                }

                from = i + 1;
                waitingSeparator = false;
            }

            escaped = false;
        }

        if (!waitingSeparator) {
            String s = create(ch, from, n - from).trim();

            if (s.length() > 0) {
                if ("null".equalsIgnoreCase(s)) {
	                result.add(null);
                } else {
	                result.add(s);
                }
            }
            else if(emptyAsNull) {
                result.add(null);
            }
        }

        return result;
    }

    static String create(char[] ch, int start, int length) {

        boolean escaped = false;

        StringBuffer buffer = new StringBuffer(length);

        for (int i = 0, j = start; i < length; i++, j++) {

			if (!escaped && ch[j] == '\\') {
				escaped = true;
			} else if (!escaped && ch[j] == '"') {
				escaped = true;
            } else {
                escaped = false;
                buffer.append(ch[j]);
            }

        }

        return buffer.toString();
    }

    private static void add(ArrayList result, String s, boolean emptyAsNull) {

        if (emptyAsNull && (s.length() == 0 || "null".equalsIgnoreCase(s)))
            result.add(null);
        else
            result.add(s);

    }
    
    private Lists() {}
}
