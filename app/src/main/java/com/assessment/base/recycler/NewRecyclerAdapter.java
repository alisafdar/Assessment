package com.assessment.base.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public abstract class NewRecyclerAdapter<T> extends RecyclerView.Adapter<NewRecyclerAdapter.ViewHolder<T>>{


    public static final int TYPE_HEADER = 7898;
    public static final int TYPE_FOOTER = 7899;

    private int headers = 0;
    private int footers = 0;
    private List<T> items = new ArrayList<>();

    private RecyclerView.LayoutManager manager;
    private LayoutInflater inflater;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            return getGridSpan(position);
        }
    };

    public int getRealItemCount() {
        return items.size();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void add(int position, T item) {
        items.add(position, item);
        notifyItemInserted(position);
        int positionStart = position + getHeadersCount();
        int itemCount = items.size() - position;
        notifyItemRangeChanged(positionStart, itemCount);
    }

    public void add(T item) {
        items.add(item);
        notifyItemInserted(items.size() - 1 + getHeadersCount());
    }

    public void addAll(List<? extends T> items) {
        final int size = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(size + getHeadersCount(), items.size());
    }

    public void addAll(int position, List<? extends T> items) {
        this.items.addAll(position, items);
        notifyItemRangeInserted(position + getHeadersCount(), items.size());
    }

    public void set(int position, T item) {
        items.set(position, item);
        notifyItemChanged(position + getHeadersCount());
    }

    public void removeChild(int position) {
        items.remove(position);
        notifyItemRemoved(position + getHeadersCount());
        int positionStart = position + getHeadersCount();
        int itemCount = items.size() - position;
        notifyItemRangeChanged(positionStart, itemCount);
    }

    @CallSuper
    public void clear() {
        final int size = items.size();
        items.clear();
        notifyItemRangeRemoved(getHeadersCount(), size);
    }

    public void moveChildTo(int fromPosition, int toPosition) {
        if (toPosition != -1 && toPosition < items.size()) {
            final T item = items.remove(fromPosition);
            items.add(toPosition, item);
            notifyItemMoved(getHeadersCount() + fromPosition, getHeadersCount() + toPosition);
            int positionStart = fromPosition < toPosition ? fromPosition : toPosition;
            int itemCount = Math.abs(fromPosition - toPosition) + 1;
            notifyItemRangeChanged(positionStart + getHeadersCount(), itemCount);
        }
    }

    public int indexOf(T object) {
        return items.indexOf(object);
    }

    public List<T> getItems() {
        return items;
    }

    @Override
    public NewRecyclerAdapter.ViewHolder<T> onCreateViewHolder(ViewGroup viewGroup, int type) {
        switch (type) {
            case TYPE_HEADER:
                return onCreateHeaderViewHolder(viewGroup);
            case TYPE_FOOTER:
                return onCreateFooterViewHolder(viewGroup);
            default:
                return onCreateItemViewHolder(viewGroup, type);
        }
    }

    protected void setHeaderFooterLayoutParams(ViewGroup viewGroup) {
        ViewGroup.LayoutParams layoutParams;
        if (manager instanceof LinearLayoutManager) {
            int orientation = ((LinearLayoutManager) manager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        } else {
            layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        viewGroup.setLayoutParams(layoutParams);
    }

    @Override
    public final void onBindViewHolder(NewRecyclerAdapter.ViewHolder<T> vh, int position) {
        //check what type of view our position is
        if (isHeader(position)) {
            onBindHeaderViewHolder(vh, position, getItemType(position));
        } else if (isFooter(position)) {
//            onBindFooterViewHolder(vh, position - getRealItemCount() - headers, getItemType(position));
            onBindFooterViewHolder(vh, position - getRealItemCount() - headers, TYPE_FOOTER);
        } else {
            //it's one of our products, display as required
            onBindItemViewHolder(vh, position - headers, getItemType(position));
        }
    }

    protected void onBindHeaderViewHolder(ViewHolder<T> _holder, int _position, int _itemType) {

    }

    protected void onBindFooterViewHolder(ViewHolder<T> _holder, int _position, int _itemType) {

    }


    protected LayoutInflater getInflater() {
        return inflater;
    }

    private void prepareHeaderFooter(HeaderFooterViewHolder vh, View view) {
        //if it's a staggered grid, span the whole layout
        if (manager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setFullSpan(true);
            vh.itemView.setLayoutParams(layoutParams);
        }
        //if the view already belongs to another layout, remove it
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        //empty out our FrameLayout and replace with our header/footer
        ((ViewGroup) vh.itemView).removeAllViews();
        ((ViewGroup) vh.itemView).addView(view);
    }

    private boolean isHeader(int position) {
        return (position < headers);
    }

    private boolean isFooter(int position) {
        return footers > 0 && (position >= getHeadersCount() + getRealItemCount());
    }

    protected ViewHolder<T> onCreateItemViewHolder(ViewGroup parent, int type) {
        return viewHolder(inflater.inflate(layoutId(type), parent, false), type);
    }

    protected ViewHolder<T> onCreateHeaderViewHolder(View view) {
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        setHeaderFooterLayoutParams(frameLayout);
        return new HeaderFooterViewHolder<>(frameLayout);
    }

    protected ViewHolder<T> onCreateFooterViewHolder(ViewGroup _viewGroup) {
        FrameLayout frameLayout = new FrameLayout(_viewGroup.getContext());
        setHeaderFooterLayoutParams(frameLayout);
        return new HeaderFooterViewHolder<>(frameLayout);
    }

    @Override
    public int getItemCount() {
        return headers + getRealItemCount() + footers;
    }

    public final boolean isEmpty() {
        return getRealItemCount() == 0;
    }

    @Override
    final public int getItemViewType(int position) {
        //check what type our position is, based on the assumption that the order is headers > products > footers
        if (isHeader(position)) {
            return TYPE_HEADER;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int type = getItemType(position);
        if (type == TYPE_HEADER || type == TYPE_FOOTER) {
            throw new IllegalArgumentException("Item type cannot equal " + TYPE_HEADER + " or " + TYPE_FOOTER);
        }
        return type;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (manager == null) {
            setManager(recyclerView.getLayoutManager());
        }
        if (inflater == null) {
            this.inflater = LayoutInflater.from(recyclerView.getContext());
        }
    }

    private void setManager(RecyclerView.LayoutManager manager) {
        this.manager = manager;
        if (this.manager instanceof GridLayoutManager) {
            ((GridLayoutManager) this.manager).setSpanSizeLookup(spanSizeLookup);
        } else if (this.manager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) this.manager).setGapStrategy(
                    StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        }
    }

    protected int getGridSpan(int position) {
        if (isHeader(position) || isFooter(position)) {
            return getMaxGridSpan();
        }
        position -= headers;
        if (getItem(position) instanceof SpanItemInterface) {
            return ((SpanItemInterface) getItem(position)).getGridSpan();
        }
        return 1;
    }

    protected int getMaxGridSpan() {
        if (manager instanceof GridLayoutManager) {
            return ((GridLayoutManager) manager).getSpanCount();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) manager).getSpanCount();
        }
        return 1;
    }

    //add a footer to the dateAdapter
    protected void addFooter() {
        footers++;
        notifyItemInserted(headers + getItemCount() + footers - 1);
    }

    protected void notifyFooter() {
        notifyItemRangeChanged(headers + getRealItemCount(), footers - 1);
    }

    protected void notifyFooter(int _position) {
        notifyItemChanged(headers + getRealItemCount() + _position - 1);
    }

    //remove footer from dateAdapter
    protected void removeFooter() {
        footers = 0;
        notifyItemRemoved(headers + getItemCount() + footers - 1);
    }

    public int getHeadersCount() {
        return headers;
    }

    public int getFootersCount() {
        return footers;
    }

    protected int getItemType(int position) {
        return 0;
    }

    protected abstract void onBindItemViewHolder(ViewHolder<T> viewHolder, int position, int type);

    protected abstract ViewHolder<T> viewHolder(View _view, int _type);

    @LayoutRes
    protected abstract int layoutId(int type);

    public interface SpanItemInterface {
        int getGridSpan();
    }

    //our header/footer RecyclerView.ViewHolder is just a FrameLayout
    public static class HeaderFooterViewHolder<T> extends NewRecyclerAdapter.ViewHolder<T> {

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(T _data) {
            //not implemented
        }
    }

    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

        protected Context context;
        private View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            rootView = itemView;
        }


        protected final <V extends View> V findView(@IdRes int id) {
            return (V) rootView.findViewById(id);
        }


        public abstract void setData(@NonNull T _data);
    }
}
