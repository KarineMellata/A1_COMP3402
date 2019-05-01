package pokerapp;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pokerapp.ErrorPage.Errors;

public class Login extends JFrame implements Runnable {
	JFrame frame;
	static String host = "";
	JLabel l1, l2, l3;
	JTextField tf1, p1;
	JButton btn1, btn2;
	private int info;
	private Service service;
	
	public Login(String host) {
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			service = (Service)registry.lookup("LoginService");
		} catch(Exception e) {
			System.err.println("Failed accessing RMI: "+e);
		}
	}
	
	public boolean checkInfo(String loginName, String loginPW) {
		if(service != null) {
			try {
				if(service.parseInfo(loginName, loginPW)) return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void addOnlineUser(String loginName) {
	    if(service != null) {
	        try {
	            service.putOnlineUser(loginName);
	        } catch (RemoteException e) {
	            System.err.println("Failed invoking RMI: ");
	        }
	        catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	public boolean isOnline(String loginName) {
	    if(service != null) {
	        try {
	            return service.isOnline(loginName);
	        } catch (RemoteException e) {
	            System.err.println("Failed invoking RMI: ");
	        }
	        catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    return false;
	}
	
	public void removeOnlineUser(String loginName) {
	    if(service != null) {
	        try {
	            service.removeOnlineUser(loginName);
	        } catch (RemoteException e) {
	            System.err.println("Failed invoking RMI: ");
	        }
	        catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	public void run() {
		frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l1 = new JLabel("Login");
		l2 = new JLabel("Login Name");
		l3 = new JLabel("Password");
		tf1 = new JTextField();
		p1 = new JTextField();
		btn1 = new JButton("Login");
		btn2 = new JButton("Register");

		l1.setBounds(40, 30, 400, 30);
		l2.setBounds(20, 70, 200, 30);
		l3.setBounds(20, 110, 200, 30);
		tf1.setBounds(140, 70, 200, 30);
		p1.setBounds(140, 110, 200, 30);
		btn1.setBounds(20, 160, 100, 30);
		btn2.setBounds(240, 160, 100, 30);

		btn1.addActionListener(new ButtonClickListener());
		btn1.setActionCommand("Login");

		btn2.addActionListener(new ButtonClickListener());
		btn2.setActionCommand("Register");

		frame.add(l1);
		frame.add(l2);
		frame.add(tf1);
		frame.add(l3);
		frame.add(p1);
		frame.add(btn1);
		frame.add(btn2);

		frame.setSize(400, 250);
		frame.setLayout(null);
		frame.setVisible(true);
		pack();
	}
	
	public static void main(String[] args) {
		host = args[0];
		SwingUtilities.invokeLater(new Login(host));
		
	}

	private class ButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			String command = ae.getActionCommand();
			if(command.equals("Login")) {
				String uname = tf1.getText();
				String pass = p1.getText();
				boolean isUser = checkInfo(uname, pass);
				// TODO : Move isOnline from service
				if(isOnline(uname)) {
					ErrorPage ep = new ErrorPage(Errors.ALREADY_LOGGED_IN);
					ep.setVisible(true);
				}
				else if(!isUser) {
					ErrorPage ep = new ErrorPage(Errors.NEED_REG);
					frame.setVisible(false);
					ep.setVisible(true);
					frame.dispose();
					
				} else {  // success
					ActivePage ap = new ActivePage(host, uname);
					ap.run();
					addOnlineUser(uname);
					frame.setVisible(false);
					ap.setVisible(true);
					frame.dispose();
				}
			}
			else if(command.equals("Register")) {
				Register reg = new Register(host);
				reg.run();
				reg.setVisible(true);
			}
		}
	}

}