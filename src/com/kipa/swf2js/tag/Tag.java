package com.kipa.swf2js.tag;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.util.DataReader;

import java.io.IOException;
import java.io.InputStream;

public class Tag {
    protected int tagType;
    protected byte[] data;

    public void read(InputStream is) throws IOException, WrongTagException {
        DataReader dr = new DataReader(is);
        int flag = dr.readUnsignedShort();
        this.tagType = flag >>> 6;
        flag &= 63;
        if (flag == 63) {
            flag = dr.readInt();
        }
        this.data = new byte[flag];
        dr.read(this.data);
        this.validate();
    }

    public Tag fromTag(Tag tag) throws WrongTagException {
        this.tagType = tag.tagType;
        this.data = tag.data.clone();
        this.validate();
        return this;
    }

    public void validate() throws WrongTagException {
    }

    public int getTagType() {
        return tagType;
    }

    public static Tag resolveTag(Tag tag) {
        try {
            switch (tag.getTagType()) {
                case 1:
                    return new ShowFrameTag().fromTag(tag);
                case 2:
                    return new DefineShapeTag().fromTag(tag);
                case 14:
                    return new DefineSoundTag().fromTag(tag);
                case 21:
                    return new DefineBitsJPEG2Tag().fromTag(tag);
                case 22:
                    return new DefineShape2Tag().fromTag(tag);
                case 28:
                    return new RemoveObject2Tag().fromTag(tag);
                case 32:
                    return new DefineShape3Tag().fromTag(tag);
                case 35:
                    return new DefineBitsJPEG3Tag().fromTag(tag);
                default:
                    return new UnknownTag().fromTag(tag);
            }
        } catch (WrongTagException err) {
            System.err.println("Something is wrong with the tag resolution: " + err.getMessage());
            // err.printStackTrace();
            /*
            System.err.printf("%d byte(s)\n", tag.data.length);
            for (byte b: tag.data) {
                System.err.printf("%02x ", b);
            }
            System.err.println();
            //*/
        }
        return new UnknownTag().fromTag(tag);
    }
}
