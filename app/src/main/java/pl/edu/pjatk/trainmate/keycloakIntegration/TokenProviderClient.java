package pl.edu.pjatk.trainmate.keycloakIntegration;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Interface for accessing Keycloak's token endpoints using Retrofit.
 */
public interface TokenProviderClient {

    /**
     * Retrieves an access token from Keycloak.
     *
     * @param grant_type The type of grant being used (e.g., "password").
     * @param username The username of the user.
     * @param password The password of the user.
     * @param client_id The client ID registered in Keycloak.
     * @return A Call object that can be used to request an access token.
     */
    @FormUrlEncoded
    @POST("/realms/trainmate/protocol/openid-connect/token")
    Call<AccessToken> getAccessToken(
        @Field("grant_type") String grant_type,
        @Field("username") String username,
        @Field("password") String password,
        @Field("client_id") String client_id
    );

    /**
     * Refreshes an access token using a refresh token.
     *
     * @param client_id The client ID registered in Keycloak.
     * @param grant_type The type of grant being used (e.g., "refresh_token").
     * @param refresh_token The refresh token issued by Keycloak.
     * @return A Call object that can be used to request a new access token.
     */
    @FormUrlEncoded
    @POST("/realms/trainmate/protocol/openid-connect/token")
    Call<AccessToken> refreshToken(
        @Field("client_id") String client_id,
        @Field("grant_type") String grant_type,
        @Field("refresh_token") String refresh_token
    );

    /**
     * Logs out a user by invalidating the refresh token.
     *
     * @param client_id The client ID registered in Keycloak.
     * @param refresh_token The refresh token issued by Keycloak.
     * @return A Call object that can be used to log out the user.
     */
    @FormUrlEncoded
    @POST("/realms/trainmate/protocol/openid-connect/logout")
    Call<AccessToken> logout(
        @Field("client_id") String client_id,
        @Field("refresh_token") String refresh_token
    );
}
