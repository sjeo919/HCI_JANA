package com.jana.overwatch.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.jana.overwatch.R;
import com.jana.overwatch.helper.DeviceListHolder;
import com.jana.overwatch.POJO.Device;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeviceActivity extends AppCompatActivity {

    private Device mDevice;
    final Context mContext = this;
    private Button mEditButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        Intent incomingIntent = getIntent();
        int devicePos = incomingIntent.getIntExtra("device_position", 0);
        sharedPreferences = getSharedPreferences("Overwatch_JANA", Context.MODE_PRIVATE);
        mDevice = DeviceListHolder.getInstance().getDeviceList().get(devicePos);
        mEditButton = (Button) findViewById(R.id.edit_button);

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptView = layoutInflater.inflate(R.layout.edit_device, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setView(promptView);

                final EditText userNameInput = (EditText) promptView.findViewById(R.id.device_name_edit);
                final EditText userDescInput = (EditText) promptView.findViewById(R.id.device_description_edit);
                final Spinner spinner = (Spinner) promptView.findViewById(R.id.spinner);

                userNameInput.setHint(mDevice.name);
                userDescInput.setHint(mDevice.description);
                Toast.makeText(mContext, mDevice.visibility, Toast.LENGTH_SHORT);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext, R.array.visibility_array,
                        android.R.layout.simple_spinner_dropdown_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (userNameInput.getText().toString() == "") {
                                            Toast.makeText(mContext, "Device Name is Required", Toast.LENGTH_SHORT).show();
                                        } else if (spinner.getSelectedItem().toString().equalsIgnoreCase("public")) {
                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                                            dialogBuilder.setTitle("Security");
                                            dialogBuilder.setMessage("Setting the device visibility to Public means unauthorised people can access your device. Are you sure you want to proceed?");

                                            dialogBuilder.setCancelable(false)
                                                    .setPositiveButton("Continue",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    updateDevice(userNameInput.getText().toString(), userDescInput.getText().toString(), spinner.getSelectedItem().toString());
                                                                }
                                                            })
                                                    .setNegativeButton("Cancel",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    dialogInterface.dismiss();
                                                                }
                                                            });

                                            AlertDialog dialog = dialogBuilder.create();
                                            dialog.show();
                                        } else {
                                            updateDevice(userNameInput.getText().toString(), userDescInput.getText().toString(), spinner.getSelectedItem().toString());
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
                params.put("visibility", visibility.toLowerCase());
                Log.d("FFFFFFF", params.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-M2X-KEY", sharedPreferences.getString("Master_API_Key", ""));

                return params;
            }
        };

        Log.d("FFFFFFF", stringRequest.getUrl());
        queue.add(stringRequest);
    }
}
