package com.jimvang.ndkmasterdetail.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by James Van Gaasbeck on 1/18/19.
 */
public class MovieDetailItem
{
    public String name;
    public float score;
    public List<ActorItem> actorItemList = new ArrayList<>();
    public String description;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getScore()
    {
        return score;
    }

    public void setScore(float score)
    {
        this.score = score;
    }

    public List<ActorItem> getActorItemList()
    {
        return actorItemList;
    }

    public void setActorItemList(List<ActorItem> actorItemList)
    {
        this.actorItemList = actorItemList;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
