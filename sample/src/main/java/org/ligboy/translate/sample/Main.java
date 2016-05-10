package org.ligboy.translate.sample;


import org.ligboy.translate.Translate;
import org.ligboy.translate.model.TranslateResult;

/**
 * @author ligboy
 */
public class Main {

    public static void main(String[] args) {
        final Translate translate = new Translate();

        try {
            translate.refreshTokenKey();

            TranslateResult translateResult = translate.translate("哈哈哈哈哈", Translate.SOURCE_LANG_AUTO, "en");

            System.out.println(translateResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
