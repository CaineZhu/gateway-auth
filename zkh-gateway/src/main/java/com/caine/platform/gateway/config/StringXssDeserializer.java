package com.caine.platform.gateway.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 11:27 2019/8/6
 * @Modified By:
 */
public class StringXssDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String source = p.getText().trim();
        // 把字符串做XSS过滤
        return StringEscapeUtils.escapeHtml4(source);
    }
}
