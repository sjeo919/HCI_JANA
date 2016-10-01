package com.jana.overwatch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jana.overwatch.POJO.Device;
import com.jana.overwatch.POJO.OAuthClient;
import com.jana.overwatch.POJO.OAuthToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mMasterKey;
    private Button mSignIn;
    private ProgressDialog mProgressDialog;
    private Toast successToast;
    private Toast failureToast;

    private String authorisationUrl = "https://m2x.att.com";
    private String tokenUrl = "https://api-m2x.att.com";
    private String requestUrl = "https://api-m2x.att.com/";
    private String redirectUri = "";
    private String mSentState = "hello1234";

    private String mReceivedState;
    private String mCode;
    private String mTokenType;
    private String mAccessToken;
    private String mScope;
    private String mMessage = "";
    private String mErrors = "";

    private String email;
    private String password;
    private List<Device> mDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mEmail = (EditText) findViewById(R.id.email);
//        mPassword = (EditText) findViewById(R.id.password);
        mSignIn = (Button) findViewById(R.id.sign_in_button);
        mMasterKey = (EditText) findViewById(R.id.masterKey);
//
        successToast = Toast.makeText(getApplicationContext(), "Devices Fetched!", Toast.LENGTH_SHORT);
        failureToast = Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT);
        mProgressDialog = new ProgressDialog(MainActivity.this);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mMasterKey.getText().toString().equals("")) {
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Fetching Data...");
                    mProgressDialog.show();

                    loadDevices();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Enter Master Key", Toast.LENGTH_SHORT);
                    toast.show();
                }

//                email = mEmail.getText().toString();
//                password = mPassword.getText().toString();
//
//                getAuthorised();
            }
        });
    }

    private void loadDevices() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(requestUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        M2XService serviceClient = retrofit.create(M2XService.class);

        Call<List<Device>> call = serviceClient.fetchDevices(mMasterKey.getText().toString());

        call.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                try {
                    mDevices = response.body();
                    successToast.show();
                    Log.d("RESPONSE", new Gson().toJson(response));
                    Intent intent = new Intent(getApplicationContext(), BeanListActivity.class);
                    intent.putParcelableArrayListExtra("device_list", (ArrayList<Device>) response.body());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                Log.d("Failure", "Fetching// " + t.toString());
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    failureToast.show();
                }
            }
        });
    }

//    private void getAuthorised() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(authorisationUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        M2XService client = retrofit.create(M2XService.class);
//
//        Call<OAuthClient> call = client.getAuthorised("code", email, redirectUri, mSentState, "GET");
//
//        call.enqueue(new Callback<OAuthClient>() {
//            @Override
//            public void onResponse(Call<OAuthClient> call, Response<OAuthClient> response) {
//                try {
//                    mCode = response.body().getCode();
//                    mReceivedState = response.body().getCheckState();
//                    getToken();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OAuthClient> call, Throwable t) {
//                Log.d("Failure", "Authorisation// " + t.toString());
//                if (mProgressDialog.isShowing()) {
//                    mProgressDialog.dismiss();
//                    failureToast.show();
//                }
//            }
//        });
//    }
//
//    private void getToken() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(tokenUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        M2XService service = retrofit.create(M2XService.class);
//
//        Call<OAuthToken> call = service.getAccessToken(email, password, "authorization_code", mCode,redirectUri);
//
//        call.enqueue(new Callback<OAuthToken>() {
//            @Override
//            public void onResponse(Call<OAuthToken> call, Response<OAuthToken> response) {
//                try {
//                    mTokenType = response.body().getTokenType();
//                    mAccessToken = response.body().getAccessToken();
//                    mScope = response.body().getScope();
//                    mMessage = response.body().getMessage();
//                    mErrors = response.body().getErrors();
//
//                    if (mProgressDialog.isShowing()) {
//                        mProgressDialog.dismiss();
//                        if (mMessage.equals("") || mErrors.equals("")) {
//                            successToast.show();
//                            Intent intent = new Intent(getApplicationContext(), BeanListActivity.class);
//                            startActivity(intent);
//                        } else {
//                            failureToast.show();
//                            Log.d("Failure", "Message: " + mMessage);
//                            Log.d("Failure", "Errors: " + mErrors);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OAuthToken> call, Throwable t) {
//                Log.d("Failure", "Token// " + t.toString());
//                if (mProgressDialog.isShowing()) {
//                    mProgressDialog.dismiss();
//                    failureToast.show();
//                }
//            }
//        });
//    }
//
//    public String getmAccessToken() {
//        return mAccessToken;
//    }
//
//    public void setmAccessToken(String mAccessToken) {
//        this.mAccessToken = mAccessToken;
//    }
}
