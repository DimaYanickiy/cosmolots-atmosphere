package cos.mol.ott;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cosmos.lot.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {
    @BindView(R.id.i1)ImageView i1;
    @BindView(R.id.i2)ImageView i2;
    @BindView(R.id.i3)ImageView i3;
    @BindView(R.id.i4)ImageView i4;
    @BindView(R.id.i5)ImageView i5;
    @BindView(R.id.i6)ImageView i6;
    @BindView(R.id.i7)ImageView i7;
    @BindView(R.id.i8)ImageView i8;
    @BindView(R.id.i9)ImageView i9;
    @BindView(R.id.i10)ImageView i10;
    @BindView(R.id.i11)ImageView i11;
    @BindView(R.id.i12)ImageView i12;
    @BindView(R.id.i13)ImageView i13;
    @BindView(R.id.i14)ImageView i14;
    @BindView(R.id.i15)ImageView i15;

    @BindView(R.id.coins)TextView coins;

    @BindView(R.id.btn_spin)ImageButton btn_spin;
    @BindView(R.id.btn_auto_spin)ImageButton btn_auto_spin;
    @BindView(R.id.btn_stop)ImageButton btn_stop;
    @BindView(R.id.btn_go_back)ImageButton btn_go_back;

    private FastSaver fs;
    private Random r;

    private int balance;
    private int coinsPerSpin = 10;
    private boolean running;
    private boolean autoSpin;
    private int seconds = 0;

    private int in1,in2,in3,in4,in5,in6,in7,in8,in9,in10,in11,in12,in13,in14,in15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        fs = new FastSaver(this);
        balance = fs.getPoints();
        coins.setText(Integer.toString(balance));
        r = new Random();

        btn_spin.setOnClickListener(v -> {
            if(!running){
                balance-=coinsPerSpin;
                coins.setText(Integer.toString(balance));
                running = true;
            }
        });
        btn_auto_spin.setOnClickListener(v -> {
            if(!running){
                autoSpin = true;
            }
        });
        btn_stop.setOnClickListener(v -> {
            autoSpin = false;
        });
        btn_go_back.setOnClickListener(v -> {
            if(!running){
                fs.setPoints(balance);
                finish();
            }
        });

        startLoop();
    }

    private int genIn(){
        return Math.abs(r.nextInt()%7) + 1;
    }

    private void setIndex(ImageView index, int in){
        switch (in){
            case 1:
                index.setImageDrawable(getResources().getDrawable(R.drawable.item_1));
                break;
            case 2:
                index.setImageDrawable(getResources().getDrawable(R.drawable.item_2));
                break;
            case 3:
                index.setImageDrawable(getResources().getDrawable(R.drawable.item_3));
                break;
            case 4:
                index.setImageDrawable(getResources().getDrawable(R.drawable.item_4));
                break;
            case 5:
                index.setImageDrawable(getResources().getDrawable(R.drawable.item_5));
                break;
            case 6:
                index.setImageDrawable(getResources().getDrawable(R.drawable.item_6));
                break;
            case 7:
                index.setImageDrawable(getResources().getDrawable(R.drawable.item_7));
                break;
        }
    }

    private void circle(){
        if(seconds < 20){
            in1 = genIn();
            in2 = genIn();
            in3 = genIn();
            setIndex(i1, in1);
            setIndex(i2, in2);
            setIndex(i3, in3);
        }
        if(seconds < 30){
            in4 = genIn();
            in5 = genIn();
            in6 = genIn();
            setIndex(i4, in4);
            setIndex(i5, in5);
            setIndex(i6, in6);
        }
        if(seconds < 40){
            in7 = genIn();
            in8 = genIn();
            in9 = genIn();
            setIndex(i7, in7);
            setIndex(i8, in8);
            setIndex(i9, in9);
        }
        if(seconds < 50){
            in10 = genIn();
            in11 = genIn();
            in12 = genIn();
            setIndex(i10, in10);
            setIndex(i11, in11);
            setIndex(i12, in12);
        }
        if(seconds < 60){
            in13 = genIn();
            in14 = genIn();
            in15 = genIn();
            setIndex(i13, in13);
            setIndex(i14, in14);
            setIndex(i15, in15);
        }
        if(seconds >= 60){
            running = false;
            seconds = 0;
            checkCircle();
        }
        if(seconds == 0 && autoSpin){
            balance-=coinsPerSpin;
            coins.setText(Integer.toString(balance));
        }
    }

    private void checkCircle() {
        if((in2 == in5 && in5 == in8 && in8 == in11 && in11 == in14)
                || (in2 == in5 && in5 == in8 && in8 == in11 && in11 == in13)
                || (in2 == in5 && in5 == in8 && in8 == in11 && in11 == in15)
                || (in1 == in5 && in5 == in9 && in9 == in11 && in11 == in13)
                || (in3 == in5 && in5 == in7 && in7 == in11 && in11 == in15)
                || (in1 == in5 && in5 == in8 && in8 == in11 && in11 == in14)
                || (in3 == in5 && in5 == in8 && in8 == in11 && in11 == in14)){
            balance+=coinsPerSpin*10;
        }
        if((in2 == in5 && in5 == in8 && in8 == in11)
                || (in2 == in5 && in5 == in8 && in8 == in12)
                || (in2 == in5 && in5 == in8 && in8 == in10)
                || (in1 == in5 && in5 == in8 && in8 == in11)
                || (in3 == in5 && in5 == in8 && in8 == in11)){
            balance+=coinsPerSpin*5;
        }
        if((in2 == in5 && in5 == in8)
                || (in1 == in5 && in5 == in7)
                || (in3 == in5 && in5 == in9)){
            balance+=coinsPerSpin*3;
        }
        if(in2 == in5 ||
                in1 == in5 ||
                in3 == in5){
            balance+=coinsPerSpin*2;
        }
        coins.setText(Integer.toString(balance));
    }

    public void startLoop(){
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                if(running){
                    seconds++;
                    circle();
                }
                if(autoSpin){
                    seconds++;
                    circle();
                }
                h.postDelayed(this,  10);
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Exit")
                .setMessage("Do you really want to exit the application?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (arg0, arg1) -> System.exit(0))
                .create()
                .show();
    }
}