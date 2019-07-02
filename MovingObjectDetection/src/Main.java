import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        int amountOfPhotographs = new File("/Users/csstu/Desktop/data3/").list().length;
        amountOfPhotographs --;
        int width = 0, height = 0;
        int counter = 0;

        File a = new File("/Users/csstu/Desktop/data3/" + 1 + ".jpg");
        BufferedImage sizeHandler = ImageIO.read(a);
        Integer array[][][] = new Integer[amountOfPhotographs][sizeHandler.getWidth()][sizeHandler.getHeight()];

        for (int i = 1; i <= amountOfPhotographs; i++) {
            System.out.println(i);
            BufferedImage img = null;
            File f;
            try {
                f = new File("/Users/csstu/Desktop/data3/" + i + ".jpg");
                img = ImageIO.read(f);

            } catch (IOException e) {
                System.out.println("Hi" + e);
            }
            height = sizeHandler.getHeight();
            width = sizeHandler.getWidth();

            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    array[i - 1][w][h] = img.getRGB(w, h);
                }
            }
        }

        System.out.println("Tüm fotoğraflar tarandı..");
        System.out.println("Arkaplan medyan alınarak yazdırılacak yazdırılacak");
        List<Integer> tempList = new ArrayList<>();
        BufferedImage backgroundImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);


        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                for (int i = 1; i <= amountOfPhotographs; i++) {

                    tempList.add(array[i - 1][w][h]);
                }
                Collections.sort(tempList);
                backgroundImg.setRGB(w, h, tempList.get(tempList.size() / 2));
                tempList.clear();
            }
        }

        File outputfile = new File("/Users/csstu/Desktop/background3.png");
        try {
            ImageIO.write(backgroundImg, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Yazdırma işlemi bitti");
        //------- ARKAPLAN YAZDIRILDI -----> KARŞILAŞTIRILMA YAPILACAK
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Integer redArray[][][] = new Integer[amountOfPhotographs][width][height];
        File redOutput = null;
        for (int i = 1; i <= amountOfPhotographs; i++) { //  frame
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    Color background = new Color(backgroundImg.getRGB(w, h));
                    Color current = new Color(array[i - 1][w][h]);
                    if (similarTo(background, current)) { // Fark varsa
                        counter++;
                    }

                }
            }
            if(counter > 50) {
                File mainPhotoFile = new File("/Users/csstu/Desktop/data3/" + i + ".jpg");
                BufferedImage mainPhoto = ImageIO.read(mainPhotoFile);
                redOutput = new File("/Users/csstu/Desktop/RedImages3/" + i + ".png");
                ImageIO.write(mainPhoto, "png", redOutput);
                counter = 0;
            }

        }
    }

    static boolean similarTo(Color background, Color current) {
        double distance =
                (background.getRed() - current.getRed()) * (background.getRed() - current.getRed()) +
                        (background.getGreen() - current.getGreen()) * (background.getGreen() - current.getGreen()) +
                        (background.getBlue() - current.getBlue()) * (background.getBlue() - current.getBlue());
        if (distance > 3500) {
            return true;
        } else {
            return false;
        }
    }



}

