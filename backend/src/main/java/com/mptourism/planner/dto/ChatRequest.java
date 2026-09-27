package com.mptourism.planner.dto;

public class ChatRequest {
    private String message;
    private String currentPage;
    private String selectedPlace;
    private Double budget;
    private Integer travelers;
    private Integer days;

    public ChatRequest() {}

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCurrentPage() { return currentPage; }
    public void setCurrentPage(String currentPage) { this.currentPage = currentPage; }

    public String getSelectedPlace() { return selectedPlace; }
    public void setSelectedPlace(String selectedPlace) { this.selectedPlace = selectedPlace; }

    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }

    public Integer getTravelers() { return travelers; }
    public void setTravelers(Integer travelers) { this.travelers = travelers; }

    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }
}
