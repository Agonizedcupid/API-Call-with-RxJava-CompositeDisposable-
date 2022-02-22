package com.aariyan.composite_disposable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.composite_disposable.Interface.Apis;
import com.aariyan.composite_disposable.Model.POJO;
import com.aariyan.composite_disposable.Networking.RetrofitClient;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void initUI() {
        textView = findViewById(R.id.textView);

        networkCall();
    }

    private void networkCall() {
        Retrofit retrofit = RetrofitClient.getClient();
        Apis apis = retrofit.create(Apis.class);
        //compositeDisposable.add(apis.getPost()
        compositeDisposable.add(apis.getPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        //Log.d("TEST_RESULT", responseBody.string());

                        JSONArray array = new JSONArray(responseBody.string());
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String title = object.getString("title");
                            String body = object.getString("body");

                            textView.append("Title: " + title + "\nBody: " + body+"\n\n");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("TEST_RESULT", ""+throwable);

                        Toast.makeText(MainActivity.this, ""+throwable, Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}