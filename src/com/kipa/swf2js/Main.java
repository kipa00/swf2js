package com.kipa.swf2js;

import com.kipa.swf2js.file.JPEGFile;
import com.kipa.swf2js.file.MP3File;
import com.kipa.swf2js.file.SWFFile;
import com.kipa.swf2js.file.svg.SVGFile;
import com.kipa.swf2js.tag.*;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(args[0]));
            SWFFile swf = new SWFFile();
            swf.read(bis);
            System.out.println("swf version: " + swf.getSwfVersion());
            System.out.println("stage size: " + swf.getFrameSize());
            System.out.println("desired fps: " + swf.getFrameRate());
            System.out.println("# of frames: " + swf.getFrameCount());
            int count = 0, unknownCount = 0;
            for (Tag tag: swf.getTagList()) {
                if (tag == null || tag instanceof UnknownTag) {
                    ++unknownCount;
                } else if (tag instanceof DefineShape2Tag) {
                    DefineShape2Tag defineTag = (DefineShape2Tag)tag;
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("./resources/" + defineTag.getShapeId() + ".svg"));
                    SVGFile file = new SVGFile();
                    file.fromDefineShape2(defineTag);
                    file.write(bos);
                    bos.flush();
                    bos.close();
                } else if (tag instanceof DefineBitsJPEG2Tag) {
                    DefineBitsJPEG2Tag defineTag = (DefineBitsJPEG2Tag)tag;
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("./resources/" + defineTag.getCharacterId() + ".jpeg"));
                    JPEGFile file = new JPEGFile();
                    file.fromDefineBitsJPEG2(defineTag);
                    file.write(bos);
                    bos.flush();
                    bos.close();
                } else if (tag instanceof DefineSoundTag) {
                    DefineSoundTag defineTag = (DefineSoundTag)tag;
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("./resources/" + defineTag.getSoundId() + ".mp3"));
                    MP3File file = new MP3File();
                    file.fromDefineSound(defineTag);
                    file.write(bos);
                    bos.flush();
                    bos.close();
                }
                ++count;
            }
            System.out.println(unknownCount + " out of " + count + " unknown");
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
