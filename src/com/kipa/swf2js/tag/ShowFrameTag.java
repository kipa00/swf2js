package com.kipa.swf2js.tag;

import com.kipa.swf2js.exception.WrongTagException;

import java.io.IOException;
import java.io.InputStream;

public class ShowFrameTag extends Tag {
    @Override
    public void validate() throws WrongTagException {
        if (this.tagType != 1 && this.data.length != 0) {
            throw new WrongTagException();
        }
    }
}
