
package com.fsck.k9.ui.messageview;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsck.k9.R;

import org.openintents.openpgp.OpenPgpSignatureResult;

public class MessageOpenPgpView extends LinearLayout {

    private Context mContext;

    private ImageView mResultEncryptionIcon;
    private TextView mResultEncryptionText;
    private ImageView mResultSignatureIcon;
    private TextView mResultSignatureText;
    private LinearLayout mResultSignatureLayout;
    private TextView mResultSignatureName;
    private TextView mResultSignatureEmail;

//    private PendingIntent mMissingKeyPI;

    private static final int REQUEST_CODE_DECRYPT_VERIFY = 12;

    public MessageOpenPgpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void setupChildViews() {
        mResultEncryptionIcon = (ImageView) findViewById(R.id.result_encryption_icon);
        mResultEncryptionText = (TextView) findViewById(R.id.result_encryption_text);
        mResultSignatureIcon = (ImageView) findViewById(R.id.result_signature_icon);
        mResultSignatureText = (TextView) findViewById(R.id.result_signature_text);
        mResultSignatureLayout = (LinearLayout) findViewById(R.id.result_signature_layout);
        mResultSignatureName = (TextView) findViewById(R.id.result_signature_name);
        mResultSignatureEmail = (TextView) findViewById(R.id.result_signature_email);

//        mGetKeyButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getMissingKey();
//            }
//        });
    }

//    public void setFragment(Fragment fragment) {
//        mFragment = (MessageViewFragment) fragment;
//    }

    /**
     * Fill the decrypt layout with signature data, if known, make controls
     * visible, if they should be visible.
     */
    public void updateLayout(final OpenPgpSignatureResult signatureResult,
                             boolean decryptedData,
                             PendingIntent getMissingKeyIntent) {

        if (decryptedData) {
            // encrypted-only
            setStatusImage(mContext, mResultEncryptionIcon, mResultEncryptionText, STATE_ENCRYPTED);

//            MessageOpenPgpView.this.setBackgroundColor(mContext.getResources().getColor(
//                    R.color.openpgp_blue));
//            mText.setText(R.string.openpgp_successful_decryption);

        }

        if (signatureResult != null) {
            switch (signatureResult.getStatus()) {
                case OpenPgpSignatureResult.SIGNATURE_ERROR:
                    setStatusImage(mContext, mResultSignatureIcon, mResultSignatureText, STATE_INVALID);
//                    // TODO: signature error but decryption works?
//                    mText.setText(R.string.openpgp_signature_invalid);
//                    MessageOpenPgpView.this.setBackgroundColor(mContext.getResources().getColor(
//                            R.color.openpgp_red));
//
//                    mGetKeyButton.setVisibility(View.GONE);
//                    mSignatureStatusImage.setImageResource(R.drawable.overlay_error);
//                    mSignatureLayout.setVisibility(View.GONE);
                    break;

                case OpenPgpSignatureResult.SIGNATURE_SUCCESS_CERTIFIED:
                    setStatusImage(mContext, mResultSignatureIcon, mResultSignatureText, STATE_VERIFIED);

//                    if (signatureResult.isSignatureOnly()) {
//                        mText.setText(R.string.openpgp_signature_valid_certified);
//                    } else {
//                        mText.setText(R.string.openpgp_successful_decryption_valid_signature_certified);
//                    }
//                    MessageOpenPgpView.this.setBackgroundColor(mFragment.getResources().getColor(
//                            R.color.openpgp_green));
//
//                    mGetKeyButton.setVisibility(View.GONE);
//                    mSignatureUserId.setText(signatureResult.getUserId());
//                    mSignatureStatusImage.setImageResource(R.drawable.overlay_ok);
//                    mSignatureLayout.setVisibility(View.VISIBLE);

                    break;

                case OpenPgpSignatureResult.SIGNATURE_UNKNOWN_PUB_KEY:
                    setStatusImage(mContext, mResultSignatureIcon, mResultSignatureText, STATE_UNKNOWN_KEY);

//                    if (signatureResult.isSignatureOnly()) {
//                        mText.setText(R.string.openpgp_signature_unknown_text);
//                    } else {
//                        mText.setText(R.string.openpgp_successful_decryption_unknown_signature);
//                    }
//                    MessageOpenPgpView.this.setBackgroundColor(mFragment.getResources().getColor(
//                            R.color.openpgp_orange));
//
//                    mGetKeyButton.setVisibility(View.VISIBLE);
//                    mSignatureUserId.setText(R.string.openpgp_signature_unknown);
//                    mSignatureStatusImage.setImageResource(R.drawable.overlay_error);
//                    mSignatureLayout.setVisibility(View.VISIBLE);

                    break;

                case OpenPgpSignatureResult.SIGNATURE_SUCCESS_UNCERTIFIED:
                    setStatusImage(mContext, mResultSignatureIcon, mResultSignatureText, STATE_UNVERIFIED);

//                    if (signatureResult.isSignatureOnly()) {
//                        mText.setText(R.string.openpgp_signature_valid_uncertified);
//                    } else {
//                        mText.setText(R.string.openpgp_successful_decryption_valid_signature_uncertified);
//                    }
//                    MessageOpenPgpView.this.setBackgroundColor(mFragment.getResources().getColor(
//                            R.color.openpgp_orange));
//
//                    mGetKeyButton.setVisibility(View.GONE);
//                    mSignatureUserId.setText(signatureResult.getUserId());
//                    mSignatureStatusImage.setImageResource(R.drawable.overlay_ok);
//                    mSignatureLayout.setVisibility(View.VISIBLE);

                    break;

                default:
                    break;
            }

        }
    }

