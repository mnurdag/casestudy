package com.zad.accountservice.common;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackFor = Exception.class)
public class MessageService {

    MessageSource messageSource;

    public String getMessage(String id, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id, args, locale);
    }

}
