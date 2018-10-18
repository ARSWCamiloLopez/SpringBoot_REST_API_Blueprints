/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintsFilter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author camilolopez
 */
//@Service
public class InMemoryBlueprintRedundancyFilter implements BlueprintsFilter {

    @Override
    public Blueprint blueprintsFilter(Blueprint bp) {
        List<Point> points = new ArrayList<>();
        if(bp.getPoints() != null){
            for(int x = 0; x < bp.getPoints().size() - 1; x++){
                if(bp.getPoints().get(x).getX() != bp.getPoints().get(x + 1).getX() ||
                        bp.getPoints().get(x).getY() != bp.getPoints().get(x + 1).getY()){
                    points.add(bp.getPoints().get(x));
                }
            }
            points.add(bp.getPoints().get((bp.getPoints().size()) - 1));
        }
        Blueprint bpToReturn = bp;
        bpToReturn.setPoints(points);
        return bpToReturn;
    }

}
