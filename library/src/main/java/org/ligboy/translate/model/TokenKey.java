package org.ligboy.translate.model;

import org.jetbrains.annotations.Nullable;

/**
 * Token Key Wrapper
 * @author ligboy
 */
public class TokenKey {

    @Nullable
    private String key;

    @Nullable
    public String getKey() {
        return key;
    }

    public void setKey(@Nullable String key) {
        this.key = key;
    }
}
