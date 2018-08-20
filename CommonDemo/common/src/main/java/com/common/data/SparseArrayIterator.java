package com.common.data;

import android.util.SparseArray;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SparseArrayIterator implements ListIterator<T> {
    SparseArray<T> mSparseArray;
    private int mCursor = -1;
    private boolean mCurserNoPlace = true;

    public SparseArrayIterator(SparseArray sparseArray) {
        mSparseArray = sparseArray;
    }

    public int currentKey() {
        if (!mCurserNoPlace) {
            return mSparseArray.keyAt(mCursor);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void remove() {
        if (!mCurserNoPlace) {
            mSparseArray.remove(currentKey());
            mCurserNoPlace = false;
            mCursor--;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean hasNext() {
        return mCursor < mSparseArray.size() - 1;
    }

    @Override
    public T next() {
        if (hasNext()) {
            if (mCurserNoPlace) {
                mCurserNoPlace = false;
            }
            mCursor++;
            return mSparseArray.get(currentKey());
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int nextIndex() {
        if (hasNext()) {
            return mCursor + 1;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean hasPrevious() {
        return mCurserNoPlace && mCursor >= 0 || mCursor > 0;
    }


    @Override
    public T previous() {
        if (hasPrevious()) {
            if (mCurserNoPlace) {
                mCurserNoPlace = false;
            } else {
                mCursor--;
            }

            return mSparseArray.get(currentKey());
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int previousIndex() {
        return --mCursor;
    }

    @Override
    public void set(T object) {
        if (!mCurserNoPlace) {
            mSparseArray.put(currentKey(), object);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void add(T t) {
        throw new UnsupportedOperationException();
    }
}
