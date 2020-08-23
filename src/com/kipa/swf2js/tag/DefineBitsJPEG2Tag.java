package com.kipa.swf2js.tag;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.util.DataReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DefineBitsJPEG2Tag extends Tag {
    protected int characterId;
    protected byte[] imageData;

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

    public String getExtension() {
        if (this.imageData == null || this.imageData.length == 0) {
            return null;
        }
        if (this.imageData[0] == (byte)0x47) {
            return "gif";
        }
        if (this.imageData[0] == (byte)0x89) {
            return "png";
        }
        return "jpg";
    }
}
