
package img_processing;

import java.io.IOException;
import java.util.LinkedList;

import principal.Main;
import utils.Metodos;

/**
 *
 * this class to extract the text from image it create another process and give
 * a commands for CMD to run Tesseract-OCR
 */

public class Text_Extractor {

	public static String get_Text(String input, int modo) throws IOException {

		String out_Text = "";

		String output_file, input_file;

		LinkedList<String> listaImagenes = new LinkedList<String>();

		if (modo == 1) {
			listaImagenes.add(input);
		}

		else {

			listaImagenes = Metodos.directorio(Main.getRuta().getText() + Main.getSeparador(), ".", true, true);
		}

		for (int i = 0; i < listaImagenes.size(); i++) {

			input_file = listaImagenes.get(i);

			output_file = input_file.substring(0, input_file.lastIndexOf(Main.getSeparador()) + 1) + "output"

					+ input_file.substring(input_file.lastIndexOf(Main.getSeparador()), input_file.lastIndexOf("."));

			String commands = "tesseract " + input_file + " " + output_file;

			if (Main.getSeparador().equals("\\")) {
				commands = "cmd.exe /K " + commands;
			}

			Metodos.crearFichero(output_file, "", false, true);

			output_file += ".txt";

			Process p;

			try {

				p = Runtime.getRuntime().exec(commands);

				p.waitFor();

				out_Text = Read_File.read_TXT_File(output_file).trim();

				Metodos.crearFichero(output_file, "\n" + input_file + "\n \n" + out_Text, false, false);

			}

			catch (Exception e) {

			}

		}

		return out_Text;

	}
}
