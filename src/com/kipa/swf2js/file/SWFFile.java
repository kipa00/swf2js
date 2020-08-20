package com.kipa.swf2js.file;

import com.kipa.swf2js.exception.NotAnSwfFileException;
import com.kipa.swf2js.exception.UnimplementedException;
import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.tag.Tag;
import com.kipa.swf2js.types.Rect;
import com.kipa.swf2js.util.BitReader;
import com.kipa.swf2js.util.DataReader;
import com.kipa.swf2js.util.Zlib;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class SWFFile extends File {
    private boolean compressed;
    private byte swfVersion;
    private Rect frameSize;
    private double frameRate;
    private int frameCount;
    private final List<Tag> tagList;

    public SWFFile() {
        this.frameSize = new Rect();
        this.tagList = new ArrayList<>();
    }

    @Override
    public void read(InputStream is) throws NotAnSwfFileException, IOException, DataFormatException, WrongTagException {
        int val = is.read();
        if (val == 0x43 || val == 0x46) {
            this.compressed = val == 0x43;
        } else {
            throw new NotAnSwfFileException("first signature byte mismatch");
        }
        val = is.read();
        if (val != 0x57) {
            throw new NotAnSwfFileException("second signature byte mismatch");
        }
        val = is.read();
        if (val != 0x53) {
            throw new NotAnSwfFileException("third signature byte mismatch");
        }
        val = is.read();
        if (val == -1) {
            throw new NotAnSwfFileException("swf version not present");
        }
        this.swfVersion = (byte)val;
        DataReader dr = new DataReader(is);
        long fileSize = dr.readUnsignedInt();
        byte[] data;
        if (this.compressed) {
            data = new byte[Math.toIntExact(fileSize)];
            byte[] compressedData = new byte[is.available()];
            dr.read(compressedData);
            Zlib.decompress(compressedData, data);
        } else {
            data = new byte[Math.toIntExact(fileSize - 8)];
            dr.read(data);
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        dr = new DataReader(bais);
        this.frameSize = new Rect();
        this.frameSize.read(new BitReader(bais));
        this.frameRate = dr.readUnsignedShort() / 256.;
        this.frameCount = dr.readUnsignedShort();
        Tag now;
        do {
            now = new Tag();
            now.read(bais);
            this.tagList.add(Tag.resolveTag(now));
        } while (now.getTagType() != 0);
    }

    @Override
    public void write(OutputStream os) throws Exception {
        throw new UnimplementedException();
    }

    public int getSwfVersion() {
        return this.swfVersion;
    }

    public double getFrameRate() {
        return this.frameRate;
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    public Rect getFrameSize() {
        return this.frameSize;
    }

    public List<Tag> getTagList() {
        return tagList;
    }
}
