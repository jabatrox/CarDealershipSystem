package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Agent.java
 */
public class Agent {
	
	private int agentID;
	private String firstName;
	private String lastName;

	/**
	 * @param agentID
	 * @param firstName
	 * @param lastName
	 */
	public Agent(int agentID, String firstName, String lastName) {
		this.agentID = agentID;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	/**
	 * @return the agentID
	 */
	public int getAgentID() {
		return agentID;
	}
	/**
	 * @param agentID the agentID to set
	 */
	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}