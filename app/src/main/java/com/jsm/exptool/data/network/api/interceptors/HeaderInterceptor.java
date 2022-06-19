package com.jsm.exptool.data.network.api.interceptors;

import com.jsm.exptool.providers.SessionProvider;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();

        String token = SessionProvider.getToken();
        if (!"".equals(token)) {
            builder.addHeader("Authorization", token);
        }

        Request request = builder.build();
        return chain.proceed(request);
    }
}
