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
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.repository.AddressRepository;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.service.OrderService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class LocationTests extends ShopTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testCreateLocation() {
        Address address = addressRepository.save(getAddress());
        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getId());

        Location location = new Location();
        location.setName("Location Name");
        location.setAddress(address);

        Location created = locationRepository.save(location);
        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(address.getId(), location.getAddress().getId());

        created.setName("Changed");

        Location updated = locationRepository.save(created);
        Assert.assertNotNull(updated);
        Assert.assertEquals(created.getId(), updated.getId());
        Assert.assertTrue(updated.getName().contains("Changed"));
    }

    @Test
    public void testFindAllAndDelete() {
        Address address = addressRepository.save(getAddress());
        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getId());

        List<Location> locations = createLocations(address);

        Assert.assertEquals(7, locationRepository.findAll().size());

        locationRepository.deleteLocationById(locations.get(0).getId());

        Assert.assertEquals(6, locationRepository.findAll().size());
    }

    @Test
    public void testReadLocation() {
        Address address = addressRepository.save(getAddress());
        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getId());

        List<Location> locations = createLocations(address);

        Location location = locationRepository.findLocationById(locations.get(0).getId());
        Assert.assertNotNull(location);
        Assert.assertNotNull(location.getId());
        Assert.assertEquals(locations.get(0).getId(), location.getId());
        Assert.assertEquals(locations.get(0).getName(), location.getName());
        Assert.assertEquals(address.getId(), location.getAddress().getId());
    }

    private List<Location> createLocations(Address address) {
        Location location1 = new Location();
        location1.setName("First");
        location1.setAddress(address);
        Location l1 = locationRepository.save(location1);
        Assert.assertNotNull(l1);

        Location location2 = new Location();
        location2.setName("Second");
        location2.setAddress(address);
        Location l2 = locationRepository.save(location2);
        Assert.assertNotNull(l2);

        Location location3 = new Location();
        location3.setName("Third");
        location3.setAddress(address);
        Location l3 = locationRepository.save(location3);
        Assert.assertNotNull(l3);

        List<Location> result = new ArrayList<>();
        result.add(l1);
        result.add(l2);
        result.add(l3);

        return result;
    }

}
