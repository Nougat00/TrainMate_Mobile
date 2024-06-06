package pl.edu.pjatk.trainmate.keycloakIntegration;

import static pl.edu.pjatk.trainmate.utils.Const.API_FAIL;
import static pl.edu.pjatk.trainmate.utils.Const.API_TAG;
import static pl.edu.pjatk.trainmate.utils.Const.CLIENT_ID;
import static pl.edu.pjatk.trainmate.utils.Const.DEFAULT_STRING_VALUE;
import static pl.edu.pjatk.trainmate.utils.Const.PREFS_NAME;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_REFRESH_TOKEN;
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

public class TokenRefreshService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(
            new Runnable() {
                @Override
                public void run() {
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

    private void refresh() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        var refreshToken = settings.getString(PREF_REFRESH_TOKEN, DEFAULT_STRING_VALUE);

        TokenProviderClient client = RetrofitClient.getRetrofitInstance().create(TokenProviderClient.class);

        Call<AccessToken> call = client.refreshToken(CLIENT_ID, TOKEN_REFRESH_GRANT_TYPE, refreshToken);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    SharedPreferences settings = getSharedPreferences(Const.PREFS_NAME,
                        Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(Const.PREF_ACCESS_TOKEN, response.body().getAccessToken());
                    editor.putString(Const.PREF_REFRESH_TOKEN, response.body().getRefreshToken());
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable throwable) {
                Log.w(API_TAG, API_FAIL);
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
