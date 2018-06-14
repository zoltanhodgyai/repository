package ro.msg.learning.shop.utility;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CsvConverter {

    private CsvConverter() {
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> fromCsv(InputStream inputStream, Class<T> clazz) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        MappingIterator<T> it = mapper.readerFor(clazz)
                .with(schema)
                .readValues(inputStream);
        List<T> result = new ArrayList<>();
        while (it.hasNext()) {
            result.add(it.next());
        }
        return result;
    }

    public static <T> void toCsv(OutputStream outputStream, Class<T> clazz, List<T> pojos) throws IOException {

        CsvMapper mapper = new CsvMapper();

        CsvSchema schema = mapper.schemaFor(clazz);

        mapper.writer(schema.withUseHeader(true)).writeValue(outputStream, pojos);
    }
}
