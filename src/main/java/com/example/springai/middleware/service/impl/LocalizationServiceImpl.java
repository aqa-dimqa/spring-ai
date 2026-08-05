package com.example.springai.middleware.service.impl;

import com.example.springai.exception.LocalizationException;
import com.example.springai.middleware.service.LocalizationService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {

    private final Locale locale = LocaleContextHolder.getLocale();
    private final MessageSource messageSource;

    @Nonnull
    @Override
    public String getTranslate(String key) {
        return getTranslate(new LocalizationParams(key, null, null, null));
    }

    @Nonnull
    @Override
    public <T> String getTranslate(@Nonnull final String key, @Nonnull final T[] params) {
        return getTranslate(new LocalizationParams(key, params, null, null));
    }

    @Nonnull
    @Override
    public String getTranslate(@Nonnull final String key, @Nonnull final String defaultValue) {
        return getTranslate(new LocalizationParams(key, null, defaultValue, null));
    }

    @Nonnull
    @Override
    public <T> String getTranslate(@Nonnull final String key, @Nonnull final T[] params, @Nonnull final String defaultValue) {
        return getTranslate(new LocalizationParams(key, params, defaultValue, null));
    }

    @Nonnull
    @Override
    public <T> String getTranslate(@Nonnull final String key, @Nonnull final T[] params, @Nonnull final Locale locale) {
        return getTranslate(new LocalizationParams(key, params, null, locale));
    }

    @Nonnull
    @Override
    public String getTranslate(@Nonnull final String key, @Nonnull final String defaultValue, @Nonnull final Locale locale) {
        return getTranslate(new LocalizationParams(key, null, defaultValue, locale));
    }

    @Nonnull
    @Override
    public <T> String getTranslate(@Nonnull final String key, @Nonnull final T[] params, @Nonnull final String defaultValue, @Nonnull final Locale locale) {
        return getTranslate(new LocalizationParams(key, params, defaultValue, locale));
    }

    @Nonnull
    private String getTranslate(LocalizationParams localizationParams) {
        Locale actualLocale = localizationParams.locale() != null ? localizationParams.locale() : this.locale;
        localizationParams.locale(actualLocale);
        return Optional.ofNullable(
                        messageSource.getMessage(
                                localizationParams.key(),
                                localizationParams.params(),
                                localizationParams.defaultValue(),
                                localizationParams.locale())
                )
                .orElseThrow(() -> new LocalizationException(localizationParams));
    }

    @Builder
    public record LocalizationParams(
            @Nonnull String key,
            @Nullable Object[] params,
            @Nullable String defaultValue,
            @Nullable Locale locale
    ) implements Serializable {
        public LocalizationParams locale(Locale locale) {
            return new LocalizationParams(this.key, this.params, this.defaultValue, locale);
        }
    }

}
