package pl.edu.pjatk.trainmate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> getAccessToken());
    }

    public void getAccessToken() {
        TokenService service = RetrofitClient.getRetrofitInstance().create(TokenService.class);

        String password = etPassword.getText().toString();
        String username = etUsername.getText().toString();

        Call<AccessToken> call = service.getAccessToken("train-mate", "password", "openid", username, password);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    Constants.ACCESS_TOKEN = response.body().getAccessToken();
                    Constants.REFRESH_TOKEN = response.body().getRefreshToken();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                } else {
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
