package com.assessment.data.retrofit;

import android.util.Log;

import com.assessment.constants.ApiConstantsKt;
import com.assessment.constants.AppConstantsKt;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import static com.assessment.constants.ApiConstantsKt.API_KEY;
import static com.assessment.constants.ApiConstantsKt.API_KEY_VALUE;


public final class RetrofitAdapter {

    public Retrofit initRetrofit(String _baseUrl, boolean isAuthorised) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        builder.addInterceptor(new LoggingInterceptor());

        builder.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .url(original.url().newBuilder()
                            .addQueryParameter(API_KEY, API_KEY_VALUE)
                            .build())
                    .build();

            return chain.proceed(request);
        });

        OkHttpClient client = builder
                .readTimeout(ApiConstantsKt.API_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(ApiConstantsKt.API_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(ApiConstantsKt.API_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl(_baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }


    public static class LoggingInterceptor implements Interceptor {
        private static final String F_BREAK = " %n";
        private static final String F_URL = " %s";
        private static final String F_TIME = " in %.1fms %n";
        private static final String F_HEADERS = "%s";
        private static final String F_RESPONSE = "Response: %d";
        private static final String F_BODY = "body: %s";

        private static final String F_BREAKER = F_BREAK + "-------------------------------------------" + F_BREAK;
        private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_BREAK + F_HEADERS;
        private static final String F_REQUEST_WITH_BODY = F_URL + F_BREAK + F_HEADERS + F_BODY + F_BREAK;
        private static final String F_RESPONSE_WITHOUT_BODY = F_RESPONSE + F_TIME + F_BREAK + F_HEADERS + F_BREAKER;
        private static final String F_RESPONSE_WITH_BODY = F_RESPONSE + F_TIME + F_BREAK + F_HEADERS + F_BREAK + F_BREAKER;

        private static String stringifyRequestBody(Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            switch (request.method()) {
                case "GET":
                    Log.d(AppConstantsKt.TAG, String.format("GET " + F_REQUEST_WITHOUT_BODY, request.url(),
                            request.headers()));
                    break;
                case "PATCH":
                    Log.d(AppConstantsKt.TAG, String.format("PATCH " + F_REQUEST_WITH_BODY, request.url(),
                            request.headers(), stringifyRequestBody(request)));
                    break;
                case "POST":
                    Log.d(AppConstantsKt.TAG, String.format("POST " + F_REQUEST_WITH_BODY, request.url(),
                            request.headers(), stringifyRequestBody(request)));
                    break;
                case "PUT":
                    Log.d(AppConstantsKt.TAG, String.format("PUT " + F_REQUEST_WITH_BODY, request.url(), request.headers(), request.body().toString()));
                    break;
                case "DELETE":
                    Log.d(AppConstantsKt.TAG, String.format("DELETE " + F_REQUEST_WITHOUT_BODY, request.url(), request.headers()));
                    break;
            }

            long t1 = System.nanoTime();
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            MediaType contentType = null;
            //        String bodyString = null;
            byte[] bodyBytes = null;
            if (response.body() != null) {
                contentType = response.body().contentType();
                bodyBytes = response.body().bytes();
                //            bodyString = response.body().string();
            }

            double diffTime = (t2 - t1) / 1e6d;
            switch (request.method()) {
                case "GET":
                    Log.d(AppConstantsKt.TAG, String.format(F_RESPONSE_WITH_BODY,
                            response.code(), diffTime, response.headers()));
                    Log.d(AppConstantsKt.TAG, stringifyResponseBody(bodyBytes));
                    break;
                case "PATCH":
                    Log.d(AppConstantsKt.TAG, String.format(F_RESPONSE_WITH_BODY,
                            response.code(), diffTime, response.headers()));
                    Log.d(AppConstantsKt.TAG, stringifyResponseBody(bodyBytes));
                    break;
                case "POST":
                    Log.d(AppConstantsKt.TAG, String.format(F_RESPONSE_WITH_BODY, response.code(), diffTime, response.headers()));
                    Log.d(AppConstantsKt.TAG, stringifyResponseBody(bodyBytes));
                    break;
                case "PUT":
                    Log.d(AppConstantsKt.TAG, String.format(F_RESPONSE_WITH_BODY,
                            response.code(), diffTime, response.headers()));
                    Log.d(AppConstantsKt.TAG, stringifyResponseBody(bodyBytes));
                    break;
                case "DELETE":
                    Log.d(AppConstantsKt.TAG, String.format("DELETE " + F_RESPONSE_WITHOUT_BODY,
                            diffTime, response.code(), response.headers()));
                    Log.d(AppConstantsKt.TAG, stringifyResponseBody(bodyBytes));
                    break;
            }
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(contentType, bodyBytes);
                return response.newBuilder().body(body).build();
            } else {

                return response;
            }
        }

        public String stringifyResponseBody(byte[] body) {
            try {
                return new String(body, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
