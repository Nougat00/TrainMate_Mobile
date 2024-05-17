package pl.edu.pjatk.trainmate;

import static pl.edu.pjatk.trainmate.utils.Const.CLIENT_ID;
import static pl.edu.pjatk.trainmate.utils.Const.LOGIN_FAIL_ANNOUNCEMENT;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_ACTIVE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pl.edu.pjatk.trainmate.utils.Const;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView announcementTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);
        announcementTextView = findViewById(R.id.announcement);

        btnLogin.setOnClickListener(v -> getAccessToken());
    }

    public void getAccessToken() {
        TokenService service = RetrofitClient.getRetrofitInstance().create(TokenService.class);

        String password = etPassword.getText().toString();
        String username = etUsername.getText().toString();

        Call<AccessToken> call = service.getAccessToken(CLIENT_ID, "password", "openid", username, password);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    Const.ACCESS_TOKEN = response.body().getAccessToken();
                    Const.REFRESH_TOKEN = response.body().getRefreshToken();
                    SharedPreferences settings = getSharedPreferences(Const.PREFS_NAME,
                        Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(Const.PREF_UNAME, username);
                    editor.putString(Const.PREF_PASSWORD, password);
                    editor.putString(Const.PREF_ACCESS_TOKEN, response.body().getAccessToken());
                    editor.putString(Const.PREF_REFRESH_TOKEN, response.body().getRefreshToken());
                    editor.putBoolean(REFRESH_ACTIVE, true);
                    editor.commit();

                    Intent serviceIntent = new Intent(LoginActivity.this, TokenRefreshService.class);
                    startService(serviceIntent);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                } else {
                    announcementTextView.setText(LOGIN_FAIL_ANNOUNCEMENT);
                    announcementTextView.setTextColor(Color.RED);
                    Toast.makeText(LoginActivity.this, "Error:", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Error:" + throwable, Toast.LENGTH_LONG).show();
            }
        });
    }
}
