package com.notespace.Font;

import org.apache.commons.lang3.StringEscapeUtils;

public class FontRepair {


    public static String fixmyanamrfont(String text) {

//        return text;
//        if (text == null) {     //if null break
//            return null;
//        }

        if (text.length() < 1) {
            return null;
        } else {
            StringBuilder escapebuilder = new StringBuilder();
            String unescape = "";
            escapebuilder.append(org.apache.commons.lang3.StringEscapeUtils.escapeJson(text));
            escapebuilder.append("\u200C");
//		escapebuilder.append ( "\u200c" );
            unescape = StringEscapeUtils.unescapeJson(escapebuilder.toString());
            return unescape;
        }


    }
}
