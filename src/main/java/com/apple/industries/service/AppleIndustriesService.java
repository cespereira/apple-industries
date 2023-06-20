package com.apple.industries.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AppleIndustriesService {


    /**
     * Returns a reflective text {@code String}.
     *  text: "apple industries" -> result: "industries apple"
     * @param text the {@code String} to be reversed
     * @return the reversed {@code String}
     * @throws java.lang.IllegalArgumentException when input is empty or null
     */
    public String reflectiveText(final String text) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("Text must not be empty or null!");
        }

        final var splitText = text.split(" ");
        if (splitText.length <= 1) {
            return text;
        }

        final StringBuilder reversed = new StringBuilder();
        for (int i = splitText.length - 1; i >= 0; i--) {
            reversed.append(splitText[i]);
            reversed.append(" ");
        }

        return reversed.toString().trim();
    }

}
