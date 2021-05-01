package com.assessment.base.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BaseAdapterDelegate<T, H extends BaseViewHolder> extends AdapterDelegate<T> {

    @Override
    protected boolean isForViewType(@NonNull T items, int position) {
        return true;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return getHolder(LayoutInflater.from(parent.getContext()).inflate(getHolderLayoutRes(), parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull T items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ((BaseViewHolder) holder).updateItem(items);
    }

    protected abstract @LayoutRes int getHolderLayoutRes();
    protected abstract H getHolder(@NotNull final View inflatedView);
}