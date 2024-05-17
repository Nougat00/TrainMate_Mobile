package pl.edu.pjatk.trainmate;

import static pl.edu.pjatk.trainmate.utils.Const.CLIENT_ID;
import static pl.edu.pjatk.trainmate.utils.Const.PREFS_NAME;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_REFRESH_TOKEN;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_ACTIVE;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_TAG;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_TOKEN_FAIL;
import static pl.edu.pjatk.trainmate.utils.Const.TOKEN_REFRESH_GRANT_TYPE;
import static pl.edu.pjatk.trainmate.utils.Const.defaultRefreshTokenValue;

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
        var refreshToken = settings.getString(PREF_REFRESH_TOKEN, defaultRefreshTokenValue);

        TokenService service = RetrofitClient.getRetrofitInstance().create(TokenService.class);

        Call<AccessToken> call = service.refreshToken(CLIENT_ID, TOKEN_REFRESH_GRANT_TYPE, refreshToken);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    Const.ACCESS_TOKEN = response.body().getAccessToken();
                    Const.REFRESH_TOKEN = response.body().getRefreshToken();
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
                Log.w(REFRESH_TAG, REFRESH_TOKEN_FAIL);
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}