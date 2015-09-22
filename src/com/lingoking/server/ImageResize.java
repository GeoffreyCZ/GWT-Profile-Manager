package com.lingoking.server;


import com.lingoking.shared.model.Profile;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResize {

    private static final int IMAGE_WIDTH = 90;
    private static final int IMAGE_HEIGHT = 90;

    public static void createThumbnail(Profile profile) throws IOException {
        File file = new File(UploadServlet.IMAGES_DIRECTORY + profile.getAvatar());
        BufferedImage img = ImageIO.read(file);
        BufferedImage thumbImg = Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, IMAGE_WIDTH, IMAGE_HEIGHT, Scalr.OP_ANTIALIAS);
        File f2 = new File(UploadServlet.IMAGES_DIRECTORY + "thumb_" + profile.getAvatar());
        ImageIO.write(thumbImg, "jpg", f2);
    }

}