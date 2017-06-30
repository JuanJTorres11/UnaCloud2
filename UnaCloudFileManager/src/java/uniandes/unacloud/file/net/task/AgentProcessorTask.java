package uniandes.unacloud.file.net.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;

import uniandes.unacloud.common.utils.UnaCloudConstants;

import uniandes.unacloud.file.FileManager;
import uniandes.unacloud.file.db.ServerVariableManager;
import uniandes.unacloud.file.db.entities.ServerVariableEntity;
import uniandes.unacloud.file.files.AgentFileManager;

/**
 * Class used to process task to update agent. It receives message from agent validate if agent requests agent files and call manager to copy files in socket stream
 * @author CesarF
 *
 */
public class AgentProcessorTask implements Runnable {
	
	private Socket socket;
	
	public AgentProcessorTask(Socket s) {
		this.socket = s;
	}

	@Override
	public void run() {
		try (Socket ss = socket; DataOutputStream out = new DataOutputStream(socket.getOutputStream()); DataInputStream is = new DataInputStream(socket.getInputStream());) {
			if (is.readInt() == UnaCloudConstants.REQUEST_AGENT_VERSION) {
				ServerVariableEntity variable = null;
				try (Connection con = FileManager.getInstance().getDBConnection();) {
					variable = ServerVariableManager.getVariable(con, UnaCloudConstants.AGENT_VERSION);
				} catch (Exception e) {
					e.printStackTrace();
				}
				out.writeUTF(variable.getValue());
				int respond = is.readInt();
				if (respond == UnaCloudConstants.GIVE_ME_FILES) {
					System.out.println(ss.getInetAddress().getHostName() + " request agent");
					AgentFileManager.copyAgentOnStream(out);
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
