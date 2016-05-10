package org.ligboy.translate;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ligboy.translate.model.TranslateResult;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
        mServer.enqueue(new MockResponse().setBody("'词组太长，无法保存。';MSG_PB_SIGNIN='登录查看您的好词好句。';MSG_SAVE="
                + "'保存';MSG_SAVED='已保存';MSG_SAVE_PB='登录保存好词好句';MSG_SAVING='正在保存…';MSG_SHOW_PB='显示好词好句';"
                + "MSG_SORT_BY='排序';MSG_SOURCE='原文';MSG_TRANSLATE_FROM_LABEL='源语言：';MSG_TRANSLATE_TO_LABEL='目标"
                + "语言：';MSG_VIEW_PB='查看我的好词好句';BUTTER_BAR_URL = '';BUTTER_BAR_LOGTYPE = '';COMMUNITY_PROMO_TYP"
                + "E = 'new';COMMUNITY_PROMO_SERVED_DISMISSED = true;ENABLE_COMMUNITY_USER_EDIT_PROMO = true;ENABLE_"
                + "COMMUNITY_SERVED_PROMO = true;ENCODING = 'UTF-8';DROP_LINK=1;MSG_DEFINITIONS_OF='%1$s的定义';MSG_F"
                + "EWER_DEFINITIONS_LABEL='隐藏部分定义';MSG_FEWER_SYNONYMS_LABEL='隐藏部分同义词';MSG_FEWER_RELATED_LAB"
                + "EL='隐藏部分相关词汇';MSG_N_MORE_DEFINITIONS_LABEL='其他%1$s个定义';MSG_N_MORE_RELATED_LABEL='其他%1$s个"
                + "相关词';MSG_N_MORE_SYNONYMS_LABEL='其他%1$s个同义词';MSG_SEE_ALSO='另请参阅';MSG_SYNONYMS='同义词';MSG"
                + "_SYNONYMS_LOWERCASE='同义词';MSG_SYNONYMS_OF='%1$s的同义词';MSG_SPEECH_INPUT_TURN_ON='打开语音输入';MS"
                + "G_SPEECH_INPUT_TURN_OFF='关闭语音输入';MSG_SPEAK_NOW='请开始说话';MSG_SPELLING_AUTO_CORRECTION='显示"
                + "以下内容的翻译：';MSG_SPELLING_REVERT_CORRECTION='仍然翻译：';TTS_PACE_CONTROL=1;SK_WP=1;WEBFONT=1;DE"
                + "FAULT_SOURCES=['en','zh-CN','de'];DEFAULT_TARGETS=['zh-CN','en','ja'];DEFAULT_TTS_DIALECT_EN = "
                + "'en-US';DEFAULT_TTS_DIALECT_ES = 'es-ES';DEFAULT_TTS_DIALECT_PT = 'pt-BR';DEFAULT_TTS_DIALECT_ZH"
                + " = 'zh';LOW_CONFIDENCE_THRESHOLD=-1;MAX_ALTERNATIVES_ROUNDTRIP_RESULTS=1;TKK=eval('((function(){v"
                + "ar a\\x3d3107398634;var b\\x3d-1461292996;return 406349+\\x27.\\x27+(a+b)})())');WEB_TRANSLA"
                + "TION_PATH='/translate';SIGNED_IN=false;USAGE='';</script><div id=gt-form-c><form id=gt-form act"
                + "ion=\"/\" name=text_form method=post enctype=\"application/x-www-form-urlencoded\"><div id=gt-a"
                + "ppbar><div id=gt-apb-c><div id=gt-apb-main><a id=gt-appname href=\"/\">翻译</a><span id=ft-l><a "
                + "id=gt-otf-switch href=\"/?hl=zh-CN&eotf=0&sl=zh-CN&tl=en\">关闭即时翻译</a></span></div></div></di"
                + "v><div id=gt-text-all><div id=gt-main><div id=gt-text-c><div id=gt-langs><div id=gt-lang-left "
                + "class=goog-inline-block><div id=gt-lang-src><div id=gt-sl-sugg class=\"gt-lang-sugg-message goo"
                + "g-inline-block je\"><div class=\"goog-inline-block jfk-button jfk-button-standard jfk-button-ch"
                + "ecked jfk-button-collapse-right\">中文</div><div class=\"goog-inline-block jfk-button jfk-butt"
                + "on-standard jfk-button-collapse-left jfk-button-collapse-right\">英语</div><div class=\"goog-in"
                + "line-block jfk-button jfk-button-standard jfk-button-collapse-left jfk-button-collapse-right\">"
                + "德语</div><div class=\"goog-inline-block jfk-button jfk-button-standard jfk-button-collapse-le"
                + "ft jfk-button-collapse-right\">检测语言</div></div><label for=gt-sl class=\"gt-lang-lbl nje\"><"
                + "/label><select id=gt-sl name=sl class=\"jfk-button jfk-button-standard nje\" tabindex=0><option"
                + " SELECTED "));
        Translate translate = new Translate(mRetrofit);
        assertNull(translate.mTokenGenerator);
        translate.refreshTokenKey();
        assertNotNull(translate.mTokenGenerator);
        assertEquals("615620.1005449", translate.mTokenGenerator.token("哈哈哈哈哈"));
    }

    @Test
    public void translate() throws Exception {
        mServer.enqueue(new MockResponse().setBody("'词组太长，无法保存。';MSG_PB_SIGNIN='登录查看您的好词好句。';MSG_SAVE="
                + "'保存';MSG_SAVED='已保存';MSG_SAVE_PB='登录保存好词好句';MSG_SAVING='正在保存…';MSG_SHOW_PB='显示好词好句';"
                + "MSG_SORT_BY='排序';MSG_SOURCE='原文';MSG_TRANSLATE_FROM_LABEL='源语言：';MSG_TRANSLATE_TO_LABEL='目标"
                + "语言：';MSG_VIEW_PB='查看我的好词好句';BUTTER_BAR_URL = '';BUTTER_BAR_LOGTYPE = '';COMMUNITY_PROMO_TYP"
                + "E = 'new';COMMUNITY_PROMO_SERVED_DISMISSED = true;ENABLE_COMMUNITY_USER_EDIT_PROMO = true;ENABLE_"
                + "COMMUNITY_SERVED_PROMO = true;ENCODING = 'UTF-8';DROP_LINK=1;MSG_DEFINITIONS_OF='%1$s的定义';MSG_F"
                + "EWER_DEFINITIONS_LABEL='隐藏部分定义';MSG_FEWER_SYNONYMS_LABEL='隐藏部分同义词';MSG_FEWER_RELATED_LAB"
                + "EL='隐藏部分相关词汇';MSG_N_MORE_DEFINITIONS_LABEL='其他%1$s个定义';MSG_N_MORE_RELATED_LABEL='其他%1$s个"
                + "相关词';MSG_N_MORE_SYNONYMS_LABEL='其他%1$s个同义词';MSG_SEE_ALSO='另请参阅';MSG_SYNONYMS='同义词';MSG"
                + "_SYNONYMS_LOWERCASE='同义词';MSG_SYNONYMS_OF='%1$s的同义词';MSG_SPEECH_INPUT_TURN_ON='打开语音输入';MS"
                + "G_SPEECH_INPUT_TURN_OFF='关闭语音输入';MSG_SPEAK_NOW='请开始说话';MSG_SPELLING_AUTO_CORRECTION='显示"
                + "以下内容的翻译：';MSG_SPELLING_REVERT_CORRECTION='仍然翻译：';TTS_PACE_CONTROL=1;SK_WP=1;WEBFONT=1;DE"
                + "FAULT_SOURCES=['en','zh-CN','de'];DEFAULT_TARGETS=['zh-CN','en','ja'];DEFAULT_TTS_DIALECT_EN = "
                + "'en-US';DEFAULT_TTS_DIALECT_ES = 'es-ES';DEFAULT_TTS_DIALECT_PT = 'pt-BR';DEFAULT_TTS_DIALECT_ZH"
                + " = 'zh';LOW_CONFIDENCE_THRESHOLD=-1;MAX_ALTERNATIVES_ROUNDTRIP_RESULTS=1;TKK=eval('((function(){v"
                + "ar a\\x3d3107398634;var b\\x3d-1461292996;return 406349+\\x27.\\x27+(a+b)})())');WEB_TRANSLA"
                + "TION_PATH='/translate';SIGNED_IN=false;USAGE='';</script><div id=gt-form-c><form id=gt-form act"
                + "ion=\"/\" name=text_form method=post enctype=\"application/x-www-form-urlencoded\"><div id=gt-a"
                + "ppbar><div id=gt-apb-c><div id=gt-apb-main><a id=gt-appname href=\"/\">翻译</a><span id=ft-l><a "
                + "id=gt-otf-switch href=\"/?hl=zh-CN&eotf=0&sl=zh-CN&tl=en\">关闭即时翻译</a></span></div></div></di"
                + "v><div id=gt-text-all><div id=gt-main><div id=gt-text-c><div id=gt-langs><div id=gt-lang-left "
                + "class=goog-inline-block><div id=gt-lang-src><div id=gt-sl-sugg class=\"gt-lang-sugg-message goo"
                + "g-inline-block je\"><div class=\"goog-inline-block jfk-button jfk-button-standard jfk-button-ch"
                + "ecked jfk-button-collapse-right\">中文</div><div class=\"goog-inline-block jfk-button jfk-butt"
                + "on-standard jfk-button-collapse-left jfk-button-collapse-right\">英语</div><div class=\"goog-in"
                + "line-block jfk-button jfk-button-standard jfk-button-collapse-left jfk-button-collapse-right\">"
                + "德语</div><div class=\"goog-inline-block jfk-button jfk-button-standard jfk-button-collapse-le"
                + "ft jfk-button-collapse-right\">检测语言</div></div><label for=gt-sl class=\"gt-lang-lbl nje\"><"
                + "/label><select id=gt-sl name=sl class=\"jfk-button jfk-button-standard nje\" tabindex=0><option"
                + " SELECTED "));

        Translate translate = new Translate(mRetrofit);
        assertNull(translate.mTokenGenerator);
        translate.refreshTokenKey();
        assertNotNull(translate.mTokenGenerator);

        mServer.enqueue(new MockResponse().setBody("[[[\"Ha ha ha ha ha\",\"哈哈哈哈哈\",,,0],[,,,\"Hāhāhā hā hā\"]],"
                + ",\"zh-CN\",,,[[\"哈哈哈\",1,[[\"Ha ha ha\",951,true,false],[\"Ha ha\",0,true,false],[\"Ha\",0,true,"
                + "false],[\"Ha ha ha ha\",0,true,false]],[[0,3]],\"哈哈哈哈哈\",0,3],[\"哈哈\",2,[[\"ha ha\",815,true,"
                + "false],[\"ha ha ha\",124,true,false],[\"ha\",11,true,false],[\"Haha\",0,true,false]],[[3,5]],,3,5]"
                + "],1,,[[\"zh-CN\"],,[1],[\"zh-CN\"]]]"));
        final String text = "哈哈哈哈哈";
        String token = translate.mTokenGenerator.token(text);
        assertEquals("615620.1005449", token);
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
