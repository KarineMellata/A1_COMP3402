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

public class ErrorPage extends JFrame {
	
//	public static void main(String[] args) {
//		//SwingUtilities.invokeLater(new ActivePage(args[0]));
//	}
	public enum Errors {
		  NEED_REG,
		  PASSWORD_NOT_MATCH,
		  ALREADY_USED_USERNAME,
		  ALREADY_LOGGED_IN
		}
	
	public ErrorPage(Errors Error) {
		JFrame frame = new JFrame("Error");
		JLabel l1 = new JLabel("Error");
		if(Error == Errors.NEED_REG) {
			l1 = new JLabel("You need to register first.");
		} else if(Error == Errors.ALREADY_USED_USERNAME) {
			l1 = new JLabel("This username is already in use.");
		} else if(Error == Errors.PASSWORD_NOT_MATCH) {
			l1 = new JLabel("The passwords need to match.");
		} else if(Error == Errors.ALREADY_LOGGED_IN) {
			l1 = new JLabel("You cannot log in twice.");
		}
		
	
		l1.setBounds(40, 30, 400, 30);
		
		
		frame.add(l1);
		
	
		frame.setSize(400, 300);
		frame.setLayout(null);
		frame.setVisible(true);
	}
}