package com.mptourism.planner.controller;

import com.mptourism.planner.dto.TripRequest;
import com.mptourism.planner.model.User;
import com.mptourism.planner.service.TripService;
import com.mptourism.planner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createTrip(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                         @RequestBody TripRequest request) {
        try {
            Long userId = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                try {
                    User user = userService.getUserFromToken(authHeader);
                    userId = user.getId();
                } catch (Exception ignored) {}
            }
            Map<String, Object> result = tripService.calculateAndSaveTrip(request, userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Trip creation failed: " + e.getMessage()));
        }
    }

    @GetMapping("/my-trips")
    public ResponseEntity<?> getMyTrips(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Authentication required. Please login."));
        }
        try {
            User user = userService.getUserFromToken(authHeader);
            List<Map<String, Object>> myTrips = tripService.getUserTrips(user.getId());
            return ResponseEntity.ok(myTrips);
        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Unauthorized: " + e.getMessage()));
        }
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateTrip(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                            @RequestBody TripRequest request) {
        return createTrip(authHeader, request);
    }


    @PostMapping("/{tripId}/places/{placeId}")
    public ResponseEntity<?> addPlaceToTrip(@PathVariable("tripId") Long tripId,
                                            @PathVariable("placeId") Long placeId) {
        try {
            Map<String, Object> result = tripService.addPlaceToTrip(tripId, placeId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to add place to trip: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{tripId}/places/{placeId}")
    public ResponseEntity<?> removePlaceFromTrip(@PathVariable("tripId") Long tripId,
                                               @PathVariable("placeId") Long placeId) {
        try {
            Map<String, Object> result = tripService.removePlaceFromTrip(tripId, placeId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to remove place from trip: " + e.getMessage()));
        }
    }

    @GetMapping("/{tripId}/suggestions")
    public ResponseEntity<?> getSuggestedPlaces(@PathVariable("tripId") Long tripId) {
        try {
            List<Map<String, Object>> result = tripService.getSuggestedPlaces(tripId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to get suggested places: " + e.getMessage()));
        }
    }

    @GetMapping("/{tripId}/hotels")
    public ResponseEntity<?> getSuggestedHotels(@PathVariable("tripId") Long tripId) {
        try {
            List<Map<String, Object>> result = tripService.getSuggestedHotels(tripId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to get suggested hotels: " + e.getMessage()));
        }
    }

    @PostMapping("/{tripId}/payment")
    public ResponseEntity<?> processPayment(@PathVariable("tripId") Long tripId,
                                           @RequestBody(required = false) Map<String, String> body) {
        try {
            String paymentMethod = body != null ? body.get("paymentMethod") : "Razorpay Mock UPI";
            Map<String, Object> result = tripService.processTripPayment(tripId, paymentMethod);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Payment processing failed: " + e.getMessage()));
        }
    }

    @PostMapping("/{tripId}/places")
    public ResponseEntity<?> saveTripPlaces(@PathVariable("tripId") Long tripId,
                                           @RequestBody List<Map<String, Object>> places) {
        try {
            Map<String, Object> result = tripService.saveTripPlaces(tripId, places);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to save trip places: " + e.getMessage()));
        }
    }
}
