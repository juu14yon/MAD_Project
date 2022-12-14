// Generated by view binder compiler. Do not edit!
package com.ma_dev.budgetcalculator.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.ma_dev.budgetcalculator.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ListrowBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView amountText;

  @NonNull
  public final TextView categoryText;

  @NonNull
  public final TextView idText;

  @NonNull
  public final TextView nameText;

  private ListrowBinding(@NonNull RelativeLayout rootView, @NonNull TextView amountText,
      @NonNull TextView categoryText, @NonNull TextView idText, @NonNull TextView nameText) {
    this.rootView = rootView;
    this.amountText = amountText;
    this.categoryText = categoryText;
    this.idText = idText;
    this.nameText = nameText;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListrowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListrowBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.listrow, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListrowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.amountText;
      TextView amountText = rootView.findViewById(id);
      if (amountText == null) {
        break missingId;
      }

      id = R.id.categoryText;
      TextView categoryText = rootView.findViewById(id);
      if (categoryText == null) {
        break missingId;
      }

      id = R.id.idText;
      TextView idText = rootView.findViewById(id);
      if (idText == null) {
        break missingId;
      }

      id = R.id.nameText;
      TextView nameText = rootView.findViewById(id);
      if (nameText == null) {
        break missingId;
      }

      return new ListrowBinding((RelativeLayout) rootView, amountText, categoryText, idText,
          nameText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
