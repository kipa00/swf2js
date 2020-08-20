package com.kipa.swf2js.tag;

public class UnknownTag extends Tag {
    @Override
    public Tag fromTag(Tag tag) {
        this.tagType = tag.tagType;
        this.data = tag.data.clone();
        return this;
    }
}
