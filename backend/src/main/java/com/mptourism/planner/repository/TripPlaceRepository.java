package com.mptourism.planner.repository;

import com.mptourism.planner.model.TripPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripPlaceRepository extends JpaRepository<TripPlace, Long> {
    List<TripPlace> findByTripId(Long tripId);
    List<TripPlace> findByTripIdAndStatus(Long tripId, String status);
    Optional<TripPlace> findByTripIdAndPlaceIdAndStatus(Long tripId, Long placeId, String status);
    Optional<TripPlace> findByTripIdAndPlaceId(Long tripId, Long placeId);
    void deleteByTripIdAndPlaceId(Long tripId, Long placeId);
}
