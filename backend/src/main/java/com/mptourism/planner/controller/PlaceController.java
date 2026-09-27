package com.mptourism.planner.controller;

import com.mptourism.planner.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceService.Destination>> getAllPlaces() {
        return ResponseEntity.ok(placeService.getAllDestinations());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlaceService.Destination>> searchPlaces(@RequestParam(value = "q", required = false) String query) {
        return ResponseEntity.ok(placeService.searchDestinations(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaceById(@PathVariable("id") String id) {
        PlaceService.Destination dest = placeService.getDestinationById(id);
        if (dest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dest);
    }
}
