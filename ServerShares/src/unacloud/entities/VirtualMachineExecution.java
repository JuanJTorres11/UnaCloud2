package unacloud.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import unacloud.enums.VirtualMachineExecutionStateEnum;

/**
 * Class to represent an entity from domain 
 * Represents Deployment
 * @author Cesar
 *
 */
public class VirtualMachineExecution {
	
	private Long id;
	private int cores;
	private int ram;
	private Date startTime;
	private Date stopTime;
	private PhysicalMachine node;
	private VirtualMachineExecutionStateEnum state;
	private String hostName;
	private List<NetInterface> interfaces;
	
	public VirtualMachineExecution(Long id, int cores, int ram, Date startTime,
			Date stopTime, PhysicalMachine node,
			VirtualMachineExecutionStateEnum state, String hostName) {
		super();
		this.id = id;
		this.cores = cores;
		this.ram = ram;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.node = node;
		this.state = state;
		this.hostName = hostName;
		interfaces = new ArrayList<NetInterface>();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public PhysicalMachine getNode() {
		return node;
	}

	public void setNode(PhysicalMachine node) {
		this.node = node;
	}

	public VirtualMachineExecutionStateEnum getState() {
		return state;
	}

	public void setState(VirtualMachineExecutionStateEnum state) {
		this.state = state;
	}

	public String getHostName() {
		return hostName;
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public List<NetInterface> getInterfaces() {
		return interfaces;
	}
	
	public void setInterfaces(List<NetInterface> interfaces) {
		this.interfaces = interfaces;
	}
	
	/**
	 * Return time execution in hours
	 * @return
	 */
	public Long getTimeInHours(){
		long millisTime=(stopTime.getTime()-startTime.getTime())/1000;
		return (millisTime/60/60)+1;
	}
}
