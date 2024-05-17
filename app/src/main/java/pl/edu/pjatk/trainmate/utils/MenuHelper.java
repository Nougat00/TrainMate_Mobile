package pl.edu.pjatk.trainmate.utils;

import static pl.edu.pjatk.trainmate.utils.Const.CLIENT_ID;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_ACTIVE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.navigation.NavigationView;
import pl.edu.pjatk.trainmate.AccessToken;
import pl.edu.pjatk.trainmate.LoginActivity;
import pl.edu.pjatk.trainmate.MainActivity;
import pl.edu.pjatk.trainmate.R;
import pl.edu.pjatk.trainmate.RetrofitClient;
import pl.edu.pjatk.trainmate.TokenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuHelper extends AppCompatActivity {

    public interface OnMenuItemClickListener {
        void onMenuItemClick(MenuItem item);
    }

    public static void setupNavigationMenu(NavigationView navigationView, final Context context, final OnMenuItemClickListener listener) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (listener != null) {
                    listener.onMenuItemClick(item);
                }
                return true;
            }
        });
    }

    public static void handleMenuItem(MenuItem item, Context context) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            // Do something for home
        } else if (itemId == R.id.nav_plan) {
            // Do something for plan
        } else if (itemId == R.id.nav_report) {
            // Do something for report
        } else if (itemId == R.id.nav_logout) {
            logout(context);
        } else {
        }
    }


    private static void logout(Context context) {
        TokenService service = RetrofitClient.getRetrofitInstance().create(TokenService.class);

        Call<AccessToken> call = service.logout(CLIENT_ID, Const.REFRESH_TOKEN);
        SharedPreferences settings = context.getSharedPreferences(Const.PREFS_NAME,
            Context.MODE_PRIVATE);
        settings.edit().putBoolean(REFRESH_ACTIVE, false).commit();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                context.startActivity(new Intent(context, LoginActivity.class));
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable throwable) {
                Toast.makeText(context, "Error: Logout problem", Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void setupNavigationMenu(NavigationView navigationView) {
        MenuHelper.setupNavigationMenu(navigationView, navigationView.getContext(), new MenuHelper.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(MenuItem item) {
                MenuHelper.handleMenuItem(item, navigationView.getContext());
            }
        });
    }
}
