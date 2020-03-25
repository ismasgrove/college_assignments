package eg.edu.alexu.csd.oop.db;



import eg.edu.alexu.csd.oop.db.SQLParser;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultStyledDocument;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;

public class Terminal extends JFrame {

	private JPanel contentPane;
	private String operation;
	private Object[][] o;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Terminal frame = new Terminal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Terminal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 542, 339);
		contentPane.add(scrollPane);
		
		JEditorPane editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (editorPane.getText()!=null){
					operation=OperationFinder.getInstance().determine(editorPane.getText());
					if (operation.equalsIgnoreCase("create")|operation.equalsIgnoreCase("drop")) {
						try {
							System.out.println(ManagementSystem.getInstance().executeStructureQuery(editorPane.getText()));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (operation.equalsIgnoreCase("insert") | operation.equalsIgnoreCase("delete")) {
						try {
							System.out.println(ManagementSystem.getInstance().executeUpdateQuery(editorPane.getText()));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (operation.equalsIgnoreCase("select")) {
						try {
							o = ManagementSystem.getInstance().executeRetrievalQuery(editorPane.getText());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(o!=null){
							  for(int i=0;i<o.length;i++){
								  for(int j=0;j<o[i].length;j++){
								     System.out.print(o[i][j]);
								     if(o[i][j] instanceof Integer)System.out.print("\tthis is an int ");
								     else System.out.print("\tthis is a String ");
								  }
								  System.out.println();
							  }
							}
					}
					else System.out.println("ERROR: Invalid operation.");
				}
				else System.out.println("ERROR.");
			}
		});
		btnRun.setBounds(227, 374, 89, 23);
		contentPane.add(btnRun);
	}
}
