package com.kipa.swf2js.file;

import com.kipa.swf2js.exception.UnimplementedException;
import com.kipa.swf2js.tag.DefineBitsJPEG2Tag;

import java.io.InputStream;
import java.io.OutputStream;

public class JPEGFile extends File {
    private byte[] data;

    public void fromDefineBitsJPEG2(DefineBitsJPEG2Tag tag) {
        this.data = tag.getImageData().clone();
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
