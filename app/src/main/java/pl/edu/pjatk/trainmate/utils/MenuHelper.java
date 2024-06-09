package pl.edu.pjatk.trainmate.utils;

import static pl.edu.pjatk.trainmate.utils.Const.CLIENT_ID;
import static pl.edu.pjatk.trainmate.utils.Const.DEFAULT_STRING_VALUE;
import static pl.edu.pjatk.trainmate.utils.Const.PREF_REFRESH_TOKEN;
import static pl.edu.pjatk.trainmate.utils.Const.REFRESH_ACTIVE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.navigation.NavigationView;
import pl.edu.pjatk.trainmate.LoginActivity;
import pl.edu.pjatk.trainmate.R;
import pl.edu.pjatk.trainmate.keycloakIntegration.AccessToken;
import pl.edu.pjatk.trainmate.keycloakIntegration.RetrofitClient;
import pl.edu.pjatk.trainmate.keycloakIntegration.TokenProviderClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuHelper extends AppCompatActivity {

    public interface OnMenuItemClickListener {

        void onMenuItemClick(MenuItem item);
    }

    public static void setupNavigationMenu(NavigationView navigationView, final OnMenuItemClickListener listener) {
        navigationView.setNavigationItemSelectedListener(item -> {
            if (listener != null) {
                listener.onMenuItemClick(item);
            }
            return true;
        });
    }

    public static void handleMenuItem(NavigationView navigationView, Context context, DrawerLayout drawerLayout) {
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_plan) {
                openFragment((FragmentActivity) navigationView.getContext(), R.id.nav_plan);
            } else if (itemId == R.id.nav_progress) {
                openFragment((FragmentActivity) navigationView.getContext(), R.id.nav_progress);
            } else if (itemId == R.id.nav_logout) {
                logout(context);
            } else {
                return false;
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }


    private static void logout(Context context) {
        TokenProviderClient service = RetrofitClient.getRetrofitInstance().create(TokenProviderClient.class);

        SharedPreferences settings = context.getSharedPreferences(Const.PREFS_NAME,
            Context.MODE_PRIVATE);
        Call<AccessToken> call = service.logout(CLIENT_ID, settings.getString(PREF_REFRESH_TOKEN, DEFAULT_STRING_VALUE));
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

    public static void setupNavigationMenu(NavigationView navigationView, DrawerLayout drawerLayout) {
        MenuHelper.setupNavigationMenu(navigationView, item -> MenuHelper.handleMenuItem(navigationView, navigationView.getContext(), drawerLayout));
    }

    private static void openFragment(FragmentActivity fragment, int navFragmentId) {
        NavController navController = Navigation.findNavController(fragment, R.id.nav_host_fragment_content_main);
        navController.popBackStack(navFragmentId, true);
        navController.navigate(navFragmentId);

    }
}
