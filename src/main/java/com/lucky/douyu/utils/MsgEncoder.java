package com.lucky.douyu.utils;

import java.util.Map;

class MsgEncoder {
    private StringBuffer buffer;

    MsgEncoder() {
        buffer = new StringBuffer();
    }

    String encode(Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            buffer.append(entry.getKey().replaceAll("/", "@S").replaceAll("@", "@A"))
                    .append("@=")
                    .append(entry.getValue().replaceAll("/", "@S").replaceAll("@", "@A"))
                    .append("/");
        }

        buffer.append('\0');
        return buffer.toString();
    }
}
