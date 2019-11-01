package br.com.redhat.poc.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CacheHealthModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("cache-health")
	private List<String> cacheHealth;

	@JsonProperty("cluster-health")
	private String clusterHealth;
	
	@JsonProperty("cluster-name")
	private String clusterName;
	
	@JsonProperty("free-memory")
	private Long freeMemory;
	
	@JsonProperty("log-tail")
	private List<String> logTail;
	
	@JsonProperty("number-of-cpus")
	private Integer numberOfCpus;
	
	@JsonProperty("number-of-nodes")
	private Integer numberOfNodes;
	
	@JsonProperty("total-memory")
	private Long totalMemory;
	public List<String> getCacheHealth() {
		return cacheHealth;
	}
	public void setCacheHealth(List<String> cacheHealth) {
		this.cacheHealth = cacheHealth;
	}
	public String getClusterHealth() {
		return clusterHealth;
	}
	public void setClusterHealth(String clusterHealth) {
		this.clusterHealth = clusterHealth;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public Long getFreeMemory() {
		return freeMemory;
	}
	public void setFreeMemory(Long freeMemory) {
		this.freeMemory = freeMemory;
	}
	public List<String> getLogTail() {
		return logTail;
	}
	public void setLogTail(List<String> logTail) {
		this.logTail = logTail;
	}
	public Integer getNumberOfCpus() {
		return numberOfCpus;
	}
	public void setNumberOfCpus(Integer numberOfCpus) {
		this.numberOfCpus = numberOfCpus;
	}
	public Integer getNumberOfNodes() {
		return numberOfNodes;
	}
	public void setNumberOfNodes(Integer numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}
	public Long getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(Long totalMemory) {
		this.totalMemory = totalMemory;
	}	
}
