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
}
