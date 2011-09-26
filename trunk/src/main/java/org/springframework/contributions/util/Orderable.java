package org.springframework.contributions.util;

/**
 * Interface for an object which can be ordered by the {@link Orderer}.
 * 
 * @author Manfred Hantschel
 */
public interface Orderable
{

    /**
     * Returns the unique id of the entry. Must not contain ',' and must not start or end with white-spaces.
     * 
     * @return the id
     */
    String getOrderableId();

    /**
     * The constraints, may be null. The string must contain match following declaration:
     * 
     * <pre>
     * declaration = ('before:' | 'after:') reference {',' reference}.
     * </pre>
     * 
     * The reference may contain wildcards like '*' and '?', or, if prefixed and suffixed by '/' a regular expression.
     * Direct references (without wildcards or regular expressions) are preferred.
     * 
     * @return the constraints, may be null
     */
    String[] getOrderableConstraints();

}
