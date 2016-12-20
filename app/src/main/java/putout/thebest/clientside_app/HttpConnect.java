package putout.thebest.clientside_app;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user1 on 2016/12/13.
 */

public class HttpConnect {
    OkHttpClient client = new OkHttpClient.Builder()
            .hostnameVerifier(new HostnameVerifier() {    /*Skip SSL*/
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            })
            .build();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }
}
