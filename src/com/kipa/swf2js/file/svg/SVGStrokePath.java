package com.kipa.swf2js.file.svg;

import com.kipa.swf2js.exception.WrongTagException;
import com.kipa.swf2js.file.svg.planar.Edge;
import com.kipa.swf2js.file.svg.planar.Point;
import com.kipa.swf2js.file.svg.planar.StyledEdge;
import com.kipa.swf2js.types.shape.LineStyle;

import java.util.ArrayList;
import java.util.List;

public class SVGStrokePath {
    private List<Edge> edges;
    private LineStyle lineStyle;

    public SVGStrokePath(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public static void fromStyledEdges(List<StyledEdge> list, List<SVGStrokePath> result) throws WrongTagException {
        SVGStrokePath lastPath = result.isEmpty() ? null : result.get(result.size() - 1);
        for (StyledEdge edge: list) {
            if (edge.getLineStyle() != null) {
                if (lastPath == null || lastPath.lineStyle != edge.getLineStyle()) {
                    lastPath = new SVGStrokePath(edge.getLineStyle());
                    result.add(lastPath);
                }
                lastPath.addEdge(edge.getEdge());
            }
        }
    }

    public String buildPath() {
        Point lastPoint = null;
        StringBuilder pathBuilder = new StringBuilder();
        for (Edge edge: edges) {
            pathBuilder.append(edge.toString(lastPoint));
            pathBuilder.append(' ');
            lastPoint = edge.getEnd();
        }
        return pathBuilder.toString().trim();
    }

    @Override
    public String toString() {
        String path = "d=\"" + buildPath() + "\"";
        return "<path " + path + " " + lineStyle + " fill=\"none\"/>";
    }
}
