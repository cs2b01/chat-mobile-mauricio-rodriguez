package cs2901.utec.chat_mobile;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity
{   JSONArray thearray= new JSONArray();
    RecyclerView recycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        recycler=findViewById(R.id.main_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        setTitle("@"+getIntent().getExtras().get("username").toString());
    }

    @Override
    protected void onResume(){
        super.onResume();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        getUsers();
    }

    public Activity getActivity(){
        return this; }

    public void getUsers(){
        String url = "http://10.0.2.2:5000/users";
        RequestQueue queue = Volley.newRequestQueue(this);
        final String userId = getIntent().getExtras().get("user_id").toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i=0;i<response.length();i++){
                                thearray.put(i,response.getJSONObject(i));
                            }
                            adapter = new ChatAdapter(thearray, getActivity(), userId);
                            recycler.setAdapter(adapter);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();

            }
        });
        queue.add(jsonArrayRequest);

    }

}
