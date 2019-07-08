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
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JFileChooser;

/*
 *this class created to carray out all file operationd like reading content , getting path , ... 
 * idea :- just we will open the fill and loop on it's lines --> store this in buffer while reading 
 */
public class Read_File {

    public static String read_TXT_File(String file_name) {
        BufferedReader br = null;  // buffer to store the lines which we will read 
        String out = ""; // hold output which we will read

        // be careful here we can get exeption like IOExeption 
        try {
            String currentLine; // hold the current line 
            br = new BufferedReader(new FileReader(file_name));
            // now we will loop on the file line by line 
            while ((currentLine = br.readLine()) != null) {
                out = out + currentLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return out;
    }

    // this method to get the path of the selected Img or any selected file 
    static public String get_FileParh() {
        String path = null;

        JFileChooser chooser = new JFileChooser();
        // check if the ok is clicked ?
        int val = chooser.showOpenDialog(null);
        if (val == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getPath();
        }

        return path;
    }
}
