package co.edu.uniandes.communication.send.report;

import com.losandes.enums.VirtualMachineExecutionStateEnum;
import com.losandes.utils.OperatingSystem;

import communication.UDPMessageEnum;
import co.edu.uniandes.communication.send.UDPCommunicator;


/**
 * Class used to send  messages to UnaCloud server
 * @author Clouder
 */
public class ServerMessageSender {
	
	//-----------------------------------------------------------------
	// Methods
	//-----------------------------------------------------------------

    /**
     * Sends to UnaCloud control server a message reporting the state of a virtual machine
     * @param virtualMachineCode The string that contains the id of the virtual machine to be reported
     * @param state The state of the reported virtual machine
     * @param message The state message of the reported virtual machine
     * @return If the message could be sent or not
     * @throws Exception 
     */
    public static boolean reportVirtualMachineState(long virtualMachineCode,VirtualMachineExecutionStateEnum state,String message) throws Exception{    	
    	return UDPCommunicator.getInstance().pushInfoVM(UDPMessageEnum.STATE_VM, "hostname",OperatingSystem.getHostname(),"executionId",virtualMachineCode,"state",state.toString(),"message",message);
    }
    
    /**
     * Send message reporting the state of a physical machine
     * @param executions
     * @throws Exception
     */
	public static void reportPhyisicalMachine(String executions) throws Exception{
		UDPCommunicator.getInstance().pushInfoPM(UDPMessageEnum.STATE_PM, "hostname",OperatingSystem.getHostname(),"hostuser",OperatingSystem.getUserName(),"executions",executions);
	}
	
	/**
	 * Send message to be save in log database
	 * @param component where event happened
	 * @param message describes error
	 * @throws Exception
	 */
	public static void reportMachineLogEvent(String component, String message) throws Exception{
		UDPCommunicator.getInstance().pushInfoPM(UDPMessageEnum.LOG_PM, "hostname",OperatingSystem.getHostname(),"component",component, "message",message);
	}
}