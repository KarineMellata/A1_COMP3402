package pokerapp;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Service extends Remote {
	boolean putUserInfo(String name, String password) throws RemoteException, IOException;
	public boolean parseInfo(String name, String password) throws IOException;
	public boolean putOnlineUser(String name) throws RemoteException, IOException;
	public boolean isOnline(String name) throws RemoteException, IOException;
	public boolean usernameIsAlreadyUsed(String name) throws RemoteException, IOException;
	public void logOut(String name) throws IOException;
}
