package br.com.redhat.poc.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ResponseExceptionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 169564755614172572L;

	@JsonProperty("timestamp")
	private LocalDateTime timestamp;

	@JsonProperty("status")
	private int httpStatus;

	@JsonProperty("error")
	private String errorDescription;

	@JsonProperty("message")
	private String message;

	@JsonProperty("message-details")
	private List<String> messageDetails;

	@JsonProperty("path")
	private String path;

	public ResponseExceptionDTO() {
		// vazio
	}

	public ResponseExceptionDTO(LocalDateTime timestamp, HttpStatus status, String message, List<String> messageDetails, String path) {
		this.timestamp = timestamp;
		this.httpStatus = status.value();
		this.errorDescription = status.getReasonPhrase();
		this.message = message;
		this.messageDetails = messageDetails;
		this.path = path;
	}

	public ResponseExceptionDTO(HttpStatus status, String path) {
		this(LocalDateTime.now(), status, status.getReasonPhrase(), Collections.emptyList(), path);
	}
	
	public ResponseExceptionDTO(HttpStatus status, String message, String path) {
		this(LocalDateTime.now(), status, message, Collections.emptyList(), path);
	}

	public ResponseExceptionDTO(HttpStatus status, String message, List<String> messageDetails, String path) {
		this(LocalDateTime.now(), status, message, messageDetails, path);
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

}
