package com.mptourism.planner.controller;

import com.mptourism.planner.model.Hotel;
import com.mptourism.planner.model.Location;
import com.mptourism.planner.model.NearbyPlace;
import com.mptourism.planner.repository.HotelRepository;
import com.mptourism.planner.repository.LocationRepository;
import com.mptourism.planner.repository.NearbyPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private NearbyPlaceRepository nearbyPlaceRepository;

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable("id") Long id) {
        return locationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalLocations", locationRepository.count());
        summary.put("totalHotels", hotelRepository.count());
        summary.put("totalNearbyPlaces", nearbyPlaceRepository.count());
        return ResponseEntity.ok(summary);
    }
}
