package com.kipa.swf2js.file.svg;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.file.svg.planar.Edge;
import com.kipa.swf2js.file.svg.planar.Point;
import com.kipa.swf2js.file.svg.planar.StyledEdge;
import com.kipa.swf2js.types.shape.FillStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SVGShape {
    private List<SVGFillPath> svgFillPaths;
    private List<SVGStrokePath> svgStrokePaths;

    public SVGShape() {
        svgFillPaths = new ArrayList<>();
        svgStrokePaths = new ArrayList<>();
    }

    public SVGShape(List<StyledEdge> list) throws WrongTagException {
        this();
        this.fromStyledEdgeList(list);
    }

    public void fromStyledEdgeList(List<StyledEdge> list) throws WrongTagException {
        Map<FillStyle, List<Edge>> process = new HashMap<>();
        Point lastPoint = new Point();
        for (StyledEdge edge: list) {
            FillStyle left = edge.getLeftFillStyle(), right = edge.getRightFillStyle();
            if (left != null) {
                List<Edge> leftEdges = process.computeIfAbsent(left, k -> new ArrayList<>());
                leftEdges.add(edge.getEdge());
            }
            if (right != null) {
                List<Edge> rightEdges = process.computeIfAbsent(right, k -> new ArrayList<>());
                rightEdges.add(edge.getEdge().reverse());
            }
        }
        for (FillStyle key: process.keySet()) {
            SVGFillPath path = new SVGFillPath(key);
            path.fromEdges(process.get(key));
            this.svgFillPaths.add(path);
        }
        SVGStrokePath.fromStyledEdges(list, this.svgStrokePaths);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SVGFillPath fill: this.svgFillPaths) {
            sb.append("  ");
            sb.append(fill);
            sb.append('\n');
        }
        for (SVGStrokePath stroke: this.svgStrokePaths) {
            sb.append("  ");
            sb.append(stroke);
            sb.append('\n');
        }
        return sb.toString();
    }
}
