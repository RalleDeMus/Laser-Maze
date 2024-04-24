package Controller;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;

public class ImageHandler {

    public static BufferedImage transImage(BufferedImage image, float alphaValue) {
        // Create a new BufferedImage with the same dimensions and a format that supports alpha
        BufferedImage transparentImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Get the Graphics2D object of the new BufferedImage
        Graphics2D g2d = transparentImage.createGraphics();

        // Set the alpha composite to the alphaValue
        g2d.setComposite(AlphaComposite.SrcOver.derive(alphaValue));

        // Draw the original image onto the transparent image with the given transparency
        g2d.drawImage(image, 0, 0, null);

        // Dispose of the Graphics2D object
        g2d.dispose();

        // Return the image with adjusted transparency
        return transparentImage;
    }

    public static BufferedImage rotateImage(BufferedImage image, double degrees) {
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        AffineTransform at = new AffineTransform();
        at.translate((double) (newWidth - image.getWidth()) / 2, (double) (newHeight - image.getHeight()) / 2);
        at.rotate(radians, (double) image.getWidth() / 2, (double) image.getHeight() / 2);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

}