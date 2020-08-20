package com.kipa.swf2js.tag;

import com.kipa.swf2js.types.ShapeWithStyle;

public class DefineShape3Tag extends DefineShape2Tag {
    public DefineShape3Tag() {
        super();
        this.shapeDefinition = new ShapeWithStyle(true);
    }
}
