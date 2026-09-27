package com.mptourism.planner.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trip_places")
public class TripPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trip_id", nullable = false)
    private Long tripId;

    @Column(name = "place_id")
    private Long placeId;

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private String status; // "ADDED", "REMOVED"

    public TripPlace() {}

    public TripPlace(Long tripId, Long placeId, String placeName, Double cost, String status) {
        this.tripId = tripId;
        this.placeId = placeId;
        this.placeName = placeName;
        this.cost = cost;
        this.status = "ADDED";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }

    public Long getPlaceId() { return placeId; }
    public void setPlaceId(Long placeId) { this.placeId = placeId; }

    public String getPlaceName() { return placeName; }
    public void setPlaceName(String placeName) { this.placeName = placeName; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
