package cs2901.utec.chat_mobile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class CurrentActivity extends AppCompatActivity {

    public Activity getActivity(){
        return this; }
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    ArrayList<String> listContactos;
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        recycler=findViewById(R.id.recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        listContactos= new ArrayList<String>();
        for (int i=0;i<20;i++){
            listContactos.add("Contacto #"+i+"");
        }
        ContactsActivity adapter= new ContactsActivity(listContactos);
        recycler.setAdapter(adapter);
    //------------------------------------//
        JsonObjectRequest request;
        request = new JsonObjectRequest
                (Request.Method.GET,
                        "http://10.0.2.2:5000/current",
                        null,
                    new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        showMessage(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //showMessage("Error recognizing");
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}
