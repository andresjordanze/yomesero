package android.hmkcode.com.yomeserorestaurantes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ListView itemsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#078673")));

        itemsListView = (ListView) findViewById(R.id.itemsListView);

        new HttpAsyncTask(this).execute("https://frozen-springs-8168.herokuapp.com/items.json");
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try{
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream!=null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work";
        }catch (Exception e){
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line="";
        String result = "";
        while ((line = bufferedReader.readLine())!=null)
            result+=line;
        inputStream.close();
        return result;
    }

    public void goToItemForm(View view){
        Intent intent = new Intent(this, ItemFormActivity.class);
        startActivity(intent);
    }

    public Boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class HttpAsyncTask extends AsyncTask<String,Void,String> {
        private Context context;
        public HttpAsyncTask(Context context){
            this.context = context;
        }
        @Override
        protected String doInBackground(String... urls){
            return GET(urls[0]);
        }

        protected void onPostExecute(String result){
            try {
                JSONArray json_items = new JSONArray(result);
                /*ArrayList<String> items = new ArrayList<>();
                Item aux;
                for (int i = 0; i < json_items.length(); i++) {
                    aux = new Item();
                    aux.parseFromJson(json_items.getJSONObject(i));
                    items.add("Name: "+aux.item_name);
                }*/
                ArrayList<Item> items = new ArrayList<>();
                Item aux;
                for (int i = 0; i < json_items.length(); i++) {
                    aux = new Item();
                    aux.parseFromJson(json_items.getJSONObject(i));
                    items.add(aux);
                }
                //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.item_list, R.id.firstLine, items);
                MyArrayAdapter myArrayAdapter = new MyArrayAdapter(context,items);
                itemsListView.setAdapter(myArrayAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
