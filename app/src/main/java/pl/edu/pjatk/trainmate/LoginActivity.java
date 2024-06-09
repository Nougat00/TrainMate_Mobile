package pl.edu.pjatk.trainmate;

import static pl.edu.pjatk.trainmate.utils.Const.API_FAIL;
import static pl.edu.pjatk.trainmate.utils.Const.API_TAG;
import static pl.edu.pjatk.trainmate.utils.Const.CLIENT_ID;
import static pl.edu.pjatk.trainmate.utils.Const.GRANT_TYPE;
import static pl.edu.pjatk.trainmate.utils.Const.LOGIN_FAIL_ANNOUNCEMENT;
import static pl.edu.pjatk.trainmate.utils.Const.PREFS_NAME;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_ACCESS_TOKEN;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_PASSWORD;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_REFRESH_TOKEN;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_UNAME;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_ACTIVE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import pl.edu.pjatk.trainmate.keycloakIntegration.AccessToken;
import pl.edu.pjatk.trainmate.keycloakIntegration.RetrofitClient;
import pl.edu.pjatk.trainmate.keycloakIntegration.TokenProviderClient;
import pl.edu.pjatk.trainmate.keycloakIntegration.TokenRefreshService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private TextView announcementTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        Button btnLogin = findViewById(R.id.btnLogin);
        announcementTextView = findViewById(R.id.announcement);

        btnLogin.setOnClickListener(v -> getAccessToken());
    }

    public void getAccessToken() {
        TokenProviderClient service = RetrofitClient.getRetrofitInstance().create(TokenProviderClient.class);

        String password = etPassword.getText().toString();
        String username = etUsername.getText().toString();

        Call<AccessToken> call = service.getAccessToken(GRANT_TYPE, username, password, CLIENT_ID);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(PREF_UNAME, username);
                    editor.putString(PREF_PASSWORD, password);
                    editor.putString(PREF_ACCESS_TOKEN, response.body().getAccessToken());
                    editor.putString(PREF_REFRESH_TOKEN, response.body().getRefreshToken());
                    editor.putBoolean(REFRESH_ACTIVE, true);
                    editor.commit();

                    Intent serviceIntent = new Intent(LoginActivity.this, TokenRefreshService.class);
                    startService(serviceIntent);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                } else {
                    announcementTextView.setText(LOGIN_FAIL_ANNOUNCEMENT);
                    announcementTextView.setTextColor(Color.RED);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable throwable) {
                Log.e(API_TAG, API_FAIL + throwable.getMessage());
            }
        });
    }
}
