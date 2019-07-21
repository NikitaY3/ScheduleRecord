package com.schedule.record.app.clock;

import android.app.AlarmManager;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyAlarmManagerList implements List<AlarmManager> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains( Object o) {
        return false;
    }

    @Override
    public Iterator<AlarmManager> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(AlarmManager alarmManager) {
        return false;
    }

    @Override
    public boolean remove( Object o) {
        return false;
    }

    @Override
    public boolean containsAll( Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll( Collection<? extends AlarmManager> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends AlarmManager> c) {
        return false;
    }

    @Override
    public boolean removeAll( Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll( Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public AlarmManager get(int index) {
        return null;
    }

    @Override
    public AlarmManager set(int index, AlarmManager element) {
        return null;
    }

    @Override
    public void add(int index, AlarmManager element) {

    }

    @Override
    public AlarmManager remove(int index) {
        return null;
    }

    @Override
    public int indexOf( Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf( Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<AlarmManager> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<AlarmManager> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<AlarmManager> subList(int fromIndex, int toIndex) {
        return null;
    }
}
