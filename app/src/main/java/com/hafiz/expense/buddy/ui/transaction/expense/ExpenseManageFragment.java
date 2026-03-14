package com.hafiz.expense.buddy.ui.transaction.expense;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hafiz.expense.buddy.data.CategoryColorPalette;
import com.hafiz.expense.buddy.data.CategoryIconEnum;
import com.hafiz.expense.buddy.data.local.entity.CategoryEntity;
import com.hafiz.expense.buddy.databinding.CategoryListItemBinding;
import com.hafiz.expense.buddy.databinding.DialogAddCategoryBinding;
import com.hafiz.expense.buddy.databinding.ExpenseManageFragmentBinding;
import com.hafiz.expense.buddy.ui.color.ColorAdapter;
import com.hafiz.expense.buddy.ui.emoji.EmojiAdapter;
import com.hafiz.expense.buddy.utils.ColorUtils;

import java.io.File;
import java.util.List;

public class ExpenseManageFragment extends Fragment {

    private ExpenseManageFragmentBinding binding;
    private ExpenseManageViewModel viewModel;

    private long selectedCategoryId = -1;

    private Uri pendingCameraUri;

    // ── Activity Result Launchers (field-level — must be registered before onStart) ──

    private final ActivityResultLauncher<String> pickFromGallery =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) viewModel.onImageSelected(uri);
            });

    private final ActivityResultLauncher<Uri> captureFromCamera =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), success -> {
                if (Boolean.TRUE.equals(success) && pendingCameraUri != null) {
                    viewModel.onImageSelected(pendingCameraUri);
                }
            });

    private final ActivityResultLauncher<String> requestCameraPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) launchCamera();
                else
                    Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            });


    public static ExpenseManageFragment newInstance() {
        return new ExpenseManageFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        binding = ExpenseManageFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ExpenseManageViewModel.class);

        subscribeData();

        initListener();

        setupListeners();
    }

    private void initListener() {
        binding.btnGallery.setOnClickListener(v -> pickFromGallery.launch("image/*"));
        binding.btnCamera.setOnClickListener(v -> checkCameraPermissionThenLaunch());

        // getViewLifecycleOwner() — never use 'this' in Fragment observers
        viewModel.getSelectedImagePath().observe(getViewLifecycleOwner(), this::displayImage);
    }

    private void subscribeData() {
        subscribeCategoryList(viewModel.categoryList);
        subscribeUiToSelectedImagePath(viewModel.getSelectedImagePath());
    }

    private void subscribeUiToSelectedImagePath(LiveData<String> liveData) {
        liveData.observe(getViewLifecycleOwner(), path -> {
            if (path != null) displayImage(path);
            binding.ivImage.setVisibility(path == null ? View.GONE : View.VISIBLE);
        });
    }

    private void subscribeCategoryList(LiveData<List<CategoryEntity>> liveData) {
        liveData.observe(getViewLifecycleOwner(), list -> {
            binding.llCategory.removeAllViews();

            if (list == null) return;

            for (CategoryEntity category : list) {

                CategoryListItemBinding mBinding =
                        CategoryListItemBinding.inflate(LayoutInflater.from(getContext()));

                CategoryIconEnum iconEnum = CategoryIconEnum.findByName(category.getIcon());

                if (iconEnum != null) {
                    mBinding.ivIcon.setImageResource(iconEnum.getIcon());
                    mBinding.ivIcon.setBackgroundTintList(
                            ColorStateList.valueOf(Color.parseColor(category.getBackgroundColor()))
                    );
                }

                mBinding.tvTitle.setText(category.getName());

                mBinding.ivIcon.setOnClickListener(v -> {

                    selectedCategoryId = category.getId();
                });

                binding.llCategory.addView(mBinding.getRoot());
            }
        });
    }

    private void setupListeners() {
        binding.btnSaveExpense.setOnClickListener(v -> saveExpense());

        binding.tvAddCategory.setOnClickListener(v -> handleAddCategory());
    }

    private void saveExpense() {

        String amountStr = binding.etAmount.getText().toString();

        if (amountStr.isEmpty()) {
            binding.etAmount.setError("Enter amount");
            return;
        }

        double amount = Double.parseDouble(amountStr);

        String note = binding.etNote.getText().toString();



//        viewModel.saveExpense(
//                amount,
//                note,
//                date,
//                selectedCategoryId,
//                "Cash"
//        );

        Toast.makeText(getContext(), "Expense Saved", Toast.LENGTH_SHORT).show();
    }


    private void populateEmojiList(RecyclerView rvEmoji, TextView emojiTextView, CategoryEntity categoryEntity) {

        EmojiAdapter adapter = new EmojiAdapter(
                CategoryIconEnum.values(),
                selectedIconEnum -> {
                    categoryEntity.setIcon(selectedIconEnum.name());
                    rvEmoji.setVisibility(View.GONE);
                }, getContext()
        );

        rvEmoji.setLayoutManager(new GridLayoutManager(getContext(), 6));
        rvEmoji.setAdapter(adapter);
    }

    private void handleAddCategory() {

        CategoryEntity categoryEntity = new CategoryEntity();
        DialogAddCategoryBinding binding =
                DialogAddCategoryBinding.inflate(LayoutInflater.from(getContext()));
        populateEmojiList(binding.rvEmoji, binding.tvAddIcon, categoryEntity);

        ColorAdapter adapter = new ColorAdapter(
                CategoryColorPalette.COLORS,
                selectedColor -> {
                    String backgroundColor;

                    // generate background
                    backgroundColor =
                            ColorUtils.generateLightColor(selectedColor);
                    categoryEntity.setColor(selectedColor);
                    categoryEntity.setBackgroundColor(backgroundColor);

                });

        binding.rvColors.setLayoutManager(new GridLayoutManager(getContext(), 4));

        binding.rvColors.setAdapter(adapter);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setTitle("Add Category")
                .setView(binding.getRoot())
                .setPositiveButton("Save", (dialog, which) -> {
                })
                .setNegativeButton("Cancel", null)
                .show();

        binding.tvAddIcon.setOnClickListener(v -> {
            binding.rvEmoji.setVisibility(View.VISIBLE);
        });

        binding.btnDone.setOnClickListener(v -> {
            String name = binding.etCategoryName.getText().toString();

            if (!name.isEmpty()) {
                categoryEntity.setName(name);
                viewModel.saveCategory(categoryEntity);
            }
            alertDialog.dismiss();
        });
    }


    private void checkCameraPermissionThenLaunch() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            launchCamera();
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA);
        }
    }

    private void launchCamera() {
        pendingCameraUri = createCameraUri();
        captureFromCamera.launch(pendingCameraUri);
    }

    private Uri createCameraUri() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "img_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        return requireContext().getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void displayImage(String filePath) {
        if (binding == null) return; // Guard: LiveData can emit after onDestroyView
        Glide.with(requireContext())
                .load(new File(filePath))
                .centerCrop()
                .into(binding.ivImage);
    }
}