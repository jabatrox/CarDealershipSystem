package classes;

/**
 * --------------------------------------------------
 * @author Javier Soler, Borja González
 * Date: 11/09/2018
 * Lab: Final Project
 * File name: Agent.java
 */
public class Agent {
	
	static int agentID;
	static String firstName;
	static String lastName;

	/**
	 * @param agentID
	 * @param firstName
	 * @param lastName
	 */
	public Agent(int agentID, String firstName, String lastName) {
		Agent.agentID = agentID;
		Agent.firstName = firstName;
		Agent.lastName = lastName;
	}
	/**
	 * @return the agentID
	 */
	public static int getAgentID() {
		return agentID;
	}
	/**
	 * @param agentID the agentID to set
	 */
	public static void setAgentID(int agentID) {
		Agent.agentID = agentID;
	}
	/**
	 * @return the firstName
	 */
	public static String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public static void setFirstName(String firstName) {
		Agent.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public static String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public static void setLastName(String lastName) {
		Agent.lastName = lastName;
	}

}
