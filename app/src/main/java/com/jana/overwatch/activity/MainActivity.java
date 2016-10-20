package com.jana.overwatch.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jana.overwatch.helper.DeviceListHolder;
import com.jana.overwatch.POJO.Device;
import com.jana.overwatch.POJO.APIResponse;
import com.jana.overwatch.R;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText mMasterKey;
    private Button mSignIn;
    private ProgressDialog mProgressDialog;
    private Toast successToast;
    private Toast failureToast;
    private SharedPreferences sharedPreferences;

    private Device[] mDevices;
    private String jsonDeviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignIn = (Button) findViewById(R.id.sign_in_button);
        mMasterKey = (EditText) findViewById(R.id.masterKey);

        successToast = Toast.makeText(getApplicationContext(), "Devices Fetched!", Toast.LENGTH_SHORT);
        failureToast = Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT);
        mProgressDialog = new ProgressDialog(MainActivity.this);

        sharedPreferences = getSharedPreferences("Overwatch_JANA", Context.MODE_PRIVATE);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mMasterKey.getText().toString().equals("")) {
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Fetching Data...");

                    loadDevices();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter Master Key", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void loadDevices() {
        mProgressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api-m2x.att.com/v2/devices";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<APIResponse> jsonAdapter = moshi.adapter(APIResponse.class);
                        try {
                            APIResponse apiResponse = jsonAdapter.fromJson(response);
                            mDevices = apiResponse.devices;
                            DeviceListHolder.getInstance().getDeviceList().clear();

                            for (Device device : mDevices) {
                                DeviceListHolder.getInstance().getDeviceList().add(device);
                            }

                            Gson gson = new Gson();
                            jsonDeviceList = gson.toJson(DeviceListHolder.getInstance().getDeviceList());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                            successToast.show();
                            Intent intent = new Intent(getApplicationContext(), BeanListActivity.class);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Master_API_Key", mMasterKey.getText().toString());
                            editor.putString("Device_List", jsonDeviceList);
                            editor.commit();

                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error//", error.toString());
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    failureToast.show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-M2X-KEY", mMasterKey.getText().toString());

                return params;
            }
        };

        queue.add(stringRequest);
    }
}
