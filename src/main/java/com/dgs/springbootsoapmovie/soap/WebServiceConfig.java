package com.dgs.springbootsoapmovie.soap;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

// @EnableWs is used with @Configuration to have Spring web services defined in WsConfigurerAdapter

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	// MessageDispatcherServlet is more a servlet which handles all the requests, all the SOAP requests and be able to identify
	// the endpoints and things like that. 
		//ApplicationContext
	// url -> /ws/*
	
	// ServletRegistrationBean configures application context, URL mappings etc
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		
		// Map the MessageDispatcherServlet to /ws/* url
		
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context); 
		messageDispatcherServlet.setTransformWsdlLocations(true); 
		
		return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
	}
	
	// Spring Web Services actually creates the WSDL for us. We will not create WSDL by hand. 
	// We would want to call this WSDL as course.wsdl and you would want to expose this as the url /ws/courses.wsdl and this
	// web service definition is based on the schema course-details.xsd. 
	
	// So what we'll configure web services to do is use the schema and generate these WSDL for us. And when we configure WSDL, 
	// we will need to configure a couple of things: PortType, Namespace
	
	//  DefaultWsdl11Definition is configuring WSDL definitions such as port type name, location URI, target namespace, schema etc. 
	
	// XsdSchema represents an abstraction for XSD schemas
	
	@Bean(name="movies")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
		
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		
		definition.setPortTypeName("MoviePort");
		definition.setTargetNamespace("http://usdgadget.com/movies"); 
		definition.setLocationUri("/ws"); 
		definition.setSchema(coursesSchema);
		
		return definition; 
	}
	
	// XsdSchema represents an abstraction for XSD schemas
	
	@Bean
	public XsdSchema coursesSchema() {
		
		return new SimpleXsdSchema(new ClassPathResource("movie-details.xsd"));
	}
	
	// Security configuration
	
	// XwsSecurityInterceptor

	// Interceptors.add -> XwsSecurityInterceptor
	
	@Bean
	public XwsSecurityInterceptor securityInterceptor() {
		
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		
		// We have a securityInterceptor but we would need to add it to be processed by the list of Spring Web Services interceptors
		// How do we do that? We need to extend a specific class WsConfigurerAdapter
		
		// Callback Handler -> SimplePasswordValidationCallbackHandler
		// Security Policy -> securityPolicy.xml
		
		securityInterceptor.setCallbackHandler(callbackHandler()); 
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		
		return securityInterceptor;
	}
	
	public SimplePasswordValidationCallbackHandler callbackHandler() {

		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		
		handler.setUsersMap(Collections.singletonMap("user", "password")); 
		
		return handler; 
	}
	
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(securityInterceptor());
	}
}
