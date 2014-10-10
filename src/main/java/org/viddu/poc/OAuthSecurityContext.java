package org.viddu.poc;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class OAuthSecurityContext implements SecurityContext {
    private final UserInfo userDetails;

    public Principal getUserPrincipal() {
        return new Principal() {

            public String getName() {
                return userDetails.getFirstName() + ", " + userDetails.getLastName();
            }
        };
    }

    public boolean isUserInRole(String role) {
        return userDetails.getRole().equalsIgnoreCase(role);
    }

    public boolean isSecure() {
        // TODO Auto-generated method stub
        return false;
    }

    public String getAuthenticationScheme() {
        // TODO Auto-generated method stub
        return null;
    }

    public OAuthSecurityContext(UserInfo userDetails) {
        this.userDetails = userDetails;
    }

}
