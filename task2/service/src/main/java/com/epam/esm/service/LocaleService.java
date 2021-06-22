package com.epam.esm.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * Provides localization of messages that are sent to the client.
 *
 * @author Shuleiko Yulia
 */
@Service
public class LocaleService {

    private MessageSource messageSource;

    /**
     * Setter method of the {@code MessageSource} object
     *
     * @param messageSource the {@code MessageSource} object
     */
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Getter method of the locale.
     *
     * @return the {@code Locale} object
     */
    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    /**
     * Getter method of the localized messages.
     *
     * @param messageName message's text
     * @param args        arguments
     * @return localized message
     */
    public String getLocaleMessage(String messageName, Object... args) {
        return messageSource.getMessage(messageName, args, getLocale());
    }
}

