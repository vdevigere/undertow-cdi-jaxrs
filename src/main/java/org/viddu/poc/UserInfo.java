package org.viddu.poc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private final String id;
    private final String email;
    private final String firstName;
    private final String gender;
    private final String lastName;
    private final String link;
    private String role;

    public UserInfo(@JsonProperty("id") String id, @JsonProperty("email") String email,
            @JsonProperty("first_name") String firstName, @JsonProperty("gender") String gender,
            @JsonProperty("last_name") String lastName, @JsonProperty("link") String link) {
        super();
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.gender = gender;
        this.lastName = lastName;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getLink() {
        return link;
    }

    public String getRole() {
        // TODO Auto-generated method stub
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
