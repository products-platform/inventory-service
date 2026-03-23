package com.web.inventory.controls;

import com.product.dtos.CreateLocationRequest;
import com.product.dtos.LocationResponse;
import com.web.inventory.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationRestController {

    private final LocationService locationService;

    @PostMapping
    public LocationResponse create(@RequestBody CreateLocationRequest request) {
        return locationService.createLocation(request);
    }

    @GetMapping
    public List<LocationResponse> getAll() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public LocationResponse getById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }
}
