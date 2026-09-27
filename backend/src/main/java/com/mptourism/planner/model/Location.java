package com.mptourism.planner.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    private Double latitude;
    private Double longitude;

    private String category;
    private Double rating;
    private String tagline;
    private String bestTime;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("location-nearby")
    private List<NearbyPlace> nearbyPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("location-hotels")
    private List<Hotel> hotels = new ArrayList<>();

    public Location() {}

    public Location(String name, String imageUrl, String shortDescription, Double latitude, Double longitude, String category, Double rating, String tagline, String bestTime) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.shortDescription = shortDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.rating = rating;
        this.tagline = tagline;
        this.bestTime = bestTime;
    }

    public void addNearbyPlace(NearbyPlace nearbyPlace) {
        nearbyPlaces.add(nearbyPlace);
        nearbyPlace.setLocation(this);
    }

    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
        hotel.setLocation(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }

    public String getBestTime() { return bestTime; }
    public void setBestTime(String bestTime) { this.bestTime = bestTime; }

    public List<NearbyPlace> getNearbyPlaces() { return nearbyPlaces; }
    public void setNearbyPlaces(List<NearbyPlace> nearbyPlaces) { this.nearbyPlaces = nearbyPlaces; }

    public List<Hotel> getHotels() { return hotels; }
    public void setHotels(List<Hotel> hotels) { this.hotels = hotels; }
}
