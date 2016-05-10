package org.ligboy.translate.model;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ligboy
 */
public class TranslateResult {
    @Nullable
    private List<Sentence> sentences;

    @Nullable
    private String translit;

    @Nullable
    private String sourceLang;

    private double langProportion;

    @Nullable
    public String getSourceText() {
        if (sentences != null && !sentences.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Sentence sentence : sentences) {
                if (sentence != null && sentence.sourceText != null && !sentence.sourceText.isEmpty()) {
                    sb.append(sentence.sourceText);
                }
            }
            return sb.toString();
        }
        return null;
    }

    @Nullable
    public String getTargetText() {
        if (sentences != null && !sentences.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Sentence sentence : sentences) {
                if (sentence != null && sentence.targetText != null && !sentence.targetText.isEmpty()) {
                    sb.append(sentence.targetText);
                }
            }
            return sb.toString();
        }
        return null;
    }

    @Nullable
    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(@Nullable List<Sentence> sentences) {
        this.sentences = sentences;
    }

    @Nullable
    public String getTranslit() {
        return translit;
    }

    public void setTranslit(@Nullable String translit) {
        this.translit = translit;
    }

    @Nullable
    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(@Nullable String sourceLang) {
        this.sourceLang = sourceLang;
    }

    /**
     * The proportion of the detected language.
     * @return proportion
     */
    public double getLangProportion() {
        return langProportion;
    }

    /**
     * Set the proportion of the detected language.
     * @param langProportion proportion
     */
    public void setLangProportion(double langProportion) {
        this.langProportion = langProportion;
    }

    @Override
    public String toString() {
        return "sentences: " + (sentences != null ? sentences.toString() : "null") + "\ntranslit: " + translit
                + "\nsourceLang: " + sourceLang + "\n";
    }

    public static class Sentence {

        @Nullable
        public String getSourceText() {
            return sourceText;
        }

        @Nullable
        public String getTargetText() {
            return targetText;
        }

        @Nullable
        private String sourceText;

        @Nullable
        private String targetText;

        @Override
        public String toString() {
            return "SourceText: [" + sourceText + "], \ntargetText: [" + targetText + "]\n";
        }

        public void setSourceText(@Nullable String sourceText) {
            this.sourceText = sourceText;
        }

        public void setTargetText(@Nullable String targetText) {
            this.targetText = targetText;
        }
    }
}
