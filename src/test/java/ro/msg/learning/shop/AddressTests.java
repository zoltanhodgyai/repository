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
import ro.msg.learning.shop.repository.AddressRepository;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Sql({"/test.sql"})
@Slf4j
public class AddressTests extends ShopTest{

    @Autowired
    AddressRepository addressRepository;

    @Test
    public void testCreateUpdateAddress() {

        Address address = getAddress(COUNTRY, CITY, COUNTY, STREET_ADDRESS);

        Address created = addressRepository.save(address);

        Assert.assertNotNull(created);
        Assert.assertNotNull(created.getId());
        Assert.assertEquals(COUNTRY, created.getCountry());

        created.setCity("M-Ciuc");
        created.setCounty("Harghita");
        created.setStreetAddress("Culmei 211");

        Address updated = addressRepository.save(created);

        Assert.assertNotNull(updated);
        Assert.assertNotNull(updated.getId());
        Assert.assertEquals(created.getId(), updated.getId());
        Assert.assertEquals(created.getCountry(), updated.getCountry());
        Assert.assertTrue(updated.getStreetAddress().contains("Culmei"));
    }

    @Test
    public void testReadAddress() {
        List<Address> addresses = createAddresses();
        Assert.assertEquals(3, addresses.size());

        Address address = addressRepository.findAddressById(addresses.get(0).getId());

        Assert.assertNotNull(address);
        Assert.assertNotNull(address.getId());
        Assert.assertEquals(STREET_ADDRESS, address.getStreetAddress());
        Assert.assertEquals(COUNTY, address.getCounty());
    }

    @Test
    public void testDeleteAddress() {
        List<Address> addresses = createAddresses();
        Assert.assertEquals(3, addresses.size());

        addressRepository.deleteAddressById(addresses.get(2).getId());

        Assert.assertEquals(9, addressRepository.findAll().size());
    }

    @Test
    public void testReadAll() {
        List<Address> addresses = createAddresses();
        Assert.assertEquals(3, addresses.size());

        Assert.assertEquals(10, addressRepository.findAll().size());

    }

    private List<Address> createAddresses() {
        Address address1 = getAddress();
        Address a1 = addressRepository.save(address1);
        Assert.assertNotNull(a1);
        Address address2 = getAddress(COUNTRY, CITY, COUNTY, "Plopilor 1.");
        Address a2 = addressRepository.save(address2);
        Assert.assertNotNull(a2);
        Address address3 = getAddress(COUNTRY, CITY, COUNTY, "Dorobantilor 97.");
        Address a3 = addressRepository.save(address3);
        Assert.assertNotNull(a3);

        List<Address> result = new ArrayList<>();
        result.add(a1);
        result.add(a2);
        result.add(a3);

        return result;
    }
}
