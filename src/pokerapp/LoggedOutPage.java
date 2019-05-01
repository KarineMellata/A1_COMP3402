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

public class LoggedOutPage extends JFrame {
	
//	public static void main(String[] args) {
//		//SwingUtilities.invokeLater(new ActivePage(args[0]));
//	}
	public LoggedOutPage() {
		JFrame frame = new JFrame("Logged out");
		JLabel l1 = new JLabel("Logged out");
		
	
		l1.setBounds(40, 30, 400, 30);
		
		
		frame.add(l1);
		
	
		frame.setSize(400, 300);
		frame.setLayout(null);
		frame.setVisible(true);
	}
}