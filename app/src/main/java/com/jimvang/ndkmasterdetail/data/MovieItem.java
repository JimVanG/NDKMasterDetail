package com.jimvang.ndkmasterdetail.data;

import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * Created by James Van Gaasbeck on 1/18/19.
 */
public class MovieItem
{
    public String name;
    public int lastUpdated;

    public MovieItem(String name, int lastUpdated)
    {
        this.name = name;
        this.lastUpdated = lastUpdated;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getLastUpdated()
    {
        return lastUpdated;
    }

    public void setLastUpdated(int lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        MovieItem movieItem = (MovieItem) o;
        return lastUpdated == movieItem.lastUpdated &&
                Objects.equals(name, movieItem.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, lastUpdated);
    }

    @NonNull
    @Override
    public String toString()
    {
        return "MovieItem{" +
                "name='" + name + '\'' +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
