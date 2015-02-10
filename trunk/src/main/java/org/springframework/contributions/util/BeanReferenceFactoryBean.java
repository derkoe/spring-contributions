/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.contributions.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Copy of org.springframework.beans.factory.config.BeanReferenceFactoryBean since it was removed in Spring 4.1.
 */
public class BeanReferenceFactoryBean implements SmartFactoryBean<Object>, BeanFactoryAware {

	private String targetBeanName;

	private BeanFactory beanFactory;


	/**
	 * Set the name of the target bean.
	 * <p>This property is required. The value for this property can be
	 * substituted through a placeholder, in combination with Spring's
	 * PropertyPlaceholderConfigurer.
	 * @param targetBeanName the name of the target bean
	 * @see PropertyPlaceholderConfigurer
	 */
	public void setTargetBeanName(String targetBeanName) {
		this.targetBeanName = targetBeanName;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
		if (this.targetBeanName == null) {
			throw new IllegalArgumentException("'targetBeanName' is required");
		}
		if (!this.beanFactory.containsBean(this.targetBeanName)) {
			throw new NoSuchBeanDefinitionException(this.targetBeanName, this.beanFactory.toString());
		}
	}


	@Override
	public Object getObject() throws BeansException {
		if (this.beanFactory == null) {
			throw new FactoryBeanNotInitializedException();
		}
		return this.beanFactory.getBean(this.targetBeanName);
	}

	@Override
	public Class<?> getObjectType() {
		if (this.beanFactory == null) {
			return null;
		}
		return this.beanFactory.getType(this.targetBeanName);
	}

	@Override
	public boolean isSingleton() {
		if (this.beanFactory == null) {
			throw new FactoryBeanNotInitializedException();
		}
		return this.beanFactory.isSingleton(this.targetBeanName);
	}

	@Override
	public boolean isPrototype() {
		if (this.beanFactory == null) {
			throw new FactoryBeanNotInitializedException();
		}
		return this.beanFactory.isPrototype(this.targetBeanName);
	}

	@Override
	public boolean isEagerInit() {
		return false;
	}

}
