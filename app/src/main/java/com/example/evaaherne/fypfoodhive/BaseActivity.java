package com.example.evaaherne.fypfoodhive;

import android.app.ProgressDialog;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
/** Base activity used for progress bar for the login page */

/**Resources for this gathered from link below,
 * https://github.com/firebase/quickstart-android/blob/564601cb1da6435e92e53ff281f883d89d815e8e/database/app/src/main/java/com/google/firebase/quickstart/database/java/MainActivity.java
 *
 */
    public class BaseActivity extends AppCompatActivity {
        @VisibleForTesting
        public ProgressDialog mProgressDialog;

        public void showProgressDialog() {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.loading));
                mProgressDialog.setIndeterminate(true);
            }

            mProgressDialog.show();
        }

        public void hideProgressDialog() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
//
//        public void hideKeyboard(View view) {
//            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (imm != null) {
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//            }
//        }

        @Override
        public void onStop() {
            super.onStop();
            hideProgressDialog();
        }

    }

