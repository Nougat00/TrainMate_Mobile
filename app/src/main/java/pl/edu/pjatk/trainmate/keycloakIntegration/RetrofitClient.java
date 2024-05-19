package pl.edu.pjatk.trainmate.keycloakIntegration;

import static pl.edu.pjatk.trainmate.utils.Const.LOCAL_KEYCLOAK_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

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
