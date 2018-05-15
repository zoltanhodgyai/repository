package ro.msg.learning.shop.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
@SuppressWarnings("unchecked")
public class CsvHandler<T> extends AbstractGenericHttpMessageConverter<List<T>> {

    @Override
    public List<T> read(Type type, Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return CsvConverter.fromCsv(httpInputMessage.getBody(), aClass, type);
    }

    @Override
    public void writeInternal(List<T> ts, Type type, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        // todo: cast ts maybe
        // cosntrangeri: tre sa fie o lista
        // tre sa fie ParameterizedType (getRawType(), getActualTypeArguments())
        CsvConverter.toCsv(httpOutputMessage.getBody(), null, null, ts);
    }

    @Override
    protected List<T> readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return CsvConverter.fromCsv(httpInputMessage.getBody(), aClass, null);
    }
}
