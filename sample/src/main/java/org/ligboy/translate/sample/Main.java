package org.ligboy.translate.sample;


import org.ligboy.translate.Translate;
import org.ligboy.translate.model.TranslateResult;

/**
 * @author ligboy
 */
public class Main {

    public static void main(String[] args) {
        final Translate translate = new Translate.Builder()
                .logLevel(Translate.LogLevel.BODY)
                .build();

        try {
            translate.refreshTokenKey();

            TranslateResult translateResult = translate.translate("“我们被教导要记住思想，而不是人，因为人可能失败，可能会被捕，"
                    + "他会被杀死，被遗忘，但几百年后，思想仍可改变世界。我曾亲眼目睹了思想的力量，我见过人们以它为名杀戮，或是为了维护它献出"
                    + "生，但你不能亲吻思想，也不能触摸它或抱着它，思想不会流血，不会感到痛苦，它们没有爱。&oq=“我们被教导要记住思想，而不是"
                    + "人，因为人可能失败，可能会被捕，他会被杀死，被遗忘，但几百年后，思想仍可改变世界。我曾亲眼目睹了思想的力量，我见过人们"
                    + "以它为名杀戮，或是为了维护它献出生，但你不能亲吻思想，也不能触摸它或抱着它，思想不会流血，不会感到痛苦，它们没有爱。",
                    Translate.SOURCE_LANG_AUTO, "en");

            System.out.println(translateResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
