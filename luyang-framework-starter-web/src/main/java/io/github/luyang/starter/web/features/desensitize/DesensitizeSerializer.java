//package io.github.luyang.starter.web.features.desensitize;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.BeanProperty;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.ser.ContextualSerializer;
//import io.github.luyang.base.util.StrUtil;
//
//import java.io.IOException;
//import java.util.Optional;
//
///**
// * 字段脱敏序列化器
// *
// * @author yang.lu
// */
//public class DesensitizeSerializer extends JsonSerializer<String> implements ContextualSerializer {
//
//    private DesensitizeRule desensitizeRule;
//    private int front;
//    private int end;
//
//    @Override
//    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        if (StrUtil.isNotBlank(value)) {
//            if (DesensitizeRule.CUSTOMIZE.equals(desensitizeRule)) {
//                jsonGenerator.writeString(StrUtil.hide(value, front, end));
//                return;
//            }
//
//            jsonGenerator.writeString(desensitizeRule.mask(value));
//            return;
//        }
//
//        jsonGenerator.writeString(value);
//    }
//
//    @Override
//    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
//        if (null != beanProperty) {
//            Desensitize annotation = Optional.ofNullable(beanProperty.getAnnotation(Desensitize.class))
//                .orElse(beanProperty.getContextAnnotation(Desensitize.class));
//            if (null != annotation) {
//                this.desensitizeRule = annotation.rule();
//                this.front = annotation.front();
//                this.end = annotation.end();
//            }
//        }
//
//        return this;
//    }
//}
