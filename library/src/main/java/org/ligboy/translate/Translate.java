package org.ligboy.translate;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.ligboy.translate.model.TokenKey;
import org.ligboy.translate.model.TranslateResult;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author ligboy ligboy@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class Translate {

    public static final String SOURCE_LANG_AUTO = "auto";

    private static final String DEFAULT_BASE_URL = "https://translate.google.cn";

    TokenGenerator mTokenGenerator;
    private TranslateService mService;

    /**
     * Default Constructor with default base URL & retrofit & client.
     */
    public Translate() {
        this(null, null);
    }

    /**
     * Constructor
     * @param baseUrl The base URL of translation server.
     * @param client The {@link OkHttpClient} be used.
     */
    public Translate(@Nullable String baseUrl, @Nullable OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(new TranslateConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl == null ? DEFAULT_BASE_URL : baseUrl);
        if (client != null) {
            builder.client(client);
        }
        mService = builder.build().create(TranslateService.class);
    }

    /**
     * Constructor with default retrofit & client.
     * @param baseUrl The base URL of translation server.
     */
    public Translate(@NotNull String baseUrl) {
        this(baseUrl, null);
    }

    /**
     * Constructor with specified {@link TranslateService}.
     * @param service The {@link TranslateService} be used.
     */
    public Translate(@NotNull TranslateService service) {
        mService = service;
    }

    /**
     * Constructor with specified {@link Retrofit}.
     * @param retrofit The {@link Retrofit} be used.
     */
    public Translate(@NotNull Retrofit retrofit) {
        this(retrofit.create(TranslateService.class));
    }

    /**
     * Refresh the token key.
     * @throws Exception If failed throw this Exception.
     */
    public void refreshTokenKey() throws Exception {
        Call<TokenKey> tokenKeyCall = mService.getTokenKey();
        Response<TokenKey> keyResponse = tokenKeyCall.execute();
        TokenKey tokenKey = keyResponse.body();
        if (keyResponse.isSuccessful() && tokenKey != null && tokenKey.getKey() != null) {
            mTokenGenerator = new TokenGenerator(tokenKey.getKey());
        } else {
            throw new Exception("Refresh token key failed.");
        }
    }

    /**
     * Translate text.
     * @param text The text be translated.
     * @param source The source Language. default "auto" - which means auto detecting the source language.
     * @param target The target Language.
     * @return The translated result.
     * @throws Exception
     */
    @Nullable
    public TranslateResult translate(@NotNull String text, @Nullable String source,
                                     @NotNull String target) throws Exception {
        if (mTokenGenerator == null) {
            throw new RuntimeException("token key == null");
        }
        if (source == null) {
            source = Translate.SOURCE_LANG_AUTO;
        }
        final Call<TranslateResult> resultCall = mService.translate(source, target, text, mTokenGenerator.token(text));
        return resultCall.execute().body();
    }
}
