package com.caine.platform.gateway.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;

/**
 * @Author: CaineZhu
 * @Description:
 * @Date: Created in 9:32 2019/9/10
 * @Modified By:
 */
@Configuration
public class JsonConfig  {
    /**
     * 重写json converter做xss过滤
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter htmlEscapingConverter = new MappingJackson2HttpMessageConverter();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = new ObjectMapper();
        //设置解析日期的工具类
        objectMapper.setDateFormat(dateFormat);
        //忽略未知属性 防止解析报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        htmlEscapingConverter.setObjectMapper(objectMapper);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new StringXssDeserializer());
        htmlEscapingConverter.getObjectMapper().registerModule(simpleModule);
        return htmlEscapingConverter;
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//        //创建配置类
//        FastJsonConfig config = new FastJsonConfig();
//        //创建fastjson消息转换器
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        //常用的SerializerFeatures配置
//        config.setSerializerFeatures(
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.WriteNullListAsEmpty,
//                SerializerFeature.WriteNullBooleanAsFalse,
//                SerializerFeature.WriteNullNumberAsZero
//        );
//        config.setDateFormat("yyyy-MM-dd hh:mm:ss");
//        List<MediaType> mediaType = new ArrayList<>();
//        mediaType.add(MediaType.APPLICATION_JSON_UTF8);
//        fastConverter.setSupportedMediaTypes(mediaType);
//        fastConverter.setFastJsonConfig(config);
//        //将fastjson添加到视图消息转换器
//        converters.add(fastConverter);
//    }
}
