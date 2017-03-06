package com.emiancang.emiancang.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emiancang.emiancang.R;

/**
 */
public class StandardDialog extends BaseDialog {

    public StandardDialog(Context context) {
        super(context);
    }

    public StandardDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }
        public StandardDialog create() {
            return create(R.style.BaseDialog);
        }
        public StandardDialog create(int theme){

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final StandardDialog dialog = new StandardDialog(context,
                    theme);
            View layout = inflater.inflate(R.layout.dialog_standard, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            TextView positiveButton = ((TextView) layout.findViewById(R.id.positiveButton));
            TextView negativeButton = ((TextView) layout.findViewById(R.id.negativeButton));
            TextView oneButton = ((TextView) layout.findViewById(R.id.oneButton));

            // set the confirm button
            if (positiveButtonText != null) {
                TextView textView = positiveButton;
                if (negativeButtonText == null) {
                    textView = oneButton;
                    positiveButton.setVisibility(View.GONE);
                }
                textView.setVisibility(View.VISIBLE);
                textView.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    textView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                } else {
                    textView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                positiveButton.setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                TextView textView = negativeButton;
                if (positiveButtonText == null) {
                    textView = oneButton;
                    negativeButton.setVisibility(View.GONE);
                }
                textView.setVisibility(View.VISIBLE);
                textView.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    textView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                } else {
                    textView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                negativeButton.setVisibility(
                        View.GONE);
            }
            if (negativeButtonText == null && positiveButtonText == null) {
                layout.findViewById(R.id.layout_btn).setVisibility(View.GONE);
            } else {
                layout.findViewById(R.id.layout_btn).setVisibility(View.VISIBLE);
            }

            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.layout_message))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.layout_message))
                        .addView(contentView, new LayoutParams(
                                LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }

    }
}
