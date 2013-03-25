package org.springframework.contributions.impl;

import java.util.List;

public class Hen
{
    private final List<Egg> eggs;
    private final String name;

    public Hen(List<Egg> eggs, String name)
    {
        super();

        this.eggs = eggs;
        this.name = name;
    }

    public List<Egg> getEggs()
    {
        return eggs;
    }

    public String getName()
    {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        return String.format("%s has %d eggs: %s", getName(), getEggs().size(), getEggs());
    }
}
