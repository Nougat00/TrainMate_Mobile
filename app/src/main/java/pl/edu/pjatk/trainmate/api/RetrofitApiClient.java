package pl.edu.pjatk.trainmate.api;

import static pl.edu.pjatk.trainmate.utils.Const.LOCAL_API_URL;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            var gson = new GsonBuilder()
                    .setLenient()
                    .disableHtmlEscaping()
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .enableComplexMapKeySerialization()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(LOCAL_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
