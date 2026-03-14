package com.hafiz.expense.buddy.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtil {
    private FileUtil() {}

    /**
     * Copies image bytes from any URI (gallery or camera) into app-private storage.
     * Returns the absolute path, or null on failure.
     */
    public static String copyUriToInternalStorage(Context context, Uri uri) {
        try {
            File dir = new File(context.getFilesDir(), "images");
            if (!dir.exists()) dir.mkdirs();

            File dest = new File(dir, System.currentTimeMillis() + ".jpg");

            try (InputStream in = context.getContentResolver().openInputStream(uri);
                 FileOutputStream out = new FileOutputStream(dest)) {

                if (in == null) return null;

                byte[] buffer = new byte[8192];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
            }

            return dest.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
