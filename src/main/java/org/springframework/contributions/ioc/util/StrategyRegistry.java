// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.springframework.contributions.ioc.util;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Modified Copy of {@link org.apache.tapestry5.ioc.util.StrategyRegistry<A>} from Tapestry in version 5.2.5
 * 
 * A key component in implementing the "Gang of Four" Strategy pattern. A StrategyRegistry will match up a given input
 * type with a registered strategy for that type.
 *
 * @param <A> the type of the strategy adapter
 */
public final class StrategyRegistry<A>
{
    private final Class<A> adapterType;

    private final boolean allowNonMatch;

    private final Map<Class, A> registrations = CollectionFactory.newMap();

    private final Map<Class, A> cache = CollectionFactory.newConcurrentMap();

    /**
     * Used to identify types for which there is no matching adapter; we're using it as if it were a ConcurrentSet.
     */
    private final Map<Class, Boolean> unmatched = CollectionFactory.newConcurrentMap();

    private StrategyRegistry(Class<A> adapterType, Map<Class, A> registrations, boolean allowNonMatch)
    {
        this.adapterType = adapterType;
        this.allowNonMatch = allowNonMatch;

        this.registrations.putAll(registrations);
    }

    /**
     * Creates a strategy registry for the given adapter type. The registry will be configured to require matches.
     *
     * @param adapterType   the type of adapter retrieved from the registry
     * @param registrations map of registrations (the contents of the map are copied)
     */
    public static <A> StrategyRegistry<A> newInstance(Class<A> adapterType,
                                                      Map<Class, A> registrations)
    {
        return newInstance(adapterType, registrations, false);
    }

    /**
     * Creates a strategy registry for the given adapter type.
     *
     * @param adapterType   the type of adapter retrieved from the registry
     * @param registrations map of registrations (the contents of the map are copied)
     * @param allowNonMatch if true, then the registry supports non-matches when retrieving an adapter
     */
    public static <A> StrategyRegistry<A> newInstance(
            Class<A> adapterType,
            Map<Class, A> registrations, boolean allowNonMatch)
    {
        return new StrategyRegistry<A>(adapterType, registrations, allowNonMatch);
    }

    public void clearCache()
    {
        cache.clear();
        unmatched.clear();
    }

    public Class<A> getAdapterType()
    {
        return adapterType;
    }

    /**
     * Gets an adapter for an object. Searches based on the value's class, unless the value is null, in which case, a
     * search on class void is used.
     *
     * @param value for which an adapter is needed
     * @return the adapter for the value or null if not found (and allowNonMatch is true)
     * @throws IllegalArgumentException if no matching adapter may be found and allowNonMatch is false
     */

    public A getByInstance(Object value)
    {
        return get(value == null ? void.class : value.getClass());
    }

    /**
     * Searches for an adapter corresponding to the given input type.
     *
     * @param type the type to search
     * @return the adapter for the type or null if not found (and allowNonMatch is true)
     * @throws IllegalArgumentException if no matching adapter may be found   and allowNonMatch is false
     */
    public A get(Class type)
    {

        A result = cache.get(type);

        if (result != null) return result;

        if (unmatched.containsKey(type)) return null;


        result = findMatch(type);

        // This may be null in the case that there is no match and we're allowing that to not
        // be an error.  That's why we check via containsKey.

        if (result != null)
        {
            cache.put(type, result);
        }
        else
        {
            unmatched.put(type, true);
        }

        return result;
    }

    private A findMatch(Class type)
    {
        for (Class t : new InheritanceSearch(type))
        {
            A result = registrations.get(t);

            if (result != null) return result;
        }

        if (allowNonMatch) return null;

        // Report the error. These things really confused the hell out of people in Tap4, so we're
        // going the extra mile on the exception message.

        List<String> names = CollectionFactory.newList();
        for (Class t : registrations.keySet())
            names.add(t.getName());

        //TODO fix output
        throw new IllegalArgumentException("No strategy adapter (type: " + type + " adapterType: " + adapterType + " names: " + names.toString());
    }

    /**
     * Returns the registered types for which adapters are available.
     */
    public Collection<Class> getTypes()
    {
        return CollectionFactory.newList(registrations.keySet());
    }

    /**
     * @param strategy the strategy
     * @return the class the given strategy was registered for
     */
    public Class getTypeForStrategy(A strategy)
    {
    	for(Entry<Class, A> registration : registrations.entrySet())
    	{
    		if(strategy.equals(registration.getValue()))
    		{
    			return registration.getKey();
    		}
    	}
    	return null;
    }

    @Override
    public String toString()
    {
        return String.format("StrategyRegistry[%s]", adapterType.getName());
    }
}
