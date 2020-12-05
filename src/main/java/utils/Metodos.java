package utils;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import principal.Main;

public abstract class Metodos {

	private static Clipboard getSystemClipboard() {

		Toolkit defaultToolkit = Toolkit.getDefaultToolkit();

		Clipboard systemClipboard = defaultToolkit.getSystemClipboard();

		return systemClipboard;
	}

	public static void copy(String text) {

		Clipboard clipboard = getSystemClipboard();

		clipboard.setContents(new StringSelection(text), null);
	}

	public static void abrirCarpeta(String ruta) throws IOException {

		if (ruta != null && !ruta.equals("") && !ruta.isEmpty()) {

			try {

				if (Main.getOs().contentEquals("Linux")) {
					Runtime.getRuntime().exec("xdg-open " + ruta);
				}

				else {
					Runtime.getRuntime().exec("cmd /c explorer " + "\"" + ruta + "\"");
				}

			}

			catch (IOException e) {
				mensaje("Ruta inválida", 1);
			}
		}

	}

	public static void mensaje(String mensaje, int titulo) {

		String tituloSuperior = "";

		int tipo = 0;

		switch (titulo) {

		case 1:
			tipo = JOptionPane.ERROR_MESSAGE;

			tituloSuperior = "Error";

			break;

		case 2:
			tipo = JOptionPane.INFORMATION_MESSAGE;

			tituloSuperior = "Informacion";

			break;

		case 3:
			tipo = JOptionPane.WARNING_MESSAGE;

			tituloSuperior = "Advertencia";

			break;

		default:
			break;

		}

		JLabel alerta = new JLabel(mensaje);

		alerta.setFont(new Font("Arial", Font.BOLD, 18));

		JOptionPane.showMessageDialog(null, alerta, tituloSuperior, tipo);

	}

	public static String saberSeparador(String os) {
		if (os.equals("Linux")) {
			return "/";
		} else {
			return "\\";
		}
	}

	public static java.io.File[] seleccionar(int tipo, String rotulo, String mensaje) {

		JFileChooser chooser = new JFileChooser();

		FileNameExtensionFilter filter = null;

		filter = new FileNameExtensionFilter(rotulo, "jpg", "gif", "jpeg", "png", "JPG", "PNG");

		chooser.setFileFilter(filter);

		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		switch (tipo) {

		case 1:

			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			break;

		case 2:

			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			break;

		default:
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			break;

		}

		if (!chooser.isMultiSelectionEnabled()) {
			chooser.setMultiSelectionEnabled(true);
		}

		chooser.showOpenDialog(chooser);

		File[] files = chooser.getSelectedFiles();

		if (files.length == 0) {
			mensaje(mensaje, 3);
		}

		return files;
	}

	public static void crearFichero(String ruta, String texto, boolean carpeta, boolean filtro) throws IOException {

		try {

			File archivo;

			if (filtro) {

				crearCarpeta(ruta.substring(0, ruta.lastIndexOf(Main.getSeparador())));

				archivo = new File(ruta + ".txt");
			}

			else {
				archivo = new File(ruta);
			}

			if (carpeta) {

				if (!archivo.exists()) {
					archivo.mkdir();
				}

			}

			else {

				BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

				try {
					bw.write(texto);
				}

				finally {
					bw.close();
				}
			}
		} catch (Exception e) {

		}
	}

	public static void crearCarpeta(String ruta) {

		File dir = new File(ruta);

		if (!dir.exists()) {
			dir.mkdir();
		}

	}

	public static void renombrar(String ruta1, String ruta2) {

		File f1 = new File(ruta1);

		File f2 = new File(ruta2);

		f1.renameTo(f2);

	}

	public static void conversion(String extension, String salida, String carpeta) {

		LinkedList<String> listaImagenes = directorio(carpeta, extension, true, false);

		int resto = 3;

		if (extension.length() == 4) {
			resto = 5;
		}

		for (int i = 0; i < listaImagenes.size(); i++) {

			File f1 = new File(carpeta + Main.getSeparador() + listaImagenes.get(i));

			File f2 = new File(carpeta + Main.getSeparador()
					+ listaImagenes.get(i).substring(0, listaImagenes.get(i).length() - resto) + "." + salida);

			f1.renameTo(f2);

		}

		listaImagenes.clear();
	}

	public static void convertir(String carpeta) {

		conversion("jpeg", "jpg", carpeta);

		conversion("JPEG", "jpg", carpeta);

		conversion("JPG", "jpg", carpeta);

		conversion("PNG", "png", carpeta);

		conversion("webp", "png", carpeta);

		conversion("GIF", "gif", carpeta);

		conversion("GIF", "gif", carpeta);

	}

	public static LinkedList<String> directorio(String ruta, String extension, boolean filtro, boolean carpeta) {

		LinkedList<String> lista = new LinkedList<String>();

		try {

			File f = new File(ruta);

			if (f.exists()) {

				File[] ficheros = f.listFiles();

				String fichero = "";

				String extensionArchivo;

				File folder;

				for (int x = 0; x < ficheros.length; x++) {

					fichero = ficheros[x].getName();

					folder = new File(ruta + fichero);

					extensionArchivo = extraerExtension(fichero);

					if (filtro) {

						if (folder.isFile()) {

							if (fichero.length() > 5 && fichero.substring(0, fichero.length() - 5).contains(".")) {

								renombrar(ruta + fichero, ruta + eliminarPuntos(fichero));

							}

							if (extension.equals("webp") && extensionArchivo.equals("webp")
									|| extension.equals("jpeg") && extensionArchivo.equals("jpeg")
									|| (extension.equals(".") && (extensionArchivo.equals("jpg")

											|| extensionArchivo.equals("png")))) {

								if (carpeta) {
									lista.add(ruta + fichero);
								}

								else {
									lista.add(fichero);
								}

							}

						}

					}

					else {

						if (folder.isDirectory()) {

							if (carpeta) {
								lista.add(ruta + fichero);
							}

							else {

								fichero = fichero.trim();

								if (!fichero.isEmpty()) {
									lista.add(fichero);
								}

							}

						}

					}

				}

			}
		}

		catch (Exception e) {

		}

		return lista;

	}

	public static String eliminarPuntos(String cadena) {

		String cadena2 = cadena;

		try {
			cadena2 = cadena.substring(0, cadena.length() - 4);

			cadena = cadena2.replace(".", "_") + "." + extraerExtension(cadena);
		} catch (Exception e) {

		}

		return cadena;
	}

	public static String extraerExtension(String nombreArchivo) {

		String extension = "";

		if (nombreArchivo.length() >= 3) {

			extension = nombreArchivo.substring(nombreArchivo.length() - 3, nombreArchivo.length());

			extension = extension.toLowerCase();

			if (extension.equals("peg")) {
				extension = "jpeg";
			}

			if (extension.equals("fif")) {
				extension = "jfif";
			}

			if (extension.equals("ebp")) {
				extension = "webp";
			}

			if (extension.equals("ebm")) {
				extension = "webm";
			}

			if (extension.equals("3u8")) {
				extension = "m3u8";
			}

			if (extension.equals(".ts")) {
				extension = "ts";
			}

		}

		return extension;
	}

	public static String saberExtension(String text) {

		String resultado = "";

		try {
			resultado = text.substring(text.length() - 4, text.length());

		}

		catch (Exception e) {
		}

		return resultado;

	}

}
