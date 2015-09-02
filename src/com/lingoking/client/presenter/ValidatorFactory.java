package com.lingoking.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;
import com.lingoking.shared.model.Profile;

import javax.validation.ParameterNameProvider;
import javax.validation.Validator;


public final class ValidatorFactory extends AbstractGwtValidatorFactory {

    public ParameterNameProvider getParameterNameProvider() {
        return null;
    }

    public void close() {

    }

    @GwtValidation(Profile.class)
    public interface GwtValidator extends Validator {
    }

    @Override
    public AbstractGwtValidator createValidator() {
        return GWT.create(GwtValidator.class);
    }
}