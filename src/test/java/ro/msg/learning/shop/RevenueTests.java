package ro.msg.learning.shop;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.model.Revenue;
import ro.msg.learning.shop.repository.RevenueRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class RevenueTests {

    @Autowired
    private RevenueRepository revenueRepository;

    @Test
    public void testCreateUpdateRevenue() {
        Revenue revenue = getRevenue(1, LocalDateTime.now(), BigDecimal.valueOf(12.34));

        Revenue created = revenueRepository.save(revenue);

        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());

        Revenue read = revenueRepository.findRevenueById(created.getId());

        Assert.assertNotNull(read);
        Assert.assertEquals(created.getId(), read.getId());
        Assert.assertEquals(created.getLocation(), read.getLocation());

        read.setSum(BigDecimal.valueOf(13.24));

        Revenue updated = revenueRepository.save(read);

        Assert.assertNotNull(updated);
        Assert.assertEquals(read.getId(), updated.getId());
        Assert.assertEquals(BigDecimal.valueOf(13.24), updated.getSum());
    }

    @Test
    public void testReadRevenues() {
        createRevenues();

        Assert.assertEquals(5, revenueRepository.findAll().size());
        Assert.assertEquals(3, revenueRepository.findAllByLocation(3).size());
        Assert.assertEquals(5, revenueRepository.findAllByDateBefore(LocalDateTime.now()).size());
        Assert.assertEquals(2, revenueRepository.findAllByDateBefore(LocalDateTime.now().minusMonths(1)).size());
        Assert.assertEquals(1, revenueRepository.findAllByDateBefore(LocalDateTime.now().minusMonths(2).minusDays(15)).size());
        Assert.assertEquals(1, revenueRepository.findAllByDateBetween(LocalDateTime.now().minusMonths(2).minusDays(15),
                LocalDateTime.now().minusMonths(1)).size());
    }

    private void createRevenues() {
        Revenue r1 = getRevenue(1, LocalDateTime.now(), BigDecimal.valueOf(12.22));
        Revenue r2 = getRevenue(2, LocalDateTime.now(), BigDecimal.valueOf(167.88));
        Revenue r3 = getRevenue(3, LocalDateTime.now(), BigDecimal.valueOf(1.19));
        Revenue r4 = getRevenue(3, LocalDateTime.now().minusMonths(2), BigDecimal.valueOf(11.11));
        Revenue r5 = getRevenue(3, LocalDateTime.now().minusMonths(3), BigDecimal.valueOf(274.00));

        Assert.assertNotNull(revenueRepository.save(r1));
        Assert.assertNotNull(revenueRepository.save(r2));
        Assert.assertNotNull(revenueRepository.save(r3));
        Assert.assertNotNull(revenueRepository.save(r4));
        Assert.assertNotNull(revenueRepository.save(r5));
    }

    private Revenue getRevenue(Integer locationNumber, LocalDateTime date, BigDecimal sum) {
        Revenue revenue = new Revenue();
        revenue.setLocation(locationNumber);
        revenue.setDate(date);
        revenue.setSum(sum);

        return revenue;
    }
}
