package pl.edu.pjatk.trainmate.keycloakIntegration;

import static pl.edu.pjatk.trainmate.utils.Const.LOCAL_KEYCLOAK_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for creating and managing a Retrofit instance for Keycloak integration.
 * Configures a Retrofit client with the base URL for the Keycloak server.
 */
public class RetrofitClient {

    /**
     * The Retrofit instance.
     */
    private static Retrofit retrofit;

    /**
     * Returns the singleton Retrofit instance for Keycloak integration.
     * If the instance is null, it initializes it with the base URL and Gson converter factory.
     *
     * @return the singleton Retrofit instance.
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(LOCAL_KEYCLOAK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }

        return retrofit;
    }
}
