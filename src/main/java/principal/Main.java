package principal;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.TooManyListenersException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import img_processing.Text_Extractor;
import utils.DragAndDrop;
import utils.Metodos;

/**
 *
 * @author h.omar
 */

public class Main extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	JRadioButton multiple;

	JRadioButton single;

	public static String os = System.getProperty("os.name");

	static String separador = Metodos.saberSeparador(os);

	private JScrollPane jScrollPane1;

	private JButton start;

	private JTextArea view;

	private JLabel label4;

	public static JTextField ruta;

	public static JTextField getRuta() {
		return ruta;
	}

	public Main() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/imagenes/convert.png")));
		setTitle("Img To TXT");
		initComponents();
	}

	public static String getOs() {
		return os;
	}

	private void initComponents() {

		start = new javax.swing.JButton();

		start.setIcon(new ImageIcon(Main.class.getResource("/imagenes/convert.png")));

		start.setFont(new Font("Dialog", Font.PLAIN, 16));

		jScrollPane1 = new javax.swing.JScrollPane();

		view = new javax.swing.JTextArea();
		view.setWrapStyleWord(true);
		view.setLineWrap(true);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		start.setText("Convert");

		start.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseClicked(java.awt.event.MouseEvent evt) {

				try {

					String imgName = ruta.getText();

					int modo = 1;

					if (!imgName.isEmpty()) {

						if (multiple.isSelected()) {

							modo = 2;

							Metodos.convertir(imgName);

						}

						String extracted_text = Text_Extractor.get_Text(imgName, modo);

						if (modo == 1) {

							view.setText(extracted_text);
						}

						else {
							view.setText("CONVERSIÓN TERMINADA");
						}

					}

					else {
						Metodos.mensaje("Por favor, selecciona una imagen para convertir a texto", 3);
					}

				}

				catch (IOException e) {
					//
				}

			}

		});

		view.setColumns(20);

		view.setRows(35);

		jScrollPane1.setViewportView(view);

		label4 = new JLabel("");

		label4.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				int filtro = 1;

				String mensaje = "Elije una imagen";

				if (multiple.isSelected()) {

					filtro = 2;

					mensaje = "Elije una carpeta";

				}

				File[] files = Metodos.seleccionar(filtro, "Imagen", mensaje);

				if (files != null && files.length > 0) {

					ruta.setText(files[0].toString());

				}

			}

		});

		label4.setIcon(new ImageIcon(Main.class.getResource("/imagenes/import.png")));
		label4.setHorizontalAlignment(SwingConstants.LEFT);
		label4.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JTextArea imagenes = new JTextArea();
		imagenes.setText("    Arrastra los archivos aqui");
		imagenes.setForeground(SystemColor.desktop);
		imagenes.setFont(new Font("Tahoma", Font.PLAIN, 26));
		imagenes.setEditable(false);

		imagenes.setBackground(SystemColor.windowBorder);

		JButton btnNewButton = new JButton("Copiar");

		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Metodos.copy(view.getText());
			}

		});

		btnNewButton.setFont(new Font("Dialog", Font.PLAIN, 16));

		btnNewButton.setIcon(new ImageIcon(Main.class.getResource("/imagenes/copy.png")));

		JButton btnNewButton_1 = new JButton("");

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				view.setText("");
			}

		});

		btnNewButton_1.setIcon(new ImageIcon(Main.class.getResource("/imagenes/clean.png")));

		JButton btnNewButton_2 = new JButton("");

		btnNewButton_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String carpeta = ruta.getText();

				try {

					if (single.isSelected()) {
						carpeta = carpeta.substring(0, carpeta.lastIndexOf(separador));
					}

					carpeta += Main.getSeparador() + "output";

					Metodos.abrirCarpeta(carpeta);

				}

				catch (Exception e) {
					//
				}

			}

		});

		btnNewButton_2.setIcon(new ImageIcon(Main.class.getResource("/imagenes/folder.png")));

		single = new JRadioButton("Single");
		single.setFont(new Font("Dialog", Font.PLAIN, 16));
		single.setSelected(true);

		single.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				multiple.setSelected(false);

				if (!Metodos.saberExtension(ruta.getText()).contains(".")) {
					ruta.setText("");
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				single.setSelected(true);
				multiple.setSelected(false);
			}

		});

		multiple = new JRadioButton("Multiple");
		multiple.setFont(new Font("Dialog", Font.PLAIN, 16));
		multiple.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				single.setSelected(false);

				try {

					if (Metodos.saberExtension(ruta.getText()).contains(".")) {
						ruta.setText(ruta.getText().substring(0, ruta.getText().lastIndexOf(Main.getSeparador())));
					}

				}

				catch (Exception e1) {
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				single.setSelected(false);
				multiple.setSelected(true);
			}
		});

		ruta = new JTextField();
		ruta.setEditable(false);
		ruta.setColumns(10);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGap(23)
						.addGroup(layout.createParallelGroup(Alignment.LEADING, false).addGroup(layout
								.createSequentialGroup()
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 529, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(layout.createSequentialGroup()
												.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 77,
														GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 54,
														GroupLayout.PREFERRED_SIZE)
												.addGap(9))
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)))
								.addGroup(
										layout.createSequentialGroup().addGap(6)
												.addComponent(label4, GroupLayout.PREFERRED_SIZE, 76,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(imagenes, GroupLayout.PREFERRED_SIZE, 403,
														GroupLayout.PREFERRED_SIZE)
												.addGap(25).addComponent(start, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addGroup(layout.createSequentialGroup().addGap(42).addComponent(single).addGap(31)
								.addComponent(multiple).addGap(18)
								.addComponent(ruta, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)))
				.addGap(472)));
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(18)
								.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(single)
										.addComponent(multiple).addComponent(ruta, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(start)
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
												.addComponent(label4, GroupLayout.PREFERRED_SIZE, 64,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(imagenes, GroupLayout.PREFERRED_SIZE, 60,
														GroupLayout.PREFERRED_SIZE)))
								.addGap(31)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addComponent(btnNewButton).addGap(27)
												.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
														.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 210,
												GroupLayout.PREFERRED_SIZE))
								.addGap(51)));
		getContentPane().setLayout(layout);

		setSize(new Dimension(750, 450));

		setLocationRelativeTo(null);
		javax.swing.border.TitledBorder dragBorder = new javax.swing.border.TitledBorder("Drop 'em");

		try {

			new DragAndDrop(imagenes, dragBorder, rootPaneCheckingEnabled, new DragAndDrop.Listener() {

				public void filesDropped(java.io.File[] files) {

					try {

						String seleccion = files[0].getCanonicalPath();

						ruta.setText(seleccion);

						if (seleccion.substring(seleccion.length() - 4, seleccion.length()).contains(".")) {
							multiple.setSelected(false);
							single.setSelected(true);
						}

						else {

							single.setSelected(false);
							multiple.setSelected(true);
						}

					} catch (Exception e) {
						Metodos.mensaje("Error", 1);
					}

				}

			});

		}

		catch (TooManyListenersException e1) {
			Metodos.mensaje("Error al mover los archivos", 1);
		}

	}

	public static String getSeparador() {
		return separador;
	}

	public static void main(String args[]) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new Main().setVisible(true);
			}

		});
	}

}
