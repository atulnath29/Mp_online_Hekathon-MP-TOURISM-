package com.mptourism.planner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hotelName;

    private Double rating;
    private Double pricePerNight;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    private String amenities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonBackReference("location-hotels")
    private Location location;

    public Hotel() {}

    public Hotel(String hotelName, Double rating, Double pricePerNight, String imageUrl, String amenities) {
        this.hotelName = hotelName;
        this.rating = rating;
        this.pricePerNight = pricePerNight;
        this.imageUrl = imageUrl;
        this.amenities = amenities;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(Double pricePerNight) { this.pricePerNight = pricePerNight; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
}
