package com.estafet.microservices.scrum.basic.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.estafet.microservices.scrum.basic.ui.config.ServiceName;
import com.estafet.microservices.scrum.basic.ui.model.ServiceHealth;

import io.opentracing.Tracer;

@Service
public class HealthCheckService {
	
	@Autowired
	private Tracer tracer;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public ServiceHealth serviceHealthCheck(ServiceName serviceName) {
		tracer.activeSpan().setTag("serviceName", serviceName.getServiceName());
		
		ServiceHealth serviceHealth = restTemplate.getForObject(System.getenv("PROJECT_GATEWAY_SERVICE_URI") + "/" + ServiceName.HEALTH_SERVICE.getServiceName() + "/" + serviceName,
				ServiceHealth.class);
		
		return serviceHealth;
	}
}
