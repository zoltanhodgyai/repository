package ro.msg.learning.shop.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
@SuppressWarnings("unchecked")
public class CsvHandler<T> extends AbstractGenericHttpMessageConverter<List<T>> {

    public CsvHandler() {
        super(new MediaType("text", "csv"));
    }

    @Override
    public boolean canRead(Type type, @Nullable Class<?> contextClass, @Nullable MediaType mediaType) {
        return type instanceof Class ? this.canRead((Class) type, mediaType) : this.canRead(mediaType);
    }

    @Override
    public boolean canWrite(@Nullable Type type, Class<?> clazz, @Nullable MediaType mediaType) {

        Class listType = type instanceof Class ? type.getClass() : (Class) ((ParameterizedType) type).getRawType();
        if (!listType.isAssignableFrom(List.class)) {
            return false;
        }
        return this.canWrite(clazz, mediaType);
    }

    @Override
    public List<T> read(Type type, Class aClass, HttpInputMessage httpInputMessage) throws IOException {
        return CsvConverter.fromCsv(httpInputMessage.getBody(), aClass);
    }

    @Override
    public void writeInternal(List<T> ts, Type type, HttpOutputMessage httpOutputMessage) throws IOException {
        Class classType = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        CsvConverter.toCsv(httpOutputMessage.getBody(), classType, ts);
    }

    @Override
    protected List<T> readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException {
        return CsvConverter.fromCsv(httpInputMessage.getBody(), aClass);
    }
}
