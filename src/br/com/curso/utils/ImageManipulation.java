package br.com.curso.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Classe que contem algoritmos de processamento de imagem
 * 
 * @author: Gabriel Tavares (Verde Tecnologia)
 *
 */
public class ImageManipulation {
    
    /**
     * Redimensiona uma BufferedImage, caso seja diferente de 0 ou diferente
     * do tamanho da imagem.
     * 
     * @param img
     * @param newW
     * @param newH
     * @return
     */
    public static Bitmap resize(Bitmap img, int newH, int newW) {
    	if((newW == 0 && newH == 0) || (img.getWidth() == newW && img.getHeight() == newH)){
        	return img;
        }
    	
        int width = img.getWidth();
        int height = img.getHeight();
        float scaleWidth = ((float) newW) / width;
        float scaleHeight = ((float) newH) / height;
        
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        try {
	        // "RECREATE" THE NEW BITMAP
	        Bitmap resizedBitmap = Bitmap.createBitmap(img, 0, 0, width, height, matrix, false);	        
	        return resizedBitmap;
	        
		} catch (OutOfMemoryError ex) {
			throw ex;
		}
    }
    
    /**
     * Save JPEG image with the compression specified.
     * @param image Image to be saved to file.
     * @param imageFile file to save image.
     * @param quality
     * @throws IOException if the image can't be saved or file is not found.
     */
    public static void saveImageJPGCompressed(final Bitmap image, final File file, final float quality) throws IOException {
		if (image == null) {
			throw new IllegalArgumentException(
					"\"image\" or \"imageFile\" params cannot be null.");
		}
        
		if (file.exists()) {
			file.delete();
		}

		try {
			FileOutputStream out = new FileOutputStream(file);
			image.compress(Bitmap.CompressFormat.JPEG, 90, out);
//			image.compress(Bitmap.CompressFormat.PNG, 85, out);

			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Rotates given image to given angle.
     * @param image image to be rotated.
     * @param angle angle to rotate image to
     * @return rotated image.
     */
    public static Bitmap rotate(Bitmap img, int degrees) {
        if (degrees != 0 && img != null) {
            Matrix m = new Matrix();

            m.setRotate(degrees, (float) img.getWidth() / 2, (float) img.getHeight() / 2);
            
            try {
                Bitmap rotated = Bitmap.createBitmap(
                        img, 0, 0, img.getWidth(), img.getHeight(), m, true);
                
                return rotated;
//                if (img != rotated) {
//                    img.recycle();
//                    img = rotated;
//                }
            } catch (OutOfMemoryError ex) {
               throw ex;
            }
        }
        return img;
    }
}
