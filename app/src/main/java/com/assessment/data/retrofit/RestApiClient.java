package com.assessment.data.retrofit;

import com.assessment.constants.ApiConstantsKt;
import com.assessment.data.retrofit.service.NewsApi;

import retrofit2.Retrofit;

public final class RestApiClient {

    private static volatile RestApiClient INSTANCE;
    private NewsApi newsApi;
    private final RetrofitAdapter retrofitAdapter;

    private RestApiClient() {
        retrofitAdapter = new RetrofitAdapter();
    }

    public void setBaseUrl(String baseUrl) {
        Retrofit client = retrofitAdapter.initRetrofit(baseUrl, true);
        newsApi = client.create(NewsApi.class);
    }

    public static RestApiClient getInstance() {
        RestApiClient localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (RestApiClient.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new RestApiClient();
                }
            }
        }
        return localInstance;
    }

    public NewsApi newsApi() {
        if (newsApi ==null) {
            setBaseUrl(ApiConstantsKt.BASE_URL);
        }
        return newsApi;
    }
}
