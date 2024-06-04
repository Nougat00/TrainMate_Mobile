package pl.edu.pjatk.trainmate.keycloakIntegration;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TokenProviderClient {

    @FormUrlEncoded
    @POST("/realms/trainmate/protocol/openid-connect/token")
    Call<AccessToken> getAccessToken(
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("password") String password,
            @Field("client_id") String client_id
    );

    @FormUrlEncoded
    @POST("/realms/trainmate/protocol/openid-connect/token")
    Call<AccessToken> refreshToken(
            @Field("client_id") String client_id,
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token
    );

    @FormUrlEncoded
    @POST("/realms/trainmate/protocol/openid-connect/logout")
    Call<AccessToken> logout(
            @Field("client_id") String client_id,
            @Field("refresh_token") String refresh_token
    );
}
