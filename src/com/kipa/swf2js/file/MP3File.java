package com.kipa.swf2js.file;

import com.kipa.swf2js.exception.UnimplementedException;
import com.kipa.swf2js.tag.DefineSoundTag;

import java.io.InputStream;
import java.io.OutputStream;

public class MP3File extends File {
    private byte[] data;

    public void fromDefineSound(DefineSoundTag tag) {
        this.data = tag.getMp3Data().clone();
    }

    @Override
    public void read(InputStream is) throws Exception {
        throw new UnimplementedException();
    }

    @Override
    public void write(OutputStream os) throws Exception {
        os.write(this.data);
    }
}
