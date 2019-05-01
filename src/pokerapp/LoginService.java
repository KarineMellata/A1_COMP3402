package pokerapp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;

public class LoginService extends UnicastRemoteObject implements Service  {

	public static void main(String[] args) {
		try {
			LoginService app = new LoginService();
			System.setSecurityManager(new SecurityManager());
			Naming.rebind("LoginService", app);
			System.out.println("Service registered");
			//Small hack to clear the file
			FileOutputStream outputStream = new FileOutputStream("/Users/karinemellata/Documents/eclipe-wspace2/A1/OnlineUser", false);
			outputStream.write("".getBytes());
			// int count = app.count("The quick brown fox jumps over a lazy dog");
			// System.out.println("There are "+count+" words");
		} catch(Exception e) {
			System.err.println("Exception thrown: "+e);
		}
	}
	
	public LoginService() throws RemoteException {}
	public boolean putUserInfo(String name, String password) throws RemoteException, IOException {
		String info = name + "," + password;
		FileOutputStream outputStream = new FileOutputStream("/Users/karinemellata/Documents/eclipe-wspace2/A1/UserInfo", true);
	    byte[] strToBytes = info.getBytes();
	    byte[] newLine = "\n".getBytes();
	    outputStream.write(strToBytes);
	    outputStream.write(newLine);
	    outputStream.close();
		return true;
	}
	
	public boolean putOnlineUser(String name) throws RemoteException, IOException {
		String info = name;
		FileOutputStream outputStream = new FileOutputStream("/Users/karinemellata/Documents/eclipe-wspace2/A1/OnlineUser", true);
	    byte[] strToBytes = info.getBytes();
	    byte[] newLine = "\n".getBytes();
	    outputStream.write(strToBytes);
	    outputStream.write(newLine);
	    outputStream.close();
		return true;
	}
	
	public boolean isOnline(String name) throws RemoteException, IOException {
		File file = new File("/Users/karinemellata/Documents/eclipe-wspace2/A1/OnlineUser");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
		    if(line.equals(name)) {
		    	return true;
		    }
		}
		return false;
	}
	
	public boolean usernameIsAlreadyUsed(String name) throws RemoteException, IOException {
		File file = new File("/Users/karinemellata/Documents/eclipe-wspace2/A1/UserInfo");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
			String[] info = line.split(",");
		    if(info[0].equals(name)) {
		    	return true;
		    }
		}
		return false;
	}
	
	
	
	public void logOut(String name) throws IOException {
		FileOutputStream outputStream = new FileOutputStream("/Users/karinemellata/Documents/eclipe-wspace2/A1/OnlineUser", true);
		
		File file = new File("/Users/karinemellata/Documents/eclipe-wspace2/A1/OnlineUser");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
		    if(line.equals(name)) {
		    }
		}
	}
	
	@SuppressWarnings("resource")
	public boolean parseInfo(String name, String password) throws IOException{
		File file = new File("/Users/karinemellata/Documents/eclipe-wspace2/A1/UserInfo");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
		    if(line.equals(name + "," + password)) {
		    	return true;
		    }
		}
		return false;
	}

}
