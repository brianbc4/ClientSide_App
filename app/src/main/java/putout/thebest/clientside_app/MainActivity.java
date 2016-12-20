package putout.thebest.clientside_app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    EditText user, pass , id;
    ImageView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        id = (EditText) findViewById(R.id.id);

        login = (ImageView) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginVerify(user.getText().toString(),pass.getText().toString(),id.getText().toString());

            }
        });

    }
     void loginVerify(final String user, final String pass, final String id) {

         final HttpConnect connect = new HttpConnect();

         final Handler handler = new Handler();
         final Thread  workerThread;
         workerThread = new Thread(new Runnable() {
             public void run() {
                 //////////放入要執行Thread/////////
                 try {
                     String json_str = connect.run("https://openapi.devel.crazymike.tw/?func=prLogin&id="+id+"&devicetoken=deviceToken!&deviceType=android&user="+user+"&&pwd="+pass);

                     final JSONObject response = new JSONObject(json_str);

                     if(response.getString("result").equals("true")){
                         String sig,token;

                         sig = response.getJSONObject("rtn").getString("sig");
                         token = response.getJSONObject("rtn").getString("token");

                         final Intent intent=new Intent();            //宣告Intent物件
                         intent.setClass(MainActivity.this, MainPage.class);
                         intent.putExtra("sig",sig);
                         intent.putExtra("token",token);
                         MainActivity.this.startActivity(intent);

                     }else{
                         handler.post(new Runnable() {
                             public void run() {
                                 //////////放入要執行的Handler/////////
                                 try {
                                     String[] tokens = response.getString("msg").split("\\[1\\]");
                                     Toast.makeText(MainActivity.this,tokens[1],Toast.LENGTH_SHORT).show();
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }
                         });
                     }

                 } catch (IOException e) {
                     e.printStackTrace();
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }



             }

         });
         workerThread.start();


    }




}
