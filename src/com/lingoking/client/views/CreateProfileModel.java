package com.lingoking.client.views;

import com.google.gwt.core.client.GWT;
import com.lingoking.shared.model.Profile;
import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;
import com.pietschy.gwt.pectin.client.form.FieldModel;
import com.pietschy.gwt.pectin.client.form.FormModel;
import com.pietschy.gwt.pectin.client.form.validation.ValidationManager;
import com.pietschy.gwt.pectin.client.form.validation.ValidationPlugin;
import com.pietschy.gwt.pectin.client.form.validation.validator.NotEmptyValidator;

import static com.pietschy.gwt.pectin.client.form.metadata.MetadataPlugin.watermark;
import static com.pietschy.gwt.pectin.client.form.validation.ValidationPlugin.validateField;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jan 2, 2010
 * Time: 9:06:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateProfileModel extends FormModel {

    public abstract static class ProfileProvider extends BeanModelProvider<Profile> {}

    private BeanModelProvider<Profile> profileProvider;

    protected final FieldModel<String> emailAddress;
    protected final FieldModel<String> password;
    protected final FieldModel<String> passwordAgain;
    protected final FieldModel<String> phoneNumber;

    public CreateProfileModel()
    {
        this((ProfileProvider) GWT.create(ProfileProvider.class));
    }

    protected CreateProfileModel(BeanModelProvider<Profile> profileProvider)
    {
        this.profileProvider = profileProvider;

        emailAddress = fieldOfType(String.class).boundTo(profileProvider, "emailAddress");
        password = fieldOfType(String.class).boundTo(profileProvider, "password");
        passwordAgain = fieldOfType(String.class).boundTo(profileProvider, "passwordAgain");
        phoneNumber = fieldOfType(String.class).boundTo(profileProvider, "phoneNumber");

        validateField(emailAddress).using(new NotEmptyValidator("Please enter your email address!"));
        validateField(password).using(new NotEmptyValidator("Please enter your password!"));
        validateField(passwordAgain).using(new NotEmptyValidator("Please confirm your password!"));
        validateField(phoneNumber).using(new NotEmptyValidator("Please enter your phone number!"));
        if (password != passwordAgain) {
            new NotEmptyValidator("Please asfasf sfa afasfasf!");
        }

        watermark(emailAddress, password, passwordAgain, phoneNumber).with("Required");
    }

    public void setProfile(Profile profile)
    {
        getValidationManager().clear();
        profileProvider.setValue(profile);
    }

    public void commit()
    {
        profileProvider.commit();
    }

    public boolean validate()
    {
        return getValidationManager().validate();
    }

    ValidationManager getValidationManager()
    {
        return ValidationPlugin.getValidationManager(this);
    }
}