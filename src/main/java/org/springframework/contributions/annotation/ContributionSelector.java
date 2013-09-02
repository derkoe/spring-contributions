package org.springframework.contributions.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * This {@link ImportSelector} implementation returns the configuration class {@link AnnotationContributionConfig}
 * and can be used in annotations which should be used for enabling Spring-Contributions in configuration classes.
 * 
 * @author Ortwin Probst
 *
 */
public class ContributionSelector implements ImportSelector
{

	public String[] selectImports(AnnotationMetadata importingClassMetadata)
	{
		return new String[] { AnnotationContributionConfig.class.getName() };
	}

}
