package android.hmkcode.com.yomeserorestaurantes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.nio.charset.Charset;


public class ItemFormActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#078673")));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_form, menu);
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

    public void createItem(View view){
        Intent intent = new Intent(this, DisplayItemActivity.class);
        EditText item_name = (EditText) findViewById(R.id.item_name);
        EditText item_description = (EditText) findViewById(R.id.item_description);
        EditText item_type = (EditText) findViewById(R.id.item_type);
        EditText item_time = (EditText) findViewById(R.id.item_time);
        EditText item_price = (EditText) findViewById(R.id.item_price);
        String message="https://frozen-springs-8168.herokuapp.com/create_from_json?";
        message += "item_name=";
        message += item_name.getText().toString();
        message += "&item_description=";
        message += item_description.getText().toString();
        message += "&item_type=";
        message += item_type.getText().toString();
        message += "&item_time=";
        message += item_time.getText().toString();
        message += "&item_price=";
        message += item_price.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }

}
