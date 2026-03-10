package com.hafiz.expense.buddy.ui.signUp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignupViewModel extends ViewModel {

    public MutableLiveData<String> email = new MutableLiveData<>("");
    public MutableLiveData<SignupEvent> event = new MutableLiveData<>();

    public void onSignupClicked() {
        String emailValue = email.getValue();
        if (emailValue == null || emailValue.trim().isEmpty()) {
            event.setValue(SignupEvent.EMPTY_EMAIL);
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue.trim()).matches()) {
            event.setValue(SignupEvent.INVALID_EMAIL);
            return;
        }
        event.setValue(SignupEvent.SIGNUP);
    }

    public void onCancelClicked() {
        event.setValue(SignupEvent.CANCEL);
    }

    public enum SignupEvent {
        SIGNUP,
        CANCEL,
        EMPTY_EMAIL,
        INVALID_EMAIL
    }
}
