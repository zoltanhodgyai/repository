package ro.msg.learning.shop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.dto.LocationDTO;
import ro.msg.learning.shop.dto.OrderDTO;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("SingleLocationWithProximityStrategy")
@Slf4j
public class SingleLocationWithProximityStrategy extends SingleLocation implements Strategy {

    @Value("${google.api.key}")
    String apiKey;

    @Value("${proxy.host}")
    String proxyHost;

    @Value("${proxy.port}")
    int proxyPort;

    @Value("${google.url}")
    String googleUrl;

    @Value("${google.units}")
    String units;


    private StockRepository stockRepository;

    private LocationRepository locationRepository;

    public SingleLocationWithProximityStrategy(StockRepository stockRepository, LocationRepository locationRepository) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Location findLocation(OrderDTO orderDTO) {
        log.info("Single Location with proximity Strategy called");

        List<Integer> locationIds = findLocationIdsByProductAndQuantity(orderDTO, stockRepository);
        if (locationIds.isEmpty()) {
            return null;
        }

        List<Location> locations = readLocationsByLocationIds(locationIds);
        SimpleClientHttpRequestFactory request = new SimpleClientHttpRequestFactory();
        request.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
        RestTemplate restTemplate = new RestTemplate(request);

        String deliveryAddress = getAddressConformGoogle(orderDTO.getDeliveryAddress());
        String url = googleUrl + "units=" + units + "&origins=" + getOrigins(locations) + "&destinations=" + deliveryAddress + "&key=" + apiKey;

        LocationDTO locationDTO = restTemplate.getForObject(url, LocationDTO.class);
        return getFinalLocation(locationDTO, locations);
    }

    private String getAddressConformGoogle(Address address) {
        StringBuilder builder = new StringBuilder();
        // example: Cluj-Napoca,RO,Scortarilor,1
        builder.append(address.getCity());
        builder.append(",");
        builder.append(address.getCountry());
        builder.append(",");
        builder.append(address.getStreetAddress());

        return builder.toString();
    }

    private String getOrigins(List<Location> locations) {
        StringBuilder builder = new StringBuilder();
        for (Location location : locations) {
            builder.append(getAddressConformGoogle(location.getAddress()));
            builder.append("|");
        }
        return builder.toString();
    }

    private List<Location> readLocationsByLocationIds(List<Integer> ids) {
        List<Location> result = new ArrayList<>();
        for (Integer id : ids) {
            result.add(locationRepository.findLocationById(id));
        }
        result.sort(new LocationComparator());
        return result;
    }

    private Location getFinalLocation(LocationDTO locationDTO, List<Location> locations) {

        // origin is rows = locations.size
        // destination is elements = always 1
        long result = -1;
        Location location = null;
        for (int i = 0; i < locationDTO.getOrigin_addresses().length; i++) {
            for (int j = 0; j < locationDTO.getDestination_addresses().length; j++) {
                if (result < 0 || result > locationDTO.getRows()[i].getElements()[j].getDistance().getValue()) {
                    result = locationDTO.getRows()[i].getElements()[j].getDistance().getValue();
                    location = locations.get(i);
                }
            }
        }
        return location;
    }

    private class LocationComparator implements java.util.Comparator<Location> {

        @Override
        public int compare(Location l1, Location l2) {
            if (l1.getId() < l2.getId()) {
                return -1;
            } else if (l1.getId() > l2.getId()) {
                return 1;
            }
            return 0;
        }
    }
}
