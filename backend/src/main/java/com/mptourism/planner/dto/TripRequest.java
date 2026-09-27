package com.mptourism.planner.dto;

import java.util.List;

public class TripRequest {
    private String firstName;
    private String lastName;
    private Double budget;
    private Integer men;
    private Integer women;
    private Integer children;
    private Integer days;
    private String placeName;
    private List<String> selectedPlaces;

    public TripRequest() {}

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

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

    public String getPlaceName() { return placeName; }
    public void setPlaceName(String placeName) { this.placeName = placeName; }

    public List<String> getSelectedPlaces() { return selectedPlaces; }
    public void setSelectedPlaces(List<String> selectedPlaces) { this.selectedPlaces = selectedPlaces; }
}
