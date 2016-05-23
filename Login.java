package main;


import java.awt.event.*;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;


public class Login extends JFrame implements ActionListener {
	JButton host;
 	JPanel j1;
 	JButton join;
 
 public Login() {
	 Container contentPane = this.getContentPane();
  	j1 = new JPanel();
  	host = new JButton("Host");
  	host.addActionListener(this);
  
  	join = new JButton("Join");
  	join.addActionListener(this);
  
  	j1.add(host);
  	j1.add(join);
  	contentPane.add(j1, BorderLayout.NORTH);
 	}

 public static void main(String[] args) {
	 Login screen = new Login();
	 screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 screen.setSize(400, 400);
//	 screen.setLocationRelativeTo(null);
	 screen.setVisible(true);
 	}
 
 public void actionPerformed(ActionEvent ae) {

	 if(ae.getActionCommand().equals("Host")){
		 /**Put the other action here*/
		 JOptionPane.showMessageDialog(null, "Host a game!");
	 }
	 else if(ae.getActionCommand().equals("Join"))
		/**Put the other action here*/
		 JOptionPane.showMessageDialog(null, "Join a game!");
 	}	
}