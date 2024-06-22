package pl.edu.pjatk.trainmate.keycloakIntegration;

import static pl.edu.pjatk.trainmate.utils.Const.CLIENT_ID;
import static pl.edu.pjatk.trainmate.utils.Const.DEFAULT_STRING_VALUE;
import static pl.edu.pjatk.trainmate.utils.Const.PREFS_NAME;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_REFRESH_TOKEN;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_TAG;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_TOKEN_FAIL;
import static pl.edu.pjatk.trainmate.utils.Const.TOKEN_REFRESH_GRANT_TYPE;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import pl.edu.pjatk.trainmate.utils.Const;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Service for periodically refreshing the access token using a refresh token.
 */
public class TokenRefreshService extends Service {

    /**
     * Called when the service is started. Initializes and starts a new thread
     * that periodically refreshes the token.
     *
     * @param intent The Intent supplied to startService(Intent).
     * @param flags Additional data about the start request.
     * @param startId A unique integer representing this specific request to start.
     * @return The return value indicates what semantics the system should use for the service's current started state.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    // Periodically refresh the token every 840000 milliseconds (14 minutes)
                    while (true) {
                        try {
                            Thread.sleep(840000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        refresh();
                    }
                }
            }
        ).start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Refreshes the access token using the stored refresh token.
     */
    private void refresh() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String refreshToken = settings.getString(PREF_REFRESH_TOKEN, DEFAULT_STRING_VALUE);

        TokenProviderClient client = RetrofitClient.getRetrofitInstance().create(TokenProviderClient.class);

        Call<AccessToken> call = client.refreshToken(CLIENT_ID, TOKEN_REFRESH_GRANT_TYPE, refreshToken);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    SharedPreferences settings = getSharedPreferences(Const.PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(Const.PREF_ACCESS_TOKEN, response.body().getAccessToken());
                    editor.putString(Const.PREF_REFRESH_TOKEN, response.body().getRefreshToken());
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable throwable) {
                Log.e(REFRESH_TAG, REFRESH_TOKEN_FAIL + throwable.getMessage());
            }
        });
    }

    /**
     * Called when a client binds to the service with bindService(Intent).
     *
     * @param intent The Intent that was used to bind to this service.
     * @return An IBinder through which clients can call on to the service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
