package com.luyang.framework.base.http.retrofit;

import okhttp3.MultipartBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * @author wangjixin
 */
public interface ApiService {
	@GET("/users/{id}")
	Call<Object> getUser(@Path("id") String userId);

	@Multipart
	@POST("/upload")
	Call<ResponseBody> uploadFile(
		@Part("description") RequestBody description,
		@Part MultipartBody.Part file
	);

	public static ApiService create() {
		return new Retrofit.Builder()
			.baseUrl("https://api.example.com/")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(ApiService.class);
	}
}

