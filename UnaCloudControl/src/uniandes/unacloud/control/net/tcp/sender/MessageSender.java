package uniandes.unacloud.control.net.tcp.sender;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import uniandes.unacloud.common.net.messages.UnaCloudAbstractMessage;
import uniandes.unacloud.common.net.messages.UnaCloudAbstractResponse;
import uniandes.unacloud.control.ControlManager;
import uniandes.unacloud.control.net.udp.processor.AbstractResponseProcessor;
import uniandes.unacloud.share.db.entities.PhysicalMachineEntity;

/**
 * Class to send message to list of machines. It class extends from thread to manage parallel connection to agents
 * @author CesarF
 *
 */
public class MessageSender extends Thread {
	
	private List<PhysicalMachineEntity> machines;
	
	private UnaCloudAbstractMessage message;
	
	private AbstractResponseProcessor processor;
	
	
	public MessageSender(List<PhysicalMachineEntity> machines,UnaCloudAbstractMessage message, AbstractResponseProcessor processor) {
		this.machines = machines;
		this.message = message;
		this.processor = processor;
	}
	
	@Override
	public void run() {
		System.out.println("Thread for "+machines.size());
		for (PhysicalMachineEntity pm : machines) {
			try (Socket s =  new Socket(pm.getIp(), ControlManager.getInstance().getPort());) {
				System.out.println("Sending message to " + pm.getIp() + ":" + ControlManager.getInstance().getPort());
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				oos.writeObject(message);
				oos.flush();
				try {
					processor.attendResponse((UnaCloudAbstractResponse) ois.readObject(),pm.getId());
				} catch (Exception e) {
					System.out.println("Error in machine response; " + pm.getIp());	
					e.printStackTrace();
					processor.attendError(e.getMessage(), pm.getId());
				}				
				s.close();
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("Error connectiong to " + pm.getIp());		
				processor.attendError(e.getMessage(), pm.getId());				
			}
			try {
				Thread.sleep(500);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}