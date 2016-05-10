package org.ligboy.translate.model;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author ligboy
 */
public class TranslateResult {

    private @Nullable List<Sentence> sentences;

    @Nullable
    public String getTranslit() {
        return translit;
    }

    @Nullable
    public List<Sentence> getSentences() {
        return sentences;
    }

    @Nullable
    private String translit;

    @Nullable
    public String getSource() {
        if (sentences != null && !sentences.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Sentence sentence : sentences) {
                if (sentence != null && sentence.source != null && !sentence.source.isEmpty()) {
                    sb.append(sentence.source);
                }
            }
            return sb.toString();
        }
        return null;
    }

    @Nullable
    public String getTarget() {
        if (sentences != null && !sentences.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Sentence sentence : sentences) {
                if (sentence != null && sentence.target != null && !sentence.target.isEmpty()) {
                    sb.append(sentence.target);
                }
            }
            return sb.toString();
        }
        return null;
    }

    public void setSentences(@Nullable List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void setTranslit(@Nullable String translit) {
        this.translit = translit;
    }

    public static class Sentence {

        @Nullable
        public String getSource() {
            return source;
        }

        @Nullable
        public String getTarget() {
            return target;
        }

        @Nullable
        private String source;

        @Nullable
        private String target;

        @Override
        public String toString() {
            return "Source: [" + source + "], \ngetTarget: [" + target + "]\n";
        }

        public void setSource(@Nullable String source) {
            this.source = source;
        }

        public void setTarget(@Nullable String target) {
            this.target = target;
        }
    }

    @Override
    public String toString() {
        return "sentences: " + (sentences != null ? sentences.toString() : "null") + "\ntranslit: " + translit + "\n";
    }
}
