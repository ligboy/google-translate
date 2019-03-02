package org.ligboy.translate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.ligboy.translate.model.TokenKey;
import org.ligboy.translate.model.TranslateResult;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Translate Converter Factory
 * @author ligboy
 */
class TranslateConverterFactory extends Converter.Factory {

    static final TranslateConverterFactory DEFAULT = new TranslateConverterFactory();

    private static final Pattern PATTERN_TOKEN_KEY = Pattern.compile("tkk: *['\"](\\d*\\.\\d*)['\"]");

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == TranslateResult.class) {
            return new Converter<ResponseBody, TranslateResult>() {

                private final Gson mGson = new Gson();

                @Override
                public TranslateResult convert(ResponseBody value) throws IOException {
                    TranslateResult data = new TranslateResult();
                    JsonReader jsonReader = mGson.newJsonReader(value.charStream());
                    JsonArray jsonArray;
                    try {
                        jsonArray = mGson.fromJson(jsonReader, JsonArray.class);
                    } finally {
                        value.close();
                    }
                    if (jsonArray != null) {
                        JsonArray translations = jsonArray.get(0).getAsJsonArray();
                        if (translations != null && translations.size() > 1) {
                            data.setSentences(new ArrayList<TranslateResult.Sentence>(translations.size() - 1));
                            List<TranslateResult.Sentence> sentences = data.getSentences();
                            for (int i = 0; i < translations.size() - 1; i++) {
                                TranslateResult.Sentence sentence = new TranslateResult.Sentence();
                                JsonArray sentenceArray = translations.get(i).getAsJsonArray();
                                sentence.setTargetText(sentenceArray.get(0).getAsString());
                                sentence.setSourceText(sentenceArray.get(1).getAsString());
                                //noinspection ConstantConditions
                                sentences.add(i, sentence);
                            }
                            JsonArray translitJsonArray = translations.get(translations.size() - 1).getAsJsonArray();
                            data.setTranslit(translitJsonArray.get(translitJsonArray.size() - 1).getAsString());
                        }
                        data.setSourceLang(jsonArray.get(2).getAsString());
                        data.setLangProportion(jsonArray.get(6).getAsDouble());
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
                            final String key = matcher.group(1);
                            TokenKey tokenKey = new TokenKey();
                            tokenKey.setKey(key);
                            return tokenKey;
                        }
                    }
                    return null;
                }
            };
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {
        return null;
    }
}
