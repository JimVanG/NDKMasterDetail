package com.jimvang.ndkmasterdetail.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * Created by James Van Gaasbeck on 1/18/19.
 */
public class MovieDetailItem
{
    public String name;
    public float score;
    public List<ActorItem> actorItemList;
    public String description;

    public MovieDetailItem(String name, float score, String description)
    {
        this(name, score, new ArrayList<ActorItem>(0), description);
    }

    public MovieDetailItem(
            String name,
            float score,
            List<ActorItem> actorItemList,
            String description)
    {
        this.name = name;
        this.score = score;
        this.actorItemList = actorItemList;
        this.description = description;
    }

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

    @NonNull
    @Override
    public String toString()
    {
        return "MovieDetailItem{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", actorItemList=" + actorItemList +
                ", description='" + description + '\'' +
                '}';
    }
}
