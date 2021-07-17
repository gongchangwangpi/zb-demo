package com.test.json.jsckson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.test.json.DateConstant;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * @author bo6.zhang
 * @date 2021/7/15
 */
public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {

    @SneakyThrows
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            gen.writeString("");
        } else {
            if (DateConstant.DB_DEFAULT_TIME.equals(value)) {
                gen.writeString("");
            } else {
                Class<?> type = gen.getOutputContext().getCurrentValue().getClass();
                String currentName = gen.getOutputContext().getCurrentName();
                Field field = type.getDeclaredField(currentName);
                JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
                if (jsonFormat != null) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    if (StringUtils.isNotEmpty(jsonFormat.pattern())) {
                        dateTimeFormatter = DateTimeFormatter.ofPattern(jsonFormat.pattern());
                    }
                    gen.writeString(dateTimeFormatter.format(value));
                } else {
                    // 默认格式
                    JsonSerializer<Object> valueSerializer = serializers.findValueSerializer(LocalDateTime.class);
                    valueSerializer.serialize(value, gen, serializers);
                }
            }
        }
    }

}
