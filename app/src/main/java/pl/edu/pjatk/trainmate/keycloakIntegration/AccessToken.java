package pl.edu.pjatk.trainmate.keycloakIntegration;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Represents an access token received from Keycloak.
 * Contains the access token, expiration time, and refresh token.
 */
public class AccessToken {

    @Getter
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("expires_in")
    private Integer expiresIn;
    @Getter
    @SerializedName("refresh_token")
    private String refreshToken;

    /**
     * Constructs a new AccessToken with the specified access token and expiration time.
     *
     * @param accessToken the access token string.
     * @param expiresIn the time in seconds until the access token expires.
     */
    public AccessToken(String accessToken, Integer expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
