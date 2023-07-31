package com.fongmi.android.tv.ui.custom;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fongmi.android.tv.bean.Result;

public class CustomScroller extends RecyclerView.OnScrollListener {

    private final Callback callback;
    private boolean loading;
    private boolean enable;
    private int page;

    public CustomScroller(Callback callback) {
        this.callback = callback;
        this.enable = true;
        this.page = 1;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView view, int dx, int dy) {
        if (!isEnable() || isLoading() || view.getScrollState() == RecyclerView.SCROLL_STATE_IDLE || callback == null) return;
        if (!view.canScrollVertically(1) && dy > 0) callback.onLoadMore(String.valueOf(++page));
    }

    public void reset() {
        enable = true;
        page = 1;
    }

    public int addPage() {
        return ++page;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setEnable(int pageCount) {
        this.enable = page < pageCount || pageCount == 0;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void endLoading(Result result) {
        if (result.getList().isEmpty()) page--;
        setEnable(result.getPageCount());
        setLoading(false);
    }

    public interface Callback {
        void onLoadMore(String page);
    }
}
