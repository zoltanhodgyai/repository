package ro.msg.learning.shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.utility.CsvHandler;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class CsvHandlerTests {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    private CsvHandler csvHandler;

    @Test
    public void testCreateCsv() throws IOException {
        List<Stock> stocks = stockRepository.findAll();

        OutputStream outputStream =  new FileOutputStream("ALMA.csv");
        csvHandler.toCsv(outputStream, stocks);
    }
}
