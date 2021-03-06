package com.jana.overwatch.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jana.overwatch.POJO.APIResponse;
import com.jana.overwatch.POJO.Entry;
import com.jana.overwatch.POJO.Stream;
import com.jana.overwatch.R;
import com.jana.overwatch.helper.DeviceListHolder;
import com.jana.overwatch.POJO.Device;
import com.jana.overwatch.helper.UtilFunction;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeviceActivity extends AppCompatActivity {

    private Device mDevice;
    private Stream[] mStreams;
    private Entry[] mEntries;
    final Context mContext = this;
    private ImageButton mEditButton;
    private TextView mDeviceName, mDeviceDescription, mTemperature, mMovement;
    private ProgressDialog mProgressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        Intent incomingIntent = getIntent();
        int devicePosition = incomingIntent.getIntExtra("device_position", 0);
        sharedPreferences = getSharedPreferences("Overwatch_JANA", Context.MODE_PRIVATE);
        mDevice = DeviceListHolder.getInstance().getDeviceList().get(devicePosition);
        mEditButton = (ImageButton) findViewById(R.id.edit_button);
        mDeviceName = (TextView) findViewById(R.id.bean_name_details);
        mDeviceDescription = (TextView) findViewById(R.id.bean_description_details);
        mTemperature = (TextView) findViewById(R.id.bean_temperature_details);
        mMovement = (TextView) findViewById(R.id.bean_movement_details);

        mDeviceName.setText(mDevice.name);
        mDeviceDescription.setText(mDevice.description);
        mProgressDialog = new ProgressDialog(DeviceActivity.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Updating...");

        fetchTemperatureLog();
        fetchTriggerLog();

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptView = layoutInflater.inflate(R.layout.edit_device, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setView(promptView);

                final EditText userNameInput = (EditText) promptView.findViewById(R.id.device_name_edit);
                final EditText userDescInput = (EditText) promptView.findViewById(R.id.device_description_edit);

                userNameInput.setHint(mDevice.name);
                userDescInput.setHint(mDevice.description);
                Toast.makeText(mContext, mDevice.visibility, Toast.LENGTH_SHORT);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (userNameInput.getText().toString().equals("")) {
                                            Toast.makeText(mContext, "Device Name is Required", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mProgressDialog.show();
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    updateDevice(userNameInput.getText().toString(), userDescInput.getText().toString(), mDevice.visibility);
                                                    mDeviceName.setText(userNameInput.getText().toString());
                                                    mDeviceDescription.setText(userDescInput.getText().toString());
                                                    mProgressDialog.dismiss();
                                                }
                                            }, 1000);
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void updateDevice(final String name, final String description, final String visibility) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api-m2x.att.com/v2/devices/" + mDevice.id ;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DeviceListHolder.getInstance().getDeviceList().remove(mDevice);
                        mDevice.name = name;
                        mDevice.description = description;
                        mDevice.visibility = visibility;
                        DeviceListHolder.getInstance().getDeviceList().add(0, mDevice);
                        Gson gson = new Gson();
                        String jsonDeviceList = gson.toJson(DeviceListHolder.getInstance().getDeviceList());

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Device_List", jsonDeviceList);
                        editor.commit();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error//", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("description", description);
                params.put("visibility", visibility);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-M2X-KEY", sharedPreferences.getString("Master_API_Key", ""));

                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void fetchTemperatureLog() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api-m2x.att.com/v2/devices/" + mDevice.id + "/streams";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<APIResponse> jsonAdapter = moshi.adapter(APIResponse.class);
                        try {
                            APIResponse apiResponse = jsonAdapter.fromJson(response);
                            mStreams = apiResponse.streams;

                            for (Stream stream: mStreams) {
                                if (stream.display_name.equals("Temperature")) {
                                    mTemperature.setText(stream.value + "°C");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error//", error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-M2X-KEY", sharedPreferences.getString("Master_API_Key", ""));

                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void fetchTriggerLog() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api-m2x.att.com/v2/devices/" + mDevice.id + "/triggers/log";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<APIResponse> jsonAdapter = moshi.adapter(APIResponse.class);
                        try {
                            APIResponse apiResponse = jsonAdapter.fromJson(response);
                            mEntries = apiResponse.entries;

                            for (Entry entry: mEntries) {
                                if (entry.trigger.equals("Movement_Trigger")) {
                                    mMovement.setText(UtilFunction.formatLastTimeUsed(entry.timestamp));
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error//", error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-M2X-KEY", sharedPreferences.getString("Master_API_Key", ""));

                return params;
            }
        };
        queue.add(stringRequest);
    }
}