    public static final int STATE_REVOKED = 1;
    public static final int STATE_EXPIRED = 2;
    public static final int STATE_VERIFIED = 3;
    public static final int STATE_UNAVAILABLE = 4;
    public static final int STATE_ENCRYPTED = 5;
    public static final int STATE_NOT_ENCRYPTED = 6;
    public static final int STATE_UNVERIFIED = 7;
    public static final int STATE_UNKNOWN_KEY = 8;
    public static final int STATE_INVALID = 9;
    public static final int STATE_NOT_SIGNED = 10;

    public static void setStatusImage(Context context, ImageView statusIcon, int state) {
        setStatusImage(context, statusIcon, null, state);
    }

    /**
     * Sets status image based on constant
     */
    public static void setStatusImage(Context context, ImageView statusIcon, TextView statusText,
                                      int state) {
        switch (state) {
            /** GREEN: everything is good **/
            case STATE_VERIFIED: {
                statusIcon.setImageDrawable(
                        context.getResources().getDrawable(R.drawable.status_signature_verified_cutout));
                int color = R.color.openpgp_green;
                statusIcon.setColorFilter(context.getResources().getColor(color),
                        PorterDuff.Mode.SRC_IN);
                if (statusText != null) {
                    statusText.setTextColor(context.getResources().getColor(color));
                }
                break;
            }
            case STATE_ENCRYPTED: {
                statusIcon.setImageDrawable(
                        context.getResources().getDrawable(R.drawable.status_lock_closed));
                int color = R.color.openpgp_green;
                statusIcon.setColorFilter(context.getResources().getColor(color),
                        PorterDuff.Mode.SRC_IN);
                if (statusText != null) {
                    statusText.setTextColor(context.getResources().getColor(color));
                }
                break;
            }
            /** ORANGE: mostly bad... **/
            case STATE_UNVERIFIED: {
                statusIcon.setImageDrawable(
                        context.getResources().getDrawable(R.drawable.status_signature_unverified_cutout));
                int color = R.color.openpgp_orange;
                statusIcon.setColorFilter(context.getResources().getColor(color),
                        PorterDuff.Mode.SRC_IN);
                if (statusText != null) {
                    statusText.setTextColor(context.getResources().getColor(color));
                }
                break;
            }
            case STATE_UNKNOWN_KEY: {
                statusIcon.setImageDrawable(
                        context.getResources().getDrawable(R.drawable.status_signature_unknown_cutout));
                int color = R.color.openpgp_orange;
                statusIcon.setColorFilter(context.getResources().getColor(color),
                        PorterDuff.Mode.SRC_IN);
                if (statusText != null) {
                    statusText.setTextColor(context.getResources().getColor(color));
                }
                break;
            }
            /** RED: really bad... **/
            case STATE_REVOKED: {
                statusIcon.setImageDrawable(
                        context.getResources().getDrawable(R.drawable.status_signature_revoked_cutout));
                int color = R.color.openpgp_red;
//                if (unobtrusive) {
//                    color = R.color.bg_gray;
//                }
                statusIcon.setColorFilter(context.getResources().getColor(color),
                        PorterDuff.Mode.SRC_IN);
                if (statusText != null) {
                    statusText.setTextColor(context.getResources().getColor(color));
                }
                break;
            }
            case STATE_EXPIRED: {
                statusIcon.setImageDrawable(
                        context.getResources().getDrawable(R.drawable.status_signature_expired_cutout));
                int color = R.color.openpgp_red;
//                if (unobtrusive) {
//                    color = R.color.bg_gray;
//                }
                statusIcon.setColorFilter(context.getResources().getColor(color),
                        PorterDuff.Mode.SRC_IN);
                if (statusText != null) {
                    statusText.setTextColor(context.getResources().getColor(color));
                }
                break;
            }
            case STATE_NOT_ENCRYPTED: {
                statusIcon.setImageDrawable(
                        context.getResources().getDrawable(R.drawable.status_lock_open));
                int color = R.color.openpgp_red;
                statusIcon.setColorFilter(context.getResources().getColor(color),
                        PorterDuff.Mode.SRC_IN);
                if (statusText != null) {
                    statusText.setTextColor(context.getResources().getColor(color));
                }
                break;
            }
            case STATE_NOT_SIGNED: {
                statusIcon.setImageDrawable(
                        context.getResources().getDrawable(R.drawable.status_signature_unknown_cutout));
                int color = R.color.openpgp_red;
                statusIcon.setColorFilter(context.getResources().getColor(color),
                        PorterDuff.Mode.SRC_IN);
                if (statusText != null) {
                    statusText.setTextColor(context.getResources().getColor(color));
                }
                break;
            }
            case STATE_INVALID: {
                statusIcon.setImageDrawable(
                        context.getResources().getDrawable(R.drawable.status_signature_invalid_cutout));
                int color = R.color.openpgp_red;
                statusIcon.setColorFilter(context.getResources().getColor(color),
                        PorterDuff.Mode.SRC_IN);
                if (statusText != null) {
                    statusText.setTextColor(context.getResources().getColor(color));
                }
                break;
            }
        }
    }

//    private void getMissingKey() {
//        try {
//            mFragment.getActivity().startIntentSenderForResult(
//                    mMissingKeyPI.getIntentSender(),
//                    REQUEST_CODE_DECRYPT_VERIFY, null, 0, 0, 0);
//        } catch (SendIntentException e) {
//            Log.e(K9.LOG_TAG, "SendIntentException", e);
//        }
//    }

}