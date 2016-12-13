package putout.thebest.clientside_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user1 on 2016/12/12.
 */

public class MainPage extends AppCompatActivity {

TextView Deliver,Return,ExchangeMiss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

    Deliver = (TextView) findViewById(R.id.Deliver);
    Return = (TextView) findViewById(R.id.Return);
    ExchangeMiss = (TextView) findViewById(R.id.ExchangeMiss);



        Deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deliver.setTextColor(Color.parseColor("#669900"));
                Return.setTextColor(Color.parseColor("#303f9f"));
                ExchangeMiss.setTextColor(Color.parseColor("#303f9f"));
            }
        });
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deliver.setTextColor(Color.parseColor("#303f9f"));
                Return.setTextColor(Color.parseColor("#669900"));
                ExchangeMiss.setTextColor(Color.parseColor("#303f9f"));
            }
        });
        ExchangeMiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deliver.setTextColor(Color.parseColor("#303f9f"));
                Return.setTextColor(Color.parseColor("#303f9f"));
                ExchangeMiss.setTextColor(Color.parseColor("#669900"));
            }
        });

    }
}