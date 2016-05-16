package com.expeval;

import com.expeval.tokens.Token;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by shahrukhimam on 06/05/16.
 */
public class Utils {

    public static boolean isValidJavaIdentifier(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        char[] c = s.toCharArray();
        if (!Character.isJavaIdentifierStart(c[0])) {
            return false;
        }

        for (int i = 1; i < c.length; i++) {
            if (!Character.isJavaIdentifierPart(c[i])) {
                return false;
            }
        }

        if (Token.FALSE.getLexeme().equals(s) || Token.TRUE.getLexeme().equals(s) || Token.NULL.getLexeme().equals(s)) {
            return false;
        }

        return true;
    }


    public static String setToString(Set<String> set) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            sb.append('\'');
            sb.append(iterator.next());
            sb.append('\'');
            sb.append(',');
        }
        String result = sb.toString();
        return result.length() > 0 ? result.substring(0, result.length() - 1) : result;
    }
}
