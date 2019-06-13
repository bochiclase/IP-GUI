import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.*;

public class MainWindowsIP extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel PanelInf = new JPanel(new GridLayout(1, 1));
	private JTextField entradaCMD = new JTextField();
	private JPanel PanelSup = new JPanel();
	private JTextArea salidaCMD = new JTextArea(30,35);
	private IP ip = new IP();
	
	public MainWindowsIP() {
		
		//Fuentes 
		Font fontsup = null;
		Font fontinf = null;
		/*
		InputStream a = getClass().getResourceAsStream("/Recursos/Orbitron-Black.ttf");
		try {
			fontsup = Font.createFont(Font.TRUETYPE_FONT, a).deriveFont(20f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InputStream b = getClass().getResourceAsStream("/Recursos/SpecialElite-Regular.ttf");
		try {
			fontinf = Font.createFont(Font.TRUETYPE_FONT, b).deriveFont(20f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		*/

		
		//VENTANA
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLayout(new BorderLayout());
	setTitle("Adrian - IP GUI");
	
		
	
		//SCROLL SUPERIOR
	salidaCMD.setEditable(false);
	JScrollPane scrollPane = new JScrollPane(salidaCMD);
	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
	PanelSup.add(scrollPane);
	
	
		//PANEL INFERIOR
	entradaCMD.addKeyListener(this);
	PanelInf.add(entradaCMD);
	
		//AGREGACIONES
	add(PanelSup, BorderLayout.NORTH);
	add(PanelInf, BorderLayout.SOUTH);
	pack();
	setLocationRelativeTo(null); // Centrar ventana
	

	
}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		
			String mensaje = ip.ejecutar(entradaCMD.getText());
			entradaCMD.setText("");
			salidaCMD.append(mensaje + System.lineSeparator());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void mostrarAyuda() {
		salidaCMD.append("Escribe una IP, un mensaje y un nombre de usuario" +  System.lineSeparator());
		salidaCMD.append("Ejemplo de sintasis" +  System.lineSeparator());
		salidaCMD.append("IP=(192.168.8.8) mensaje=(hola mundo) usuario=(bocheti)" + System.lineSeparator());
	}
}
