package com.mptourism.planner.service;

import com.mptourism.planner.dto.PaymentRequest;
import com.mptourism.planner.dto.TripRequest;
import com.mptourism.planner.model.*;
import com.mptourism.planner.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripPlaceRepository tripPlaceRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private NearbyPlaceRepository nearbyPlaceRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Map<String, Object> calculateAndSaveTrip(TripRequest request, Long userId) {
        if (request.getBudget() == null || request.getBudget() <= 0) {
            throw new IllegalArgumentException("Budget must be a positive value.");
        }
        if (request.getPlaceName() == null || request.getPlaceName().isBlank()) {
            throw new IllegalArgumentException("Place name is required.");
        }

        Trip trip = new Trip(
                userId,
                request.getPlaceName().trim(),
                request.getBudget(),
                request.getMen() != null ? request.getMen() : 0,
                request.getWomen() != null ? request.getWomen() : 0,
                request.getChildren() != null ? request.getChildren() : 0,
                request.getDays() != null ? request.getDays() : 1
        );
        Trip savedTrip = tripRepository.save(trip);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("tripId", savedTrip.getId());
        result.put("placeName", savedTrip.getPlaceName());
        result.put("budget", savedTrip.getBudget());
        result.put("remainingBudget", savedTrip.getBudget());
        result.put("men", savedTrip.getMen());
        result.put("women", savedTrip.getWomen());
        result.put("children", savedTrip.getChildren());
        result.put("totalMembers", savedTrip.getTotalMembers());
        result.put("days", savedTrip.getDays());
        result.put("status", savedTrip.getStatus());
        result.put("message", "Trip created and calculated successfully.");
        return result;
    }

    @Transactional
    public Map<String, Object> addPlaceToTrip(Long tripId, Long placeId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));

        NearbyPlace nearbyPlace = nearbyPlaceRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Nearby place not found with id: " + placeId));

        int totalMembers = trip.getTotalMembers();
        double placeTotalCost = nearbyPlace.getEstimatedCostPerPerson() * totalMembers;

        double currentSpent = calculateTotalSpent(tripId);
        double remainingBudget = trip.getBudget() - currentSpent;

        if (placeTotalCost > remainingBudget) {
            throw new IllegalArgumentException(String.format("Cannot add '%s'. Cost (₹%.2f) exceeds remaining budget (₹%.2f).", 
                    nearbyPlace.getName(), placeTotalCost, remainingBudget));
        }

        // Check if already added
        Optional<TripPlace> existing = tripPlaceRepository.findByTripIdAndPlaceId(tripId, placeId);
        TripPlace tripPlace;
        if (existing.isPresent()) {
            tripPlace = existing.get();
            tripPlace.setStatus("ADDED");
            tripPlace.setCost(placeTotalCost);
        } else {
            tripPlace = new TripPlace(tripId, placeId, nearbyPlace.getName(), placeTotalCost, "ADDED");
        }
        tripPlaceRepository.save(tripPlace);

        double newSpent = calculateTotalSpent(tripId);
        double newRemaining = trip.getBudget() - newSpent;

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("tripId", tripId);
        response.put("placeId", placeId);
        response.put("placeName", nearbyPlace.getName());
        response.put("placeCost", placeTotalCost);
        response.put("totalSpent", newSpent);
        response.put("remainingBudget", newRemaining);
        response.put("message", "Place added to trip successfully.");
        return response;
    }

    @Transactional
    public Map<String, Object> removePlaceFromTrip(Long tripId, Long placeId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));

        Optional<TripPlace> existing = tripPlaceRepository.findByTripIdAndPlaceId(tripId, placeId);
        if (existing.isPresent()) {
            TripPlace tp = existing.get();
            tp.setStatus("REMOVED");
            tripPlaceRepository.save(tp);
        }

        double newSpent = calculateTotalSpent(tripId);
        double newRemaining = trip.getBudget() - newSpent;

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("tripId", tripId);
        response.put("removedPlaceId", placeId);
        response.put("totalSpent", newSpent);
        response.put("remainingBudget", newRemaining);
        response.put("message", "Place removed from trip successfully.");
        return response;
    }

    public List<Map<String, Object>> getSuggestedPlaces(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));

        double currentSpent = calculateTotalSpent(tripId);
        double remainingBudget = trip.getBudget() - currentSpent;
        int totalMembers = trip.getTotalMembers();

        Location location = findLocationByName(trip.getPlaceName());
        if (location == null) {
            return Collections.emptyList();
        }

        List<NearbyPlace> allNearby = nearbyPlaceRepository.findByLocationId(location.getId());
        List<TripPlace> addedPlaces = tripPlaceRepository.findByTripIdAndStatus(tripId, "ADDED");
        Set<Long> addedPlaceIds = addedPlaces.stream()
                .map(TripPlace::getPlaceId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<Map<String, Object>> suggestions = new ArrayList<>();
        for (NearbyPlace np : allNearby) {
            double totalCost = np.getEstimatedCostPerPerson() * totalMembers;
            if (totalCost <= remainingBudget) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("id", np.getId());
                map.put("name", np.getName());
                map.put("distanceKm", np.getDistanceKm());
                map.put("estimatedCostPerPerson", np.getEstimatedCostPerPerson());
                map.put("totalCostForFamily", totalCost);
                map.put("category", np.getCategory());
                map.put("description", np.getDescription());
                map.put("imageUrl", np.getImageUrl());
                map.put("isAdded", addedPlaceIds.contains(np.getId()));
                suggestions.add(map);
            }
        }
        return suggestions;
    }

    public List<Map<String, Object>> getSuggestedHotels(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));

        double currentSpent = calculateTotalSpent(tripId);
        double remainingBudget = trip.getBudget() - currentSpent;
        int days = trip.getDays() != null && trip.getDays() > 0 ? trip.getDays() : 1;

        Location location = findLocationByName(trip.getPlaceName());
        if (location == null) {
            return Collections.emptyList();
        }

        List<Hotel> allHotels = hotelRepository.findByLocationId(location.getId());
        List<Map<String, Object>> suggestions = new ArrayList<>();

        for (Hotel h : allHotels) {
            double totalStayPrice = h.getPricePerNight() * days;
            if (totalStayPrice <= remainingBudget || h.getPricePerNight() <= remainingBudget) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("id", h.getId());
                map.put("hotelName", h.getHotelName());
                map.put("rating", h.getRating());
                map.put("pricePerNight", h.getPricePerNight());
                map.put("totalStayCost", totalStayPrice);
                map.put("amenities", h.getAmenities());
                map.put("imageUrl", h.getImageUrl());
                suggestions.add(map);
            }
        }
        return suggestions;
    }

    @Transactional
    public Map<String, Object> processTripPayment(Long tripId, String paymentMethod) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));

        double totalSpent = calculateTotalSpent(tripId);
        double paymentAmount = totalSpent > 0 ? totalSpent : trip.getBudget();

        String txId = "MPT-TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Payment payment = new Payment(
                tripId,
                paymentAmount,
                "SUCCESS",
                txId,
                paymentMethod != null ? paymentMethod : "Razorpay Mock UPI"
        );
        Payment savedPayment = paymentRepository.save(payment);

        trip.setStatus("PAID");
        tripRepository.save(trip);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("paymentId", savedPayment.getId());
        response.put("transactionId", savedPayment.getTransactionId());
        response.put("tripId", tripId);
        response.put("destination", trip.getPlaceName());
        response.put("amount", savedPayment.getAmount());
        response.put("status", savedPayment.getStatus());
        response.put("tripStatus", trip.getStatus());
        response.put("paymentMethod", savedPayment.getPaymentMethod());
        response.put("message", "Payment created and trip marked as PAID successfully!");
        return response;
    }

    public Map<String, Object> saveTripPlaces(Long tripId, List<Map<String, Object>> places) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with id: " + tripId));

        List<TripPlace> tripPlaces = places.stream()
                .map(p -> new TripPlace(
                        tripId,
                        null,
                        (String) p.getOrDefault("placeName", "Unknown"),
                        ((Number) p.getOrDefault("cost", 0)).doubleValue(),
                        (String) p.getOrDefault("status", "ADDED")
                ))
                .collect(Collectors.toList());

        tripPlaceRepository.saveAll(tripPlaces);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("tripId", tripId);
        response.put("placesSaved", tripPlaces.size());
        response.put("remainingBudget", trip.getBudget() - calculateTotalSpent(tripId));
        response.put("message", "Trip places saved successfully.");
        return response;
    }

    private double calculateTotalSpent(Long tripId) {
        List<TripPlace> addedPlaces = tripPlaceRepository.findByTripIdAndStatus(tripId, "ADDED");
        return addedPlaces.stream().mapToDouble(TripPlace::getCost).sum();
    }

    private Location findLocationByName(String placeName) {
        if (placeName == null) return null;
        return locationRepository.findByName(placeName)
                .orElseGet(() -> locationRepository.findAll().stream()
                        .filter(l -> l.getName().equalsIgnoreCase(placeName) || placeName.toLowerCase().contains(l.getName().toLowerCase()))
                        .findFirst().orElse(null));
    }

    public List<Map<String, Object>> getUserTrips(Long userId) {
        List<Trip> trips = tripRepository.findByUserId(userId);
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Trip trip : trips) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("tripId", trip.getId());
            map.put("placeName", trip.getPlaceName());
            map.put("days", trip.getDays());
            map.put("budget", trip.getBudget());

            double totalSpent = calculateTotalSpent(trip.getId());
            map.put("totalSpent", totalSpent);
            map.put("remainingBudget", trip.getBudget() - totalSpent);

            map.put("men", trip.getMen());
            map.put("women", trip.getWomen());
            map.put("children", trip.getChildren());
            map.put("totalMembers", trip.getTotalMembers());
            map.put("status", trip.getStatus());
            map.put("createdAt", trip.getCreatedAt());

            // Destination Location image
            Location loc = findLocationByName(trip.getPlaceName());
            if (loc != null && loc.getImageUrl() != null) {
                map.put("imageUrl", loc.getImageUrl());
            } else {
                map.put("imageUrl", "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=800&q=80");
            }

            // Places added to trip
            List<TripPlace> addedPlaces = tripPlaceRepository.findByTripIdAndStatus(trip.getId(), "ADDED");
            List<Map<String, Object>> placesList = new ArrayList<>();
            for (TripPlace tp : addedPlaces) {
                Map<String, Object> pMap = new LinkedHashMap<>();
                pMap.put("id", tp.getId());
                pMap.put("placeId", tp.getPlaceId());
                pMap.put("placeName", tp.getPlaceName());
                pMap.put("cost", tp.getCost());
                placesList.add(pMap);
            }
            map.put("places", placesList);

            // Payment details if paid
            List<Payment> payments = paymentRepository.findByTripId(trip.getId());
            if (!payments.isEmpty()) {
                Payment payment = payments.get(payments.size() - 1);
                Map<String, Object> payMap = new LinkedHashMap<>();
                payMap.put("transactionId", payment.getTransactionId());
                payMap.put("amount", payment.getAmount());
                payMap.put("status", payment.getStatus());
                payMap.put("paymentMethod", payment.getPaymentMethod());
                payMap.put("createdAt", payment.getCreatedAt());
                map.put("payment", payMap);
            } else {
                map.put("payment", null);
            }

            resultList.add(map);
        }
        return resultList;
    }
}

