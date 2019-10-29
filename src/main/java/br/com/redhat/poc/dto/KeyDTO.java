package br.com.redhat.poc.dto;

import java.io.Serializable;

public class KeyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1050620516477850574L;
	private String key;
	private String value;
	
	
	
	public KeyDTO() {
		super();
		
	}


	public String getKey() {
		return key;
	}



	public void setKey(String key) {
		this.key = key;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
