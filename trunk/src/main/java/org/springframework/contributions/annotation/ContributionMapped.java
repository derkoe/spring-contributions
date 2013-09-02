package org.springframework.contributions.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used inside of spring java configuration classes to add a bean to a mapped
 * contribution.
 * 
 * @author Ortwin Probst
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContributionMapped
{

	/**
	 * The name of the bean to be contributed (added to the mapped contribution. This property is
	 * optional. If not provided the name of the bean definition method will be considered as the
	 * beans name.
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * The name of the mapped contribution bean where the contributed bean will be added to
	 * 
	 * @return
	 */
	String to();

	/**
	 * The value which will be used as the key in the mapped contribution for the bean to
	 * be contributed.
	 * 
	 * @return
	 */
	String key() default "";

	/**
	 * The class which will be used as the key in the mapped contribution for the bean to be
	 * contributed.
	 * 
	 * @return
	 */
	Class<?> keyClass() default ContributionMapped.class;

	/**
	 * The value of the Enum used as key
	 * 
	 * @return
	 */
	String keyEnumValue() default "";
	
	/**
	 * The class of the Enum used as key
	 * 
	 * @return
	 */
	Class<? extends Enum<?>> keyEnumClass() default NoEnumKey.class;

	/**
	 * this enum is used as default return value for {@link ContributionMapped#keyEnumClass()} to
	 * indicate that no enum key was defined in the configuration.
	 */
	enum NoEnumKey{}
}
