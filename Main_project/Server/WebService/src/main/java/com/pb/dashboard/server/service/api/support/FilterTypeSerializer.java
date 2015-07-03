package com.pb.dashboard.server.service.api.support;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * Created by vlad
 * Date: 12.05.15_17:53
 */
public class FilterTypeSerializer extends JsonSerializer<FilterType> {

    @Override
    public void serialize(FilterType value, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeFieldName("id");
        generator.writeNumber(value.getId());
        generator.writeFieldName("value");
        generator.writeString(value.getValue());
        generator.writeFieldName("isDate");
        generator.writeString(String.valueOf(value.isDate()));
        generator.writeFieldName("isPeriod");
        generator.writeString(String.valueOf(value.isPeriod()));
        generator.writeEndObject();
    }

}
