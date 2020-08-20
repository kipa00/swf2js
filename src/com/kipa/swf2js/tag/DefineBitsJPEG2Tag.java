package com.kipa.swf2js.tag;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.util.DataReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DefineBitsJPEG2Tag extends Tag {
    private int characterId;
    private byte[] imageData;

    @Override
    public void validate() throws WrongTagException {
        ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
        try {
            DataReader dr = new DataReader(bais);
            this.characterId = dr.readUnsignedShort();
            this.imageData = this.data.clone();
            if (dr.readInt() == -654321153) {
                this.imageData = new byte[this.data.length - 6];
                System.arraycopy(this.data, 6, this.imageData, 0, this.imageData.length);
            } else {
                this.imageData = new byte[this.data.length - 2];
                System.arraycopy(this.data, 2, this.imageData, 0, this.imageData.length);
            }
        } catch (IOException e) {
            throw new WrongTagException();
        }
    }

    public int getCharacterId() {
        return characterId;
    }

    public byte[] getImageData() {
        return imageData;
    }
}
