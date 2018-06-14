package ro.msg.learning.shop;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.dto.StockDTO;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.utility.CsvConverter;

import java.io.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvHandlerTests {

    @Autowired
    StockRepository stockRepository;

    @Test
    public void testCreateCsv() throws IOException {
        List<Stock> stocks = stockRepository.findAll();

        OutputStream outputStream =  new FileOutputStream("test.csv");
        CsvConverter.toCsv(outputStream, StockDTO.class, StockDTO.toStockDTOS(stocks));
    }

    @Test
    public void testReadCsv() throws IOException {
        InputStream inputStream = new FileInputStream("test.csv");

        List<StockDTO> stocks = CsvConverter.fromCsv(inputStream, StockDTO.class);

        Assert.assertNotNull(stocks);
        Assert.assertEquals(8, stocks.size());
    }
}
