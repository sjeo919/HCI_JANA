package com.jana.overwatch.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jana.overwatch.R;
import com.jana.overwatch.helper.DeviceListHolder;
import com.jana.overwatch.POJO.Device;

public class DeviceActivity extends AppCompatActivity {

    private Device mDevice;
    final Context mContext = this;
    private Button mEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        Intent incomingIntent = getIntent();
        int devicePos = incomingIntent.getIntExtra("device_position", 0);
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
                                        Toast.makeText(mContext, userNameInput.getText().toString() + " is new device name", Toast.LENGTH_SHORT ).show();
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
}
