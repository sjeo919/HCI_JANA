package com.jana.overwatch.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.jana.overwatch.POJO.APIResponse;
import com.jana.overwatch.POJO.Device;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InitialActivity extends AppCompatActivity {

    private Device[] mDevices;
    private String jsonDeviceList;
    private Toast successToast;
    private Toast failureToast;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        successToast = Toast.makeText(getApplicationContext(), "Devices Updated!", Toast.LENGTH_SHORT);
        failureToast = Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT);

        Intent intent;
        sharedPreferences = getSharedPreferences("Overwatch_JANA", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("Master_API_Key", "").length() > 1) {
            intent = new Intent(this, BeanListActivity.class);
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
                            successToast.show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Device_List", jsonDeviceList);
                            editor.commit();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error//", error.toString());
                    failureToast.show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("X-M2X-KEY", sharedPreferences.getString("Master_API_Key",""));

                    return params;
                }
            };

            queue.add(stringRequest);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
