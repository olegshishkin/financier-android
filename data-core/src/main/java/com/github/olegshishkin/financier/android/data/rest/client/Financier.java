package com.github.olegshishkin.financier.android.data.rest.client;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

import com.github.olegshishkin.financier.android.data.rest.FinancierApi;
import com.github.olegshishkin.financier.android.data.rest.client.provider.HttpClientProvider;

public class Financier {

    private static FinancierApi client;

    public static FinancierApi client() {
        if (client == null) {
            client = HttpClientProvider.builder()
                    .withLogging()
                    .withLogLevel(BODY)
                    .withBaseUrl("https://d5do5mcbjuponhkpg9f7.apigw.yandexcloud.net")
                    .async()
                    .clientProvider()
                    .create(FinancierApi.class);
        }
        return client;
    }
}
