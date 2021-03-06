package com.ryantenney.metrics.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import com.yammer.metrics.core.HealthCheck;
import com.yammer.metrics.core.HealthCheckRegistry;

public class HealthCheckBeanPostProcessor implements BeanPostProcessor, Ordered {

	private static final Logger log = LoggerFactory.getLogger(HealthCheckBeanPostProcessor.class);

	private final HealthCheckRegistry healthChecks;

	public HealthCheckBeanPostProcessor(final HealthCheckRegistry healthChecks) {
		this.healthChecks = healthChecks;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof HealthCheck) {
			healthChecks.register((HealthCheck) bean);

			log.debug("Registering HealthCheck bean {}", beanName);
		}

		return bean;
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

}
