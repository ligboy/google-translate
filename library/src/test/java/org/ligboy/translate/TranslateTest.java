package org.ligboy.translate;

import okhttp3.Dns;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ligboy.translate.model.TranslateResult;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * @author ligboy
 */
public class TranslateTest {

    private MockWebServer mServer;
    private Retrofit mRetrofit;
    private TranslateService mTranslateService;

    @Before
    public void setUp() throws Exception {
        mServer = new MockWebServer();
        mServer.start();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(new TranslateConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mServer.url("/"))
                .build();
        mTranslateService = mRetrofit.create(TranslateService.class);
    }

    @Test
    public void refreshTokenKey() throws Exception {
        URL url = getClass().getResource("/google.html");
        File googelHtml = new File(url.toURI());
        String htmlContent = FileUtils.readFileToString(googelHtml, Charset.forName("utf-8"));
        mServer.enqueue(new MockResponse().setBody(htmlContent));
        Translate translate = new Translate.Builder()
                .baseUrl(mServer.url("/").toString())
                .connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .writeTimeout(10L, TimeUnit.SECONDS)
                .dns(Dns.SYSTEM)
                .logLevel(Translate.LogLevel.BODY)
                .retryOnConnectionFailure(true)
                .build();
        assertNull(translate.mTokenGenerator);
        translate.refreshTokenKey();
        assertNotNull(translate.mTokenGenerator);
        assertEquals("987921.622743", translate.mTokenGenerator.token("哈哈哈哈哈"));
    }

    @Test
    public void translate() throws Exception {
        URL url = getClass().getResource("/google.html");
        File googelHtml = new File(url.toURI());
        String htmlContent = FileUtils.readFileToString(googelHtml, Charset.forName("utf-8"));
        mServer.enqueue(new MockResponse().setBody(htmlContent));

        Translate translate = new Translate.Builder()
                .baseUrl(mServer.url("/").toString())
                .connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .writeTimeout(10L, TimeUnit.SECONDS)
                .dns(Dns.SYSTEM)
                .logLevel(Translate.LogLevel.BODY)
                .retryOnConnectionFailure(true)
                .build();
        assertNull(translate.mTokenGenerator);
        translate.refreshTokenKey();
        assertNotNull(translate.mTokenGenerator);
        assertEquals("430982.2084267326", translate.mTokenGenerator.getTokenKey());

        mServer.enqueue(new MockResponse().setBody("[[[\"Ha ha ha ha ha\",\"哈哈哈哈哈\",,,0],[,,,\"Hāhāhā hā hā\"]],"
                + ",\"zh-CN\",,,[[\"哈哈哈\",1,[[\"Ha ha ha\",951,true,false],[\"Ha ha\",0,true,false],[\"Ha\",0,true,"
                + "false],[\"Ha ha ha ha\",0,true,false]],[[0,3]],\"哈哈哈哈哈\",0,3],[\"哈哈\",2,[[\"ha ha\",815,true,"
                + "false],[\"ha ha ha\",124,true,false],[\"ha\",11,true,false],[\"Haha\",0,true,false]],[[3,5]],,3,5]"
                + "],1,,[[\"zh-CN\"],,[1],[\"zh-CN\"]]]"));
        final String text = "哈哈哈哈哈";
        String token = translate.mTokenGenerator.token(text);
        assertEquals("987921.622743", token);
        TranslateResult result = translate.translate(text, null, "en");
        assertNotNull(result);
        assertNotNull(result.getSentences());
        assertNotNull(result.getSourceText());
        assertNotNull(result.getTargetText());
        assertEquals("zh-CN", result.getSourceLang());
        assertEquals(1D, result.getLangProportion(), 0.00000001D);
        assertEquals("哈哈哈哈哈", result.getSourceText());
        assertEquals("Ha ha ha ha ha", result.getTargetText());
        assertEquals("Hāhāhā hā hā", result.getTranslit());
    }

    @Test
    public void testBuilder() throws Exception {
        URL url = getClass().getResource("/google.html");
        File googelHtml = new File(url.toURI());
        String htmlContent = FileUtils.readFileToString(googelHtml, Charset.forName("utf-8"));
        mServer.enqueue(new MockResponse().setBody(htmlContent));
        Translate translate = new Translate.Builder()
                .baseUrl(mServer.url("/").toString())
                .connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .writeTimeout(10L, TimeUnit.SECONDS)
                .dns(Dns.SYSTEM)
                .logLevel(Translate.LogLevel.BODY)
                .retryOnConnectionFailure(true)
                .build();
        translate.refreshTokenKey();
        assertNotNull(translate.mTokenGenerator);

        mServer.enqueue(new MockResponse().setBody("[[[\"Ha ha ha ha ha\",\"哈哈哈哈哈\",,,0],[,,,\"Hāhāhā hā hā\"]],"
                + ",\"zh-CN\",,,[[\"哈哈哈\",1,[[\"Ha ha ha\",951,true,false],[\"Ha ha\",0,true,false],[\"Ha\",0,true,"
                + "false],[\"Ha ha ha ha\",0,true,false]],[[0,3]],\"哈哈哈哈哈\",0,3],[\"哈哈\",2,[[\"ha ha\",815,true,"
                + "false],[\"ha ha ha\",124,true,false],[\"ha\",11,true,false],[\"Haha\",0,true,false]],[[3,5]],,3,5]"
                + "],1,,[[\"zh-CN\"],,[1],[\"zh-CN\"]]]"));
        final String text = "哈哈哈哈哈";
        String token = translate.mTokenGenerator.token(text);
        assertEquals("987921.622743", token);
        TranslateResult result = translate.translate(text, null, "en");
        assertNotNull(result);
        assertNotNull(result.getSentences());
        assertNotNull(result.getSourceText());
        assertNotNull(result.getTargetText());
        assertEquals("zh-CN", result.getSourceLang());
        assertEquals(1D, result.getLangProportion(), 0.00000001D);
        assertEquals("哈哈哈哈哈", result.getSourceText());
        assertEquals("Ha ha ha ha ha", result.getTargetText());
        assertEquals("Hāhāhā hā hā", result.getTranslit());
    }

    @After
    public void tearDown() throws Exception {
        mServer.shutdown();
    }

}
