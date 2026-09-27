package com.mptourism.planner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "nearby_places")
public class NearbyPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Double distanceKm;
    private Double estimatedCostPerPerson;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonBackReference("location-nearby")
    private Location location;

    public NearbyPlace() {}

    public NearbyPlace(String name, Double distanceKm, Double estimatedCostPerPerson, String description, String imageUrl, String category) {
        this.name = name;
        this.distanceKm = distanceKm;
        this.estimatedCostPerPerson = estimatedCostPerPerson;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Double getEstimatedCostPerPerson() { return estimatedCostPerPerson; }
    public void setEstimatedCostPerPerson(Double estimatedCostPerPerson) { this.estimatedCostPerPerson = estimatedCostPerPerson; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
}
