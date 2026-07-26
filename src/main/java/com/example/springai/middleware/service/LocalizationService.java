package com.example.springai.middleware.service;

import jakarta.annotation.Nonnull;

import java.util.Locale;

public interface LocalizationService {

    @Nonnull
    String getTranslate(String key);

    @Nonnull
    <T> String getTranslate(@Nonnull final String key,
                            @Nonnull final T[] params);

    @Nonnull
    String getTranslate(@Nonnull final String key,
                        @Nonnull final String defaultValue);

    @Nonnull
    <T> String getTranslate(@Nonnull final String key,
                            @Nonnull final T[] params,
                            @Nonnull final String defaultValue);

    @Nonnull
    <T> String getTranslate(@Nonnull final String key,
                            @Nonnull final T[] params,
                            @Nonnull final Locale locale);

    @Nonnull
    String getTranslate(@Nonnull final String key,
                        @Nonnull final String defaultValue,
                        @Nonnull final Locale locale);

    @Nonnull
    <T> String getTranslate(@Nonnull final String key,
                            @Nonnull final T[] params,
                            @Nonnull final String defaultValue,
                            @Nonnull final Locale locale);

}
