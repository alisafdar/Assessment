package com.assessment.base.recycler;

import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected final List<T> dataList = new ArrayList<>();
    private final AdapterDelegatesManager<T> delegatesManager = new AdapterDelegatesManager<>();

    public BaseRecyclerAdapter() {
        fillDelegatesManger(true);
    }

    public BaseRecyclerAdapter(final boolean isFillDelegates) {
        fillDelegatesManger(isFillDelegates);
    }

    protected void fillDelegatesManger(final boolean isFillDelegates) {
        if (isFillDelegates)
            initDelegatesManager(delegatesManager);
    }

    public void addAll(final List<T> dataList) {
        clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void onlyAddAll(final List<T> dataList) {
        this.dataList.addAll(dataList);
    }

    public void removeChild(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        int itemCount = dataList.size() - position;
        notifyItemRangeChanged(position, itemCount);
    }

    @CallSuper
    public void clear() {
        final int size = dataList.size();
        dataList.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(dataList.get(position), position, holder);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(getItem(position), position);
    }

    public T getItem(int position) {

        return dataList.get(position);
    }

    public List<T> getItems() {
        return dataList;
    }

    protected abstract void initDelegatesManager(@NotNull final AdapterDelegatesManager<T> delegatesManager);
}
