package pl.edu.pjatk.trainmate.api;

import static pl.edu.pjatk.trainmate.utils.Const.LOCAL_API_URL;

import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for creating and managing a Retrofit instance.
 * Configures a Retrofit client with custom Gson settings for JSON conversion.
 */
public class RetrofitApiClient {

    /**
     * The Retrofit instance.
     */
    private static Retrofit retrofit;

    /**
     * Returns the singleton Retrofit instance.
     * If the instance is null, it initializes it with custom Gson settings and the base URL.
     *
     * @return the singleton Retrofit instance.
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            var gson = new GsonBuilder()
                .setLenient()
                .disableHtmlEscaping()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .create();

            retrofit = new Retrofit.Builder()
                .baseUrl(LOCAL_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        }

        return retrofit;
    }
}
