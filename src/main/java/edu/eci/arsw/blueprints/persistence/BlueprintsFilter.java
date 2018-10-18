/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 *
 * @author camilolopez
 */
public interface BlueprintsFilter {

    /**
     *
     * @param bp
     * @return
     */
    public Blueprint blueprintsFilter(Blueprint bp);

}
