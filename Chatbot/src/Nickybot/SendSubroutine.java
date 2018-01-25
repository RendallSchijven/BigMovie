package Nickybot;

import com.rivescript.macro.Subroutine;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.imageio.ImageIO;

/**
 *
 * @author Groep 16
 */
public class SendSubroutine implements Subroutine {
    DefaultAbsSender das;

    public SendSubroutine(DefaultAbsSender sender) {
        das = sender;
    }

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {
        String type = args[0];
        if ("photo".equals(type)) {
            String f_id = args[1];

            /**
             * A randomizer that calculates a random number between 1 and 11, if the number is 7 run the merge easter egg
             */
            Random rand = new Random();
            int  n = rand.nextInt(8) + 1;
            if(n == 2){merge(f_id);}

            String caption = "";
            for (int i = 2; i < args.length; i++)
                caption = caption + " " + args[i];
            caption = caption.trim();
            SendPhoto msg = new SendPhoto()
                    .setChatId(rs.currentUser())
                    .setNewPhoto(new File(f_id))
                    .setCaption(caption);
            try {
                das.sendPhoto(msg); // Call method to send the photo with caption
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * Easter egg that places the graph into the photograph Chad Kroeger is holding in Nickelback`s hit song 'Photograph'
     */
    private void merge(String s) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Chatbot/resources/images/nickelback.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage overlay = null;
        try {
            overlay = ImageIO.read(new File(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // resize the image
        int type = overlay.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : overlay.getType();
        BufferedImage resizedImage = new BufferedImage(440, 300, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(overlay, 0, 0, 440, 300, null);
        g.dispose();

        //rotate the image

        AffineTransform tx = new AffineTransform();
        tx.rotate(-0.23, resizedImage.getWidth() + 800 / 2, resizedImage.getHeight() - 800 / 2);

        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        resizedImage = op.filter(resizedImage, null);

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics gr = combined.getGraphics();
        gr.drawImage(image, 0, 0, null);
        gr.drawImage(resizedImage, 650, 134, null);

        // Save as new image
        try {
            ImageIO.write(combined, "PNG", new File(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}