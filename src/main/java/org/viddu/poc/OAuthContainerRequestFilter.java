package org.viddu.poc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;

@Priority(Priorities.AUTHENTICATION)
public class OAuthContainerRequestFilter implements ContainerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(OAuthContainerRequestFilter.class);
    private static final Token EMPTY_TOKEN = null;
    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";

    @Inject
    private Config config;

    @Inject
    private ObjectMapper mapper;

    public OAuthContainerRequestFilter(Config config, ObjectMapper mapper) {
        this.config = config;
        this.mapper = mapper;
    }

    public OAuthContainerRequestFilter() {
        // TODO Auto-generated constructor stub
    }

    private static final String USER_COOKIE = "USER_COOKIE";

    public void filter(ContainerRequestContext requestContext) throws IOException {
        Map<String, Cookie> cookieMap = requestContext.getCookies();
        Cookie userCookie = cookieMap.get(USER_COOKIE);
        UriInfo uriInfo = requestContext.getUriInfo();
        String currentPath = uriInfo.getRequestUri().toString();

        logger.debug("Checking uri:{}", currentPath);
        if (userCookie == null) {
            // No user cookie, begin auth process.
            String apiKey = config.getString("facebook-app-id"); //Read api key from application.conf or env variable.
            //Read api secret from application.conf or env variable. Never put prod secret keys in application.conf
            String apiSecret = config.getString("facebook-app-secret");
            OAuthService service = new ServiceBuilder().provider(FacebookApi.class).apiKey(apiKey).apiSecret(apiSecret)
                    .callback(currentPath).build();

            final MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            String code = queryParams.getFirst("code");
            if (code == null || code.isEmpty()) {
                // Initiate Facebook Auth workflow.
                String fbAuthUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
                try {
                    requestContext.abortWith(Response.seeOther(new URI(fbAuthUrl)).build());
                } catch (URISyntaxException e) {
                    logger.error("Bad facebook url:{}", e);
                }
            } else {
                // User logged in, exchange code for access token.
                Verifier verifier = new Verifier(code);
                Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
                OAuthRequest fbRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
                service.signRequest(accessToken, fbRequest);
                org.scribe.model.Response fbResponse = fbRequest.send();
                if (fbResponse.isSuccessful()) {
                    String jsonUserInfo = fbResponse.getBody();
                    logger.debug("User Details={}", jsonUserInfo);
                    UserInfo userInfo = mapper.readValue(jsonUserInfo, UserInfo.class);
                    //Hardcoded here, but lookup the role from elsewhere.
                    userInfo.setRole("LOGGED_IN");
                    // Set SecurityContext.
                    OAuthSecurityContext fbSecurityContext = new OAuthSecurityContext(userInfo);
                    requestContext.setSecurityContext(fbSecurityContext);
                    NewCookie newUserCookie = new NewCookie(USER_COOKIE, URLEncoder.encode(jsonUserInfo,"UTF-8"));
                    requestContext.abortWith(Response.seeOther(uriInfo.getRequestUri()).cookie(newUserCookie).build());
                } else {
                    requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
                }
            }

        } else {
            // User logged in, proceed to authorization.
            String jsonUserInfo = URLDecoder.decode(userCookie.getValue(), "UTF-8");
            UserInfo userInfo = mapper.readValue(jsonUserInfo, UserInfo.class);
            //Hardcoded here, but lookup the role from elsewhere.
            userInfo.setRole("LOGGED_IN");
            OAuthSecurityContext fbSecurityContext = new OAuthSecurityContext(userInfo);
            requestContext.setSecurityContext(fbSecurityContext);
            return;
        }
    }

}
