package org.springframework.contributions.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Bean;

/**
 * This annotation can be used inside of spring java configuration classes to add a bean to a ordered contribution.
 * 
 * @author Ortwin Probst
 *
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Contribution
{

	/**
	 * The destination bean which will use the mapped contribution.
	 * @return
	 */
	String to();

	/**
	 * The order constraints which will be used to define the position of the bean in the ordered contribution list.
	 * @return
	 */
	String constraints() default "";

	/**
	 * The name of the bean to be contributed defined in the {@link Bean} annotation.
	 * This property is optional since it is also possible to use the name of the bean definition method for naming. 
	 * @return
	 */
	String name() default "";

}
