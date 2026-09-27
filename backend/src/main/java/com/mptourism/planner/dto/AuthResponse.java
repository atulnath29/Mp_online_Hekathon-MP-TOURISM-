package com.mptourism.planner.dto;

public class AuthResponse {
    private String token;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String whatsapp;
    private String profileImage;
    private String message;

    public AuthResponse() {}

    public AuthResponse(String token, Long id, String firstName, String lastName, String email, String whatsapp, String profileImage, String message) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.whatsapp = whatsapp;
        this.profileImage = profileImage;
        this.message = message;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getWhatsapp() { return whatsapp; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
