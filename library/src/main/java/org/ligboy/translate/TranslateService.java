package org.ligboy.translate;

import org.ligboy.translate.model.TokenKey;
import org.ligboy.translate.model.TranslateResult;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Translate API Service
 * @author ligboy
 */
interface TranslateService {

    @GET("/")
    @Headers({
            "user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94"
                    + " Safari/537.37"
        })
    Call<TokenKey> getTokenKey();

    @POST("/translate_a/single?client=t&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie="
            + "UTF-8&oe=UTF-8&getSourceText=btn&ssel=0&tsel=0&kc=1")
    @Headers({
        "user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 "
                + "Safari/537.37",
        "referer: https://translate.google.cn/?hl=zh-CN&tab=wT",
        "authority: translate.google.cn"
        })
    @FormUrlEncoded
    Call<TranslateResult> translate(@Query("sl") String sourceLanguage, @Query("tl") String targetLanguage,
                                    @Field("q") String keywords, @Query("tk") String token);
}
