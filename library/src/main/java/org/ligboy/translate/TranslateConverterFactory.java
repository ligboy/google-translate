package org.ligboy.translate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.ligboy.translate.model.TokenKey;
import org.ligboy.translate.model.TranslateResult;
import retrofit2.Converter;
import retrofit2.Retrofit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ligboy
 */
public class TranslateConverterFactory extends Converter.Factory {

    private static Pattern PATTERN_TOKEN_KEY = Pattern.compile("TKK=eval\\('(.*?)'\\);");

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == TranslateResult.class) {
            return new Converter<ResponseBody, TranslateResult>() {
                @Override
                public TranslateResult convert(ResponseBody value) throws IOException {
                    TranslateResult data = new TranslateResult();
                    JSONArray objects = JSON.parseArray(value.string());
                    if (objects != null) {
                        JSONArray translations = objects.getJSONArray(0);
                        if (translations != null && translations.size() > 1) {
                            data.setSentences(new ArrayList<TranslateResult.Sentence>(translations.size() - 1));
                            List<TranslateResult.Sentence> sentences = data.getSentences();
                            for (int i = 0; i < translations.size() - 1; i++) {
                                TranslateResult.Sentence sentence = new TranslateResult.Sentence();
                                JSONArray sentenceArray = translations.getJSONArray(i);
                                sentence.setSource(sentenceArray.getString(1));
                                sentence.setTarget(sentenceArray.getString(0));
                                //noinspection ConstantConditions
                                sentences.add(i, sentence);
                            }
                            JSONArray translit = translations.getJSONArray(translations.size() - 1);
                            data.setTranslit(translit.getString(translit.size() - 1));
                        }
                    }
                    return data;
                }
            };
        } else if (type == TokenKey.class) {
            return new Converter<ResponseBody, TokenKey>() {
                @Override
                public TokenKey convert(ResponseBody value) throws IOException {
                    String body = value.string();
                    if (!body.isEmpty()) {
                        Matcher matcher = PATTERN_TOKEN_KEY.matcher(body);
                        if (matcher.find()) {
                            /**
                             * 这里使用JavaScript引擎去处理，是规避Token Key计算表达式的变更。
                             */
                            ScriptEngine engine;
                            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
                            engine = scriptEngineManager.getEngineByName("nashorn");
                            if (engine == null) {
                                engine = scriptEngineManager.getEngineByName("JavaScript");
                            }
                            try {
                                String key = (String) engine.eval(matcher.group());
                                TokenKey tokenKey = new TokenKey();
                                tokenKey.setKey(key);
                                return tokenKey;
                            } catch (ScriptException ignored) {
                            }
                        }
                    }
                    return null;
                }
            };
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return null;
    }
}
