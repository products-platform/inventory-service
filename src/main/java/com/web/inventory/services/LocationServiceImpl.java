package com.web.inventory.services;

import com.product.dtos.CreateLocationRequest;
import com.product.dtos.LocationResponse;
import com.web.inventory.models.Location;
import com.web.inventory.repos.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public LocationResponse createLocation(CreateLocationRequest request) {

        Location location = Location.builder()
                .name(request.name())
                .city(request.city())
                .state(request.state())
                .country(request.country())
                .type(request.type())
                .active(true)
                .build();

        Location saved = locationRepository.save(location);

        return map(saved);
    }

    @Override
    public List<LocationResponse> getAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public LocationResponse getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        return map(location);
    }

    private LocationResponse map(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getCity(),
                location.getState(),
                location.getCountry(),
                location.getType(),
                location.getActive()
        );
    }
}
