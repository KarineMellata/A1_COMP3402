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

import pokerapp.ErrorPage.Errors;

public class Register extends JFrame implements Runnable{
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Register(args[0]));
	}
	
	public Register(String host) {
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			service = (Service)registry.lookup("LoginService");
		} catch(Exception e) {
			System.err.println("Failed accessing RMI: "+e);
		}
	}
	
	private Service service;
	JFrame frame;
	JLabel l1, l2, l3, l4;
	JTextField tf1;
	JButton btn1, btn2;
	JPasswordField p1, p2;
	
	public void updateInfo(String loginName, String loginPW) {
	    if(service != null) {
	        try {
	            service.putUserInfo(loginName, loginPW);
	        } catch (RemoteException e) {
	            System.err.println("Failed invoking RMI: ");
	        }
	        catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	public boolean usernameExists(String loginName) {
	    if(service != null) {
	        try {
	            return service.usernameIsAlreadyUsed(loginName);
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

	@Override
	public void run() {
		frame = new JFrame("Register");
		l1 = new JLabel("Register");
		l2 = new JLabel("Login Name");
		l3 = new JLabel("Password");
		l4 = new JLabel("Confirm Password");
		tf1 = new JTextField();
		p1 = new JPasswordField();
		p2 = new JPasswordField();
		btn1 = new JButton("Register");
		btn2 = new JButton("Cancel");

		l1.setBounds(40, 30, 400, 30);
		l2.setBounds(20, 70, 200, 30);
		l3.setBounds(20, 110, 200, 30);
		l4.setBounds(20, 150, 200, 30);
		tf1.setBounds(140, 70, 200, 30);
		p1.setBounds(140, 110, 200, 30);
		p2.setBounds(140, 150, 200, 30);
		btn1.setBounds(20, 190, 100, 30);
		btn2.setBounds(240, 190, 100, 30);
		
		btn1.addActionListener(new ButtonClickListener());
		btn1.setActionCommand("Register");

		btn2.addActionListener(new ButtonClickListener());
		btn2.setActionCommand("Cancel");
		
		frame.add(l1);
		frame.add(l2);
		frame.add(tf1);
		frame.add(l3);
		frame.add(l4);
		frame.add(p2);
		frame.add(p1);
		frame.add(btn1);
		frame.add(btn2);

		frame.setSize(400, 300);
		frame.setLayout(null);
		frame.setVisible(true);
		pack();
		
	}
	
	private class ButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			String command = ae.getActionCommand();
			if(command.equals("Register")) {
				String uname = tf1.getText();
				if(usernameExists(uname)) {
					ErrorPage ep = new ErrorPage(Errors.ALREADY_USED_USERNAME);
					ep.setVisible(true);
				}
				else if(! p1.getText().equals(p2.getText())){
					ErrorPage ep = new ErrorPage(Errors.PASSWORD_NOT_MATCH);
					frame.setVisible(false);
					ep.setVisible(true);
					frame.dispose();
				} else {
					String pass = p1.getText();
					updateInfo(uname, pass);
					frame.setVisible(false);
					SuccessPage s = new SuccessPage();
					s.setVisible(true);
					frame.dispose();
				}
			}
			else if(command.equals("Cancel")) {
				frame.dispose();
			}
		}
	}

}