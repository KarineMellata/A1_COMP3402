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

public class ActivePage extends JFrame implements Runnable{
	
	public static void main(String[] args) {
		//SwingUtilities.invokeLater(new ActivePage(args[0]));
	}
	
	public ActivePage(String host, String username) {
		this.username = username;
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			service = (Service)registry.lookup("LoginService");
		} catch(Exception e) {
			System.err.println("Failed accessing RMI: "+e);
		}
	}
	JFrame frame;
	String username;
	private Service service;
	JLabel l1, l2, l3, l4;
	JButton btn1, btn2, btn3, btn4;

	
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

	@Override
	public void run() {
		frame = new JFrame("JPoker 24-Game");
		l1 = new JLabel(username);
		l2 = new JLabel("Number of wins: 10");
		l3 = new JLabel("Number of games: 20");
		l4 = new JLabel("Average time to win: 12.5s");
		l4 = new JLabel("Rank: #10");
		btn1 = new JButton("User Profile");
		btn2 = new JButton("Play Game");
		btn3 = new JButton("LeaderBoard");
		btn4 = new JButton("Logout");

		btn1.setBounds(20, 30, 120, 30);
		btn2.setBounds(140, 30, 120, 30);
		btn3.setBounds(260, 30, 120, 30);
		btn4.setBounds(380, 30, 120, 30);
		
		l1.setBounds(40, 70, 500, 30);
		l2.setBounds(20, 110, 200, 30);
		l3.setBounds(20, 150, 200, 30);
		l4.setBounds(20, 190, 200, 30);
		
		btn1.addActionListener(new ButtonClickListener());
		btn1.setActionCommand("User Profile");

		btn2.addActionListener(new ButtonClickListener());
		btn2.setActionCommand("Play Game");
		
		btn3.addActionListener(new ButtonClickListener());
		btn3.setActionCommand("LeaderBoard");
		
		btn4.addActionListener(new ButtonClickListener());
		btn4.setActionCommand("Logout");
		
		frame.add(l1);
		frame.add(l2);
		frame.add(l3);
		frame.add(l4);
		frame.add(btn1);
		frame.add(btn2);
		frame.add(btn3);
		frame.add(btn4);

		frame.setSize(650, 300);
		frame.setLayout(null);
		frame.setVisible(true);
		pack();
		
	}
	
	private class ButtonClickListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			String command = ae.getActionCommand();
			if(command.equals("Logout")) {
				frame.setVisible(false);
				LoggedOutPage lp = new LoggedOutPage();
				frame.dispose();
			}
		}
	}

}