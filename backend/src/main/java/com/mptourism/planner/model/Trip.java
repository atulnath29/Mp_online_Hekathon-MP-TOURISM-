package com.mptourism.planner.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "place_name")
    private String placeName;

    @Column(nullable = false)
    private Double budget;

    @Column(nullable = false)
    private Integer men;

    @Column(nullable = false)
    private Integer women;

    @Column(nullable = false)
    private Integer children;

    @Column(nullable = false)
    private Integer days;

    private String status = "CREATED"; // "CREATED", "PAID"

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Trip() {}

    public Trip(Long userId, String placeName, Double budget, Integer men, Integer women, Integer children, Integer days) {
        this.userId = userId;
        this.placeName = placeName;
        this.budget = budget;
        this.men = men != null ? men : 0;
        this.women = women != null ? women : 0;
        this.children = children != null ? children : 0;
        this.days = days != null ? days : 1;
        this.status = "CREATED";
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = "CREATED";
        }
    }

    public int getTotalMembers() {
        int m = men != null ? men : 0;
        int w = women != null ? women : 0;
        int c = children != null ? children : 0;
        int total = m + w + c;
        return total > 0 ? total : 1;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getPlaceName() { return placeName; }
    public void setPlaceName(String placeName) { this.placeName = placeName; }

    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }

    public Integer getMen() { return men; }
    public void setMen(Integer men) { this.men = men; }

    public Integer getWomen() { return women; }
    public void setWomen(Integer women) { this.women = women; }

    public Integer getChildren() { return children; }
    public void setChildren(Integer children) { this.children = children; }

    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
