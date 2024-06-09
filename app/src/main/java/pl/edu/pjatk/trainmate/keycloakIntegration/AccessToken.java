package pl.edu.pjatk.trainmate.keycloakIntegration;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public class AccessToken {

    @Getter
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("expires_in")
    private Integer expiresIn;
    @Getter
    @SerializedName("refresh_token")
    private String refreshToken;

    public AccessToken(String accessToken, Integer expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

}
