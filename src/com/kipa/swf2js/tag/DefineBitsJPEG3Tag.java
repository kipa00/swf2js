package com.kipa.swf2js.tag;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.util.DataReader;
import com.kipa.swf2js.util.Zlib;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.zip.DataFormatException;

public class DefineBitsJPEG3Tag extends DefineBitsJPEG2Tag {
    @Override
    public void validate() throws WrongTagException {
        ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
        try {
            DataReader dr = new DataReader(bais);
            this.characterId = dr.readUnsignedShort();
            int alphaDataOffset = dr.readInt();
            this.imageData = new byte[alphaDataOffset];
            dr.read(this.imageData);
            if (new DataReader(new ByteArrayInputStream(this.imageData)).readInt() == -654321153) {
                byte[] temp = new byte[this.imageData.length - 4];
                System.arraycopy(this.imageData, 4, temp, 0, temp.length);
                this.imageData = temp;
            }
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(this.imageData));
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int width = image.getWidth(), height = image.getHeight();

            byte[] compressedAlphas = new byte[this.data.length - 6 - alphaDataOffset], alphas = new byte[width * height];
            dr.read(compressedAlphas);
            Zlib.decompress(compressedAlphas, alphas);
            dr = new DataReader(new ByteArrayInputStream(alphas));

            for (int i=0; i<height; ++i) {
                for (int j=0; j<width; ++j) {
                    int color = image.getRGB(j, i);
                    color = (dr.read() << 24) | (color & 0xffffff);
                    newImage.setRGB(j, i, color);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(newImage, "png", baos);
            this.imageData = baos.toByteArray();
        } catch (IOException | DataFormatException e) {
            throw new WrongTagException();
        }
    }
}
