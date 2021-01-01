package com.kipa.swf2js.tag;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.util.DataReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DefineSpriteTag extends Tag {
    private int spriteId, frameCount;

    @Override
    public void validate() throws WrongTagException {
        ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
        try {
            DataReader dr = new DataReader(bais);
            this.spriteId = dr.readUnsignedShort();
            this.frameCount = dr.readUnsignedShort();
            Tag now;
            do {
                now = new Tag();
                now.read(bais);
                this.feed(Tag.resolveTag(now));
            } while (now.getTagType() != 0);
        } catch (IOException err) {
            throw new WrongTagException();
        }
    }

    public void feed(Tag tag) {
        // TODO: place/remove objects
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getSpriteId() {
        return spriteId;
    }
}
