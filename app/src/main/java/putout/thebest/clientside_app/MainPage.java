package putout.thebest.clientside_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user1 on 2016/12/12.
 */

public class MainPage extends AppCompatActivity {

    TextView Deliver,Return,ExchangeMiss;
    ListView listView ;
    String sig,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        Intent intent=getIntent();
        sig = intent.getStringExtra("sig");
        token = intent.getStringExtra("token");

    Deliver = (TextView) findViewById(R.id.Deliver);
    Return = (TextView) findViewById(R.id.Return);
    ExchangeMiss = (TextView) findViewById(R.id.ExchangeMiss);
    listView = (ListView) findViewById(R.id.item_list);

    getItemDetail("prDeliver");  //起始頁

        Deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deliver.setTextColor(Color.parseColor("#669900"));
                Return.setTextColor(Color.parseColor("#303f9f"));
                ExchangeMiss.setTextColor(Color.parseColor("#303f9f"));
                getItemDetail("prDeliver");
            }
        });
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deliver.setTextColor(Color.parseColor("#303f9f"));
                Return.setTextColor(Color.parseColor("#669900"));
                ExchangeMiss.setTextColor(Color.parseColor("#303f9f"));
                getItemDetail("prReturn");
            }
        });
        ExchangeMiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deliver.setTextColor(Color.parseColor("#303f9f"));
                Return.setTextColor(Color.parseColor("#303f9f"));
                ExchangeMiss.setTextColor(Color.parseColor("#669900"));
                getItemDetail("prExchangeMiss");
            }
        });



    }

    void getItemDetail(final String func){

        final HttpConnect example = new HttpConnect();
        final String[] response = {null};

        final Handler handler = new Handler();
        final Thread  workerThread;

        workerThread = new Thread(new Runnable() {
            public void run() {
                final ArrayList<HashMap<String, String>> item_list;
                //////////放入要執行Thread/////////
                try {
                    response[0] = example.run("https://openapi.devel.crazymike.tw/?func="+func+"&sig="+sig+"&token="+token);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                item_list = BegintoDeserializ(response[0]);
                handler.post(new Runnable() {
                    public void run() {
                        //////////放入要執行的Handler/////////
                        ListAdapter adapter = new SimpleAdapter(
                                MainPage.this,
                                item_list,
                                R.layout.item_detail,
                                new String[] { "item_id", "num","name","cnt"},
                                new int[] { R.id.item_id, R.id.num,R.id.name,R.id.cnt });

                        listView.setAdapter(adapter);
                    }
                });
            }

        });
        workerThread.start();
    }

    ArrayList<HashMap<String, String>> BegintoDeserializ(String response) {
        String str;
        ArrayList<HashMap<String, String>> item_list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject obj = new JSONObject(response);
            obj = obj.getJSONObject("rtn");
            JSONArray array = obj.getJSONArray("result");
            for (int i = 1; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);

                HashMap<String, String> map = new HashMap<String, String>();

                map.put("item_id", "商品編號 " + item.getString("item_id"));
                map.put("num", item.getString("num"));
                map.put("name", item.getString("name"));
                map.put("cnt", item.getString("cnt") + "筆");

                item_list.add(map);   // adding HashList to ArrayList
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item_list;
    }

}