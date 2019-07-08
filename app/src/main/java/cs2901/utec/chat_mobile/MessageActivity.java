package cs2901.utec.chat_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

public class MessageActivity extends AppCompatActivity {
    JSONArray messages = new JSONArray();
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    public Activity getActivity(){
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        String username = getIntent().getExtras().get("username").toString();
        setTitle("@"+username);
        mRecyclerView = findViewById(R.id.main_recycler_view);
    }

    @Override
    protected void onResume(){
        super.onResume();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getChats();
    }

    public void onClickBtnSend(View v) {
        postMessage();
    }

    public void getChats(){
        final String userFromId = getIntent().getExtras().get("user_from_id").toString();
        String userToId = getIntent().getExtras().get("user_to_id").toString();
        String url = "http://10.0.2.2:5000/messages/<user_from_id>/<user_to_id>";
        url = url.replace("<user_from_id>", userFromId);
        url = url.replace("<user_to_id>", userToId);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request= new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i=0;i<response.length();i++){
                                messages.put(i,response.getJSONObject(i));
                            }
                            int uID = Integer.parseInt(userFromId);
                            mAdapter = new MyMessageAdapter(messages, getActivity(), uID);
                            mRecyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        queue.add(request);
    }

    public void postMessage(){
        String url = "http://10.0.2.2:5000/create/messages";
        RequestQueue queue = Volley.newRequestQueue(this);
        Map<String, String> params = new HashMap();
        final String user_from_id = getIntent().getExtras().get("user_from_id").toString();
        final String user_to_id = getIntent().getExtras().get("user_to_id").toString();
        final String content = ((EditText)findViewById(R.id.txtMessage)).getText().toString();
        params.put("user_from_id",user_from_id);
        params.put("user_to_id", user_to_id);
        params.put("content", content);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();

            }
        });
        queue.add(jsonObjectRequest);

    }
}