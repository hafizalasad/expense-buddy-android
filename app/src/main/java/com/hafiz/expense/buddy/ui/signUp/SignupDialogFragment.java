package com.hafiz.expense.buddy.ui.signUp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.hafiz.expense.buddy.R;
import com.hafiz.expense.buddy.databinding.SignupDialogFragmentBinding;

public class SignupDialogFragment extends DialogFragment {

    private SignupDialogFragmentBinding binding;
    private SignupViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No custom style needed; we'll configure the window manually
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.signup_dialog_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClickListeners();
        observeEvents();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Make dialog background transparent so CardView rounded corners show
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // Center the dialog with proper width
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.gravity = Gravity.CENTER;
            params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(params);

            // Dim the background
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            params.dimAmount = 0.5f;
            dialog.getWindow().setAttributes(params);
        }
    }

    private void setupClickListeners() {
        binding.ivClose.setOnClickListener(v -> dismiss());
    }

    private void observeEvents() {
        viewModel.event.observe(getViewLifecycleOwner(), event -> {
            if (event == null) return;
            switch (event) {
                case SIGNUP:
                    Toast.makeText(requireContext(),
                            "Account created for: " + viewModel.email.getValue(),
                            Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
                case CANCEL:
                    dismiss();
                    break;
                case EMPTY_EMAIL:
                    binding.tilEmail.setError("Please enter your email");
                    break;
                case INVALID_EMAIL:
                    binding.tilEmail.setError("Please enter a valid email");
                    break;
            }
        });
    }
}
