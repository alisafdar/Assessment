package com.assessment.data.retrofit.service;

import com.assessment.constants.ApiConstantsKt;
import com.assessment.data.models.response.NewsResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.Single;

public interface NewsApi {

    @GET(ApiConstantsKt.MOST_VIEWED_BY_SECTION_AND_PERIOD)
    Single<NewsResponse> getNews(@Path("section") String section, @Path("period") String period);

}
