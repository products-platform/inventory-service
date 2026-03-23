package com.web.inventory.services;

import com.product.dtos.CreateLocationRequest;
import com.product.dtos.LocationResponse;

import java.util.List;

public interface LocationService {

    LocationResponse createLocation(CreateLocationRequest request);

    List<LocationResponse> getAllLocations();

    LocationResponse getLocationById(Long id);
}
