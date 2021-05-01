package com.assessment.base.recycler;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected T item;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void updateItem(final T item) {
        this.item = item;
        fillFields();
    }

    protected abstract void fillFields();
}
