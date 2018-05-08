package ro.msg.learning.shop;

import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Customer;

class ShopTest {

    static final String COUNTRY = "Romania";
    static final String CITY = "Cluj-Napoca";
    static final String COUNTY = "Cluj";
    static final String STREET_ADDRESS = "Brassai 9.";

    static final String CUSTOMER_FIRST_NAME = "First Name";
    static final String CUSTOMER_LAST_NAME = "Last Name";
    static final String CUSTOMER_USER_NAME = "User Name";

    Address getAddress(String country, String city, String county, String street) {
        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setCounty(county);
        address.setStreetAddress(street);

        return address;
    }

    Address getAddress() {
        return getAddress(COUNTRY, CITY, COUNTY, STREET_ADDRESS);
    }

    Customer getCustomer(String firstName, String lastName, String userName) {
        Customer customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setUsername(userName);

        return customer;
    }

}
