package org.ligboy.translate;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ligboy.translate.model.TokenKey;
import org.ligboy.translate.model.TranslateResult;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author ligboy ligboy@gmail.com
 */
public class TranslateServiceTest {

    private static final String SOURCE_TEXT_1 = "That with devotion's visage and pious action \n"
            + "we do sugar o'er the devil himself. And ideas are bulletproof.";

    private MockWebServer mMockWebServer;
    private TranslateService mTranslateService;
    private TranslateService mOnlineTransService;

    @Before
    public void setUp() throws Exception {
        mMockWebServer = new MockWebServer();
        mMockWebServer.start();
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


        Retrofit scriptRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(mMockWebServer.url("/"))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(new TranslateConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mTranslateService = scriptRetrofit.create(TranslateService.class);

        Retrofit onlineRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://translate.google.cn")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(new TranslateConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mOnlineTransService = onlineRetrofit.create(TranslateService.class);
    }

    @Test
    public void getTokenKey() throws Exception {
        mMockWebServer.enqueue(new MockResponse().setBody("其他%1$s个定义';MSG_N_MORE_RELATED_LABEL='其他%1$s个相关词';"
                + "MSG_N_MORE_SYNONYMS_LABEL='其他%1$s个同义词';MSG_SEE_ALSO='另请参阅';MSG_SYNONYMS='同义词';MSG_SYNON"
                + "YMS_LOWERCASE='同义词';MSG_SYNONYMS_OF='%1$s的同义词';MSG_SPEECH_INPUT_TURN_ON='打开语音输入';MSG_SPE"
                + "ECH_INPUT_TURN_OFF='关闭语音输入';MSG_SPEAK_NOW='请开始说话';MSG_SPELLING_AUTO_CORRECTION='显示以下内容"
                + "的翻译：';MSG_SPELLING_REVERT_CORRECTION='仍然翻译：';TTS_PACE_CONTROL=1;SK_WP=1;WEBFONT=1;DEFAULT_S"
                + "OURCES=['en','zh-CN','de'];DEFAULT_TARGETS=['zh-CN','en','ja'];DEFAULT_TTS_DIALECT_EN = 'en-US';D"
                + "EFAULT_TTS_DIALECT_ES = 'es-US';DEFAULT_TTS_DIALECT_PT = 'pt-BR';DEFAULT_TTS_DIALECT_ZH = 'zh';LO"
                + "W_CONFIDENCE_THRESHOLD=-1;MAX_ALTERNATIVES_ROUNDTRIP_RESULTS=1;TKK=eval('((function(){var a\\x3d8"
                + "40695688;var b\\x3d-766515088;return 406344+\\x27.\\x27+(a+b)})())');WEB_TRANSLATION_PATH='/trans"
                + "late';SIGNED_IN=true;USAGE='ALkJrhgAAAAAVz5hc-z9UpKEVu7mG4haWO2zUliW2xv8';</script><div id=gt-form"
                + "-c><form id=gt-form action=\"/\" name=text_form method=post enctype=\"application/x-www-form-urlen"
                + "coded\"><div id=gt-appbar><div id=gt-apb-c><div id=gt-apb-main><a id=gt-appname href=\"/\">翻译</a>"
                + "<span id=ft-l><a id=gt-otf-switch href=\"/?hl=zh-CN&eotf=0&sl=zh-CN&tl=en\">关闭即时翻译</a></span></"
                + "div></div></div><div id=gt-text-all><div id=gt-main><div id=gt-text-c><div id=gt-langs><div id=gt-"
                + "lang-left class=goog-inline-block><div id=gt-lang-src><div id=gt-sl-sugg class=\"gt-lang-sugg-messa"
                + "ge goog-inline-block je\"><div class=\"goog-inline-block jfk-button jfk-button-standard jfk-button-"
                + "checked jfk-button-collapse-right\">中文</div><div class=\"goog-inline-block jfk-button jfk-button-st"
                + "andard jfk-button-collapse-left jfk-button-collapse-right\">中文</div><div class=\"goog-inline-block"
                + " jfk-button jfk-button-standard jfk-button-collapse-left jfk-button-collapse-right\">英语</div>"));
        mMockWebServer.enqueue(new MockResponse().setBody("其他%1$s个定义';MSG_N_MORE_RELATED_LABEL='其他%1$s个相关词"
                + "';MSG_N_MORE_SYNONYMS_LABEL='其他%1$s个同义词';MSG_SEE_ALSO='另请参阅';MSG_SYNONYMS='同义词';MSG_SYNONY"
                + "MS_LOWERCASE='同义词';MSG_SYNONYMS_OF='%1$s的同义词';MSG_SPEECH_INPUT_TURN_ON='打开语音输入';MSG_SPEECH"
                + "_INPUT_TURN_OFF='关闭语音输入';MSG_SPEAK_NOW='请开始说话';MSG_SPELLING_AUTO_CORRECTION='显示以下内容的翻译"
                + "：';MSG_SPELLING_REVERT_CORRECTION='仍然翻译：';TTS_PACE_CONTROL=1;SK_WP=1;WEBFONT=1;DEFAULT_SOURCES"
                + "=['en','zh-CN','de'];DEFAULT_TARGETS=['zh-CN','en','ja'];DEFAULT_TTS_DIALECT_EN = 'en-US';DEFAULT_"
                + "TTS_DIALECT_ES = 'es-US';DEFAULT_TTS_DIALECT_PT = 'pt-BR';DEFAULT_TTS_DIALECT_ZH = 'zh';LOW_CONFID"
                + "ENCE_THRESHOLD=-1;MAX_ALTERNATIVES_ROUNDTRIP_RESULTS=1;TsdfKK=eval('((function(){var a\\x3d8406956"
                + "88;var b\\x3d-766515088;return 406344+\\x27.\\x27+(a+b)})())');WEB_TRANSLATION_PATH='/translate';S"
                + "IGNED_IN=true;USAGE='ALkJrhgAAAAAVz5hc-z9UpKEVu7mG4haWO2zUliW2xv8';</script><div id=gt-form-c><fo"
                + "rm id=gt-form action=\"/\" name=text_form method=post enctype=\"application/x-www-form-urlencoded"
                + "\"><div id=gt-appbar><div id=gt-apb-c><div id=gt-apb-main><a id=gt-appname href=\"/\">翻译</a><spa"
                + "n id=ft-l><a id=gt-otf-switch href=\"/?hl=zh-CN&eotf=0&sl=zh-CN&tl=en\">关闭即时翻译</a></span></div"
                + "></div></div><div id=gt-text-all><div id=gt-main><div id=gt-text-c><div id=gt-langs><div id=gt-la"
                + "ng-left class=goog-inline-block><div id=gt-lang-src><div id=gt-sl-sugg class=\"gt-lang-sugg-messag"
                + "e goog-inline-block je\"><div class=\"goog-inline-block jfk-button jfk-button-standard jfk-button-"
                + "checked jfk-button-collapse-right\">中文</div><div class=\"goog-inline-block jfk-button jfk-button-"
                + "standard jfk-button-collapse-left jfk-button-collapse-right\">中文</div><div class=\"goog-inline-bl"
                + "ock jfk-button jfk-button-standard jfk-button-collapse-left jfk-button-collapse-right\">英语</div>"));
        Call<TokenKey> tokenKeyCall1 = mTranslateService.getTokenKey();
        Response<TokenKey> keyResponse1 = tokenKeyCall1.execute();
        Assert.assertTrue(keyResponse1.isSuccessful());
        Assert.assertNotNull(keyResponse1.body());
        Assert.assertEquals("406344.74180600", keyResponse1.body().getKey());

        Call<TokenKey> tokenKeyCall2 = mTranslateService.getTokenKey();
        Response<TokenKey> keyResponse2 = tokenKeyCall2.execute();
        Assert.assertTrue(keyResponse1.isSuccessful());
    }

    @Test
    public void translate() throws Exception {
        mMockWebServer.enqueue(new MockResponse().setBody("[[[\"Ligboy\\n翻译关闭即时翻译\\n中文英语德语检测到中文英语"
                + "中文(简体)日语翻译\\n\\nfuck啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\\nfuck啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
                + "啊\\n修改建议\\nGoogle 翻译（企业版）：译者工具包网站翻译器全球商机洞察\\n关于 Google 翻译社区移动Google 大全隐私权"
                + "和使用条款帮助发送反馈\",\"Ligboy\\n翻译关闭即时翻译\\n中文英语德语检测到中文英语中文(简体)日语翻译\\n\\nfuck啊啊啊"
                + "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\\nfuck啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\\n修改建议\\nGoogle 翻译（企业"
                + "版）：译者工具包网站翻译器全球商机洞察\\n关于 Google 翻译社区移动Google 大全隐私权和使用条款帮助发送反馈\",,,0],[,"
                + ",\"Ligboy\\nfānyì guānbì jíshí fānyì\\nzhōngwén yīngyǔ déyǔ jiǎncè dào zhōngwén yīngyǔ zhòng wén "
                + "(jiǎntǐ) rìyǔ fānyì\\n\\nfuck a a a a a a a a a a a a a a a a a a a a a\\nfuck a a a a a a a a a a"
                + " a a a a a a a a a a a\\nxiūgǎi jiànyì\\nGoogle fānyì (qǐyè bǎn): Yì zhě gōngjù bāo wǎngzhàn fānyì"
                + " qì quánqiú shāngjī dòngchá\\nguānyú Google fānyì shèqū yídòng Google dàquán yǐnsī quán hé shǐyòng"
                + " tiáokuǎn bāngzhù fāsòng fǎnkuì\",\"Ligboy\\nfānyì guānbì jíshí fānyì\\nzhōngwén yīngyǔ déyǔ jiǎnc"
                + "è dào zhōngwén yīngyǔ zhòng wén (jiǎntǐ) rìyǔ fānyì\\n\\nfuck a a a a a a a a a a a a a a a a a a "
                + "a a a\\nfuck a a a a a a a a a a a a a a a a a a a a a\\nxiūgǎi jiànyì\\nGoogle fānyì (qǐyè bǎn): "
                + "Yì zhě gōngjù bāo wǎngzhàn fānyì qì quánqiú shāngjī dòngchá\\nguānyú Google fānyì shèqū yídòng Goo"
                + "gle dàquán yǐnsī quán hé shǐyòng tiáokuǎn bāngzhù fāsòng fǎnkuì\"]],,\"zh-CN\",,,,0.95330018,,[[\""
                + "zh-CN\"],,[0.95330018],[\"zh-CN\"]]]"));
        mMockWebServer.enqueue(new MockResponse().setBody(""));
        Call<TranslateResult> translateResultCall1 = mTranslateService.translate(TranslateResult.SOURCE_LANG_AUTO, "en",
                "Ligboy\n翻译关闭即时翻译\n中文英语德语检测到中文英语中文(简体)日语翻译\n\n"
                        + "fuck啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\nfuck啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\n"
                        + "修改建议\nGoogle 翻译（企业版）：译者工具包网站翻译器全球商机洞察\n关于 Google 翻译社区移动Google "
                        + "大全隐私权和使用条款帮助发送反馈", "406344.74180600");
        Response<TranslateResult> resultResponse1 = translateResultCall1.execute();
        Assert.assertTrue(resultResponse1.isSuccessful());
        TranslateResult translateResult = resultResponse1.body();
        Assert.assertNotNull(translateResult);
        Assert.assertNotNull(translateResult.getSentences());
        Assert.assertEquals(1, translateResult.getSentences().size());
        Assert.assertEquals("Ligboy\n翻译关闭即时翻译\n中文英语德语检测到中文英语中文(简体)日语翻译\n\nfuck啊啊啊啊啊啊啊啊啊啊"
                + "啊啊啊啊啊啊啊啊啊啊啊\nfuck啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\n修改建议\nGoogle 翻译（企业版）：译者工具包"
                + "网站翻译器全球商机洞察\n关于 Google 翻译社区移动Google 大全隐私权和使用条款帮助发送反馈", "Ligboy\n翻译关闭即时翻"
                + "译\n中文英语德语检测到中文英语中文(简体)日语翻译\n\nfuck啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\nfuck啊啊啊啊啊"
                + "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\n修改建议\nGoogle 翻译（企业版）：译者工具包网站翻译器全球商机洞察\n关于 Google "
                + "翻译社区移动Google 大全隐私权和使用条款帮助发送反馈", translateResult.getSourceText());
        Assert.assertEquals("Ligboy\n翻译关闭即时翻译\n中文英语德语检测到中文英语中文(简体)日语翻译\n\nfuck啊啊啊啊啊啊啊啊啊啊"
                + "啊啊啊啊啊啊啊啊啊啊啊\nfuck啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\n修改建议\nGoogle 翻译（企业版）：译者工具包"
                + "网站翻译器全球商机洞察\n关于 Google 翻译社区移动Google 大全隐私权和使用条款帮助发送反馈", "Ligboy\n翻译关闭即时翻"
                + "译\n中文英语德语检测到中文英语中文(简体)日语翻译\n\nfuck啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\nfuck啊啊啊啊啊"
                + "啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\n修改建议\nGoogle 翻译（企业版）：译者工具包网站翻译器全球商机洞察\n关于 Google "
                + "翻译社区移动Google 大全隐私权和使用条款帮助发送反馈", translateResult.getTargetText());
        Assert.assertEquals("Ligboy\nfānyì guānbì jíshí fānyì\nzhōngwén yīngyǔ déyǔ jiǎncè dào zhōngwén yīngyǔ "
                + "zhòng wén (jiǎntǐ) rìyǔ fānyì\n\nfuck a a a a a a a a a a a a a a a a a a a a a\nfuck a a a a "
                + "a a a a a a a a a a a a a a a a a\nxiūgǎi jiànyì\nGoogle fānyì (qǐyè bǎn): Yì zhě gōngjù bāo w"
                + "ǎngzhàn fānyì qì quánqiú shāngjī dòngchá\nguānyú Google fānyì shèqū yídòng Google dàquán yǐnsī"
                + " quán hé shǐyòng tiáokuǎn bāngzhù fāsòng fǎnkuì", "Ligboy\nfānyì guānbì jíshí fānyì\nzhōngwén y"
                + "īngyǔ déyǔ jiǎncè dào zhōngwén yīngyǔ zhòng wén (jiǎntǐ) rìyǔ fānyì\n\nfuck a a a a a a a a a "
                + "a a a a a a a a a a a a\nfuck a a a a a a a a a a a a a a a a a a a a a\nxiūgǎi jiànyì\nGoogle"
                + " fānyì (qǐyè bǎn): Yì zhě gōngjù bāo wǎngzhàn fānyì qì quánqiú shāngjī dòngchá\nguānyú Google "
                + "fānyì shèqū yídòng Google dàquán yǐnsī quán hé shǐyòng tiáokuǎn bāngzhù fāsòng fǎnkuì",
                translateResult.getTranslit());
        Assert.assertEquals("zh-CN", translateResult.getSourceLang());
        Assert.assertEquals(0.95330018D, translateResult.getLangProportion(), 0.00000001);

    }

    @Test
    @Ignore
    public void translateOnline() throws Exception {
        Call<TokenKey> tokenKeyCall = mOnlineTransService.getTokenKey();
        Response<TokenKey> tokenKeyResponse = tokenKeyCall.execute();
        TokenKey tokenKey = tokenKeyResponse.body();
        if (tokenKeyResponse.isSuccessful() && tokenKey != null && tokenKey.getKey() != null) {
            TokenGenerator tokenGenerator = new TokenGenerator(tokenKey.getKey());
            Call<TranslateResult> translateResultCall = mOnlineTransService.translate(TranslateResult.SOURCE_LANG_AUTO,
                    "zh-CN", SOURCE_TEXT_1, tokenGenerator.token(SOURCE_TEXT_1));
            Response<TranslateResult> resultResponse = translateResultCall.execute();
            Assert.assertTrue(resultResponse.isSuccessful());
            TranslateResult result = resultResponse.body();
            Assert.assertNotNull(result);
            Assert.assertNotNull(result.getSentences());
            Assert.assertEquals(3, result.getSentences().size());
            Assert.assertEquals("en", result.getSourceLang());
            //The translation will be changed dynamically. So, can't use assertEquals.
            Assert.assertTrue(result.getLangProportion() > 0.0F);
            Assert.assertNotNull(result.getTargetText());
            Assert.assertNotNull(result.getSourceText());
        } else {
            throw new Exception("Can't retrieve the tokenKey.");
        }
    }

    @After
    public void tearDown() throws Exception {
        mMockWebServer.shutdown();
    }

}
