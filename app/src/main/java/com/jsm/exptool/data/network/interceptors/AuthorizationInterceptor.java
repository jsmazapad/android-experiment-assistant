package com.jsm.exptool.data.network.interceptors;

import com.jsm.exptool.core.data.network.responses.NetworkElementResponse;
import com.jsm.exptool.data.network.responses.LoginResponse;
import com.jsm.exptool.providers.SessionProvider;
import com.jsm.exptool.repositories.RemoteSyncRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Provee del ciclo de vida para la sesión
 */
public class AuthorizationInterceptor implements Interceptor {

    public AuthorizationInterceptor() {

    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response mainResponse = chain.proceed(chain.request());
        Request mainRequest = chain.request();

        // if response code is 401 or 403, 'mainRequest' has encountered authentication error
        if (mainResponse.code() == 401 || mainResponse.code() == 403) {

            // request to login API to get fresh token
            // synchronously calling login API
            retrofit2.Response<NetworkElementResponse<LoginResponse>> authorizationResponse = RemoteSyncRepository.login(null, true).execute();

            if (authorizationResponse.isSuccessful()) {
                // login request succeed, new token generated
                LoginResponse loginResponse = authorizationResponse.body().getElement();
                // save the new token
                SessionProvider.setToken(loginResponse.getToken());
                // retry the 'mainRequest' which encountered an authentication error
                // add new token into 'mainRequest' header and request again
                Request.Builder builder = mainRequest.newBuilder().header("Authorization", SessionProvider.getToken()).
                        method(mainRequest.method(), mainRequest.body());
                mainResponse = chain.proceed(builder.build());
            }
        }
        return mainResponse;
    }
}

