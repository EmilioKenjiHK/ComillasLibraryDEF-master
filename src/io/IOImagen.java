package io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class IOImagen {
    public static void setImagen(File imagen, String direccion) {
        try {
            BufferedImage bi = ImageIO.read(imagen);
            File f = new File(direccion);
            ImageIO.write(bi,"png",f);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void setTexto(String texto,String direccion)
    {
        try{
            FileWriter fw = new FileWriter(direccion);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(texto);
            bw.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
