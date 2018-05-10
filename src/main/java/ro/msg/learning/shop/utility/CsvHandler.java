package ro.msg.learning.shop.utility;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.model.Stock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvHandler {

    public List<Stock> fromCsv(InputStream inputStream, Stock stock) {
        return new ArrayList<>();
    }

    public void toCsv(OutputStream outputStream, List<Stock> stocks) throws IOException {

        CsvMapper mapper = new CsvMapper();

        CsvSchema schema = mapper.schemaFor(Stock.class);

        String s = mapper.writer(schema.withUseHeader(true)).writeValueAsString(stocks);
    }
}
