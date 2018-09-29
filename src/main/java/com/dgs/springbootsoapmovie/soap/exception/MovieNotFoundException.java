package com.dgs.springbootsoapmovie.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode=FaultCode.CLIENT)
public class MovieNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7174204617119075232L;

	public MovieNotFoundException(String message) {
		super(message);
	}
}
