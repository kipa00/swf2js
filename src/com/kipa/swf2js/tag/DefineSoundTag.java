package com.kipa.swf2js.tag;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.util.DataReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DefineSoundTag extends Tag {
    private int soundId, flag;
    private byte[] mp3Data;
    private int sampleCount;

    @Override
    public void validate() throws WrongTagException {
        ByteArrayInputStream bais = new ByteArrayInputStream(this.data);
        DataReader dr = new DataReader(bais);
        try {
            this.soundId = dr.readUnsignedShort();
            this.flag = dr.read();
            if ((this.flag >> 4) != 2) {
                throw new WrongTagException("unimplemented sound");
            }
            this.sampleCount = dr.readInt();
            dr.readShort(); // TODO: implement this latency
            this.mp3Data = new byte[this.data.length - 9];
            dr.read(this.mp3Data);
        } catch (IOException e) {
            throw new WrongTagException();
        }
    }

    public int getSoundId() {
        return soundId;
    }

    public byte[] getMp3Data() {
        return mp3Data;
    }
}
