package com.jimvang.ndkmasterdetail.data;

import java.util.Objects;

/**
 * Created by James Van Gaasbeck on 1/18/19.
 */
public class ActorItem
{
    public String name;
    public int age;
    public String imageUrl;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
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
