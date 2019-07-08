/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package img_processing;

/**
 *
 * @author h.omar
 */
import java.io.PrintWriter;

/**
 *
 * this class to extract the text from image it create another process and give
 * a commands for CMD to run Tesseract-OCR
 */
public class Text_Extractor {

// method to get the text which is recognized
    public static String get_Text(String imageName) {
        
        
        String out_Text = null; // hold the value of output 
        String input_file = "C:\\Program Files (x86)\\Tesseract-OCR\\"+imageName;
        String output_file = "C:\\Users\\h.omar\\Documents\\TR_LTA\\OUT\\1";
        String tesseract_install_path = "C:\\Program Files (x86)\\Tesseract-OCR\\tesseract";
        // array of commands which we will pass to the command 
        String[] commands
                = {
                    "cmd",};
        // define process "this to run cmd "
        Process p;
        try {
            p = Runtime.getRuntime().exec(commands);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("\"" + tesseract_install_path + "\" \"" + input_file + "\" \"" + output_file + "\" -l eng");
            stdin.close();
            p.waitFor();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(Read_File.read_TXT_File(output_file + ".txt"));
            out_Text = Read_File.read_TXT_File(output_file + ".txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return out_Text;
    }
}
