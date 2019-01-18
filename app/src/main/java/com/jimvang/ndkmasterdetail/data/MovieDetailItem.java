package com.jimvang.ndkmasterdetail.data;

import java.util.List;
import java.util.Objects;

/**
 * Created by James Van Gaasbeck on 1/18/19.
 */
public class MovieDetailItem
{
    public String name;
    public float score;
    public List<ActorItem> actorItemList;
    public String description;

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
        MovieDetailItem that = (MovieDetailItem) o;
        return Float.compare(that.score, score) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(actorItemList, that.actorItemList) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, score, actorItemList, description);
    }
}
