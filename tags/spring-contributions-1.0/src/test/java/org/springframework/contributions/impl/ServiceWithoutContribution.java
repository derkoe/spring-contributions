package org.springframework.contributions.impl;

import java.util.List;

public class ServiceWithoutContribution
{
    private final List<String> emptyContribution;

    public ServiceWithoutContribution(final List<String> emptyContribution)
    {
        this.emptyContribution = emptyContribution;
    }

    public List<String> getEmptyContribution()
    {
        return emptyContribution;
    }
    
}
