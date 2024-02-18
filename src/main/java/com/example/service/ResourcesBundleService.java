package com.example.service;

import com.example.enums.AppLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResourcesBundleService {
    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

    public String getMessage(String code, AppLanguage appLanguage) {
        return resourceBundleMessageSource.getMessage("email.password.wrong", null, new Locale(appLanguage.name()));
    }
}
