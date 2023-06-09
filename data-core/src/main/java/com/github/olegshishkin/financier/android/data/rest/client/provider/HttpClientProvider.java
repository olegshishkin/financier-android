package com.github.olegshishkin.financier.android.data.rest.client.provider;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClientProvider {

    public static HttpClientBuilder builder() {
        return new HttpClientBuilder();
    }

    public static class HttpClientBuilder {

        private final OkHttpClient.Builder clientBuilder;
        private HttpLoggingInterceptorBuilder logInterceptorBuilder;
        private boolean async;
        private String baseUrl;

        private HttpClientBuilder() {
            clientBuilder = new OkHttpClient.Builder();
        }

        public HttpLoggingInterceptorBuilder withLogging() {
            logInterceptorBuilder = new HttpLoggingInterceptorBuilder();
            return logInterceptorBuilder;
        }

        public HttpClientBuilder async() {
            async = true;
            return this;
        }

        public HttpClientBuilder withBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Retrofit clientProvider() {
            if (baseUrl == null || baseUrl.isBlank()) {
                throw new IllegalArgumentException("Base URL must not be empty");
            }
            if (logInterceptorBuilder != null) {
                clientBuilder.addInterceptor(logInterceptorBuilder.logInterceptor);
            }
            var builder = new Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build());
            if (async) {
                builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            }
            return builder.build();
        }

        public class HttpLoggingInterceptorBuilder {

            private final HttpLoggingInterceptor logInterceptor;

            private HttpLoggingInterceptorBuilder() {
                logInterceptor = new HttpLoggingInterceptor();
            }

            public HttpClientBuilder withLogLevel(Level level) {
                if (level == null) {
                    throw new IllegalArgumentException("Log level must not be null");
                }
                logInterceptor.setLevel(level);
                return HttpClientBuilder.this;
            }
        }
    }
}
