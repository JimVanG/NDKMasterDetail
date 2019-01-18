package com.jimvang.ndkmasterdetail.data;

import java.util.Objects;

/**
 * Created by James Van Gaasbeck on 1/18/19.
 */
class ActorItem
{
    public String name;
    public int age;
    //optional challenge 1: Load image from URL
    public String imageUrl;

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
        ActorItem actorItem = (ActorItem) o;
        return age == actorItem.age &&
                Objects.equals(name, actorItem.name) &&
                Objects.equals(imageUrl, actorItem.imageUrl);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, age, imageUrl);
    }
}
