package com.kipa.swf2js.tag;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.util.DataReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RemoveObject2Tag extends Tag {
    private int characterDepth;
    @Override
    public void validate() throws WrongTagException {
        if (this.tagType != 28 && this.data.length != 2) {
            throw new WrongTagException();
        }
        try {
            this.characterDepth = new DataReader(new ByteArrayInputStream(this.data)).readUnsignedShort();
        } catch (IOException e) {
            throw new WrongTagException(e.getMessage());
        }
    }

    public int getCharacterDepth() {
        return characterDepth;
    }
}
