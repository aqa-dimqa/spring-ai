package com.example.springai.exception;

import com.example.springai.middleware.service.impl.LocalizationServiceImpl.LocalizationParams;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

public class LocalizationException extends RuntimeException {

    public LocalizationException(String message) {
        super(message);
    }

    public LocalizationException(LocalizationParams localizationParams) {
        super(messageBuild(localizationParams));
    }

    private static String messageBuild(LocalizationParams localizationParams) {
        String base = "Failed to get localized message for key = {%s}".formatted(localizationParams.key());
        StringJoiner joiner = new StringJoiner("\n");

        attachParams(localizationParams, joiner);
        attachDefaultValue(localizationParams, joiner);
        attachLocale(localizationParams, joiner);

        return base + "\n" + joiner;
    }

    private static void attachLocale(LocalizationParams localizationParams, StringJoiner joiner) {
        Optional.ofNullable(localizationParams.locale())
                .ifPresent(locale -> joiner.add("Locale: " + locale));
    }

    private static void attachDefaultValue(LocalizationParams localizationParams, StringJoiner joiner) {
        Optional.ofNullable(localizationParams.defaultValue())
                .ifPresent(defaultValue -> joiner.add("Default value: " + defaultValue));
    }

    private static void attachParams(LocalizationParams localizationParams, StringJoiner joiner) {
        Optional.ofNullable(localizationParams.params())
                .ifPresent(params -> {
                    if (params.length > 0)
                        joiner.add("Params: [%s]".formatted(Arrays.toString(params)));
                });
    }

}
