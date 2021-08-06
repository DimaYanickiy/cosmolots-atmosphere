package cos.mol.ott;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.cosmos.lot.R;

public class MenuActivity extends AppCompatActivity {

    Button btn_start, btn_play_market, btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_start = findViewById(R.id.button2);
        btn_play_market = findViewById(R.id.button3);
        btn_exit = findViewById(R.id.button4);
        btn_start.setBackground(getDrawable(R.drawable.button));
        btn_play_market.setBackground(getDrawable(R.drawable.button));
        btn_exit.setBackground(getDrawable(R.drawable.button));

        btn_start.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, GameActivity.class));
        });
        btn_play_market.setOnClickListener(v -> {
            try {
                String marketStr = "market://details?id=" + getPackageName();
                Intent market = new Intent(Intent.ACTION_VIEW, Uri.parse(marketStr));
                startActivity(market);
            } catch (ActivityNotFoundException e) {
                String marketStr = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Uri.parse(marketStr);
            }
        });
        btn_exit.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Exit game")
                    .setMessage("Do you really want to exit the game?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (arg0, arg1) -> System.exit(0))
                    .create()
                    .show();
        });
    }
}