package org.springframework.contributions.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * This annotation can be used to activate Spring-Contribution functionality in a spring java configuration
 * class annotated with the {@link Configuration} annotation.
 * 
 * @author Ortwin Probst
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ContributionSelector.class)
public @interface EnableContributions
{

}
