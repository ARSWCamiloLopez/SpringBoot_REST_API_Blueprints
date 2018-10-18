/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    ApplicationContext bPrints = new ClassPathXmlApplicationContext("applicationContext.xml");
    BlueprintsServices bpServices = bPrints.getBean(BlueprintsServices.class);

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Loading a previously stored blueprint returned null.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Loading a previously stored blueprint returned a different blueprint.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);

    }

    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        } catch (BlueprintPersistenceException ex) {

        }

    }

    @Test
    public void blueprintsByAuthorTest() throws BlueprintNotFoundException, BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Blueprint bp01 = new Blueprint("Camilo", "Firstbp");
        Blueprint bp02 = new Blueprint("Camilo", "Secondbp");
        Blueprint bp03 = new Blueprint("Alex", "Thirdbp");
        Blueprint bp04 = new Blueprint("Andres", "Fourthbp");

        ibpp.saveBlueprint(bp01);
        ibpp.saveBlueprint(bp02);
        ibpp.saveBlueprint(bp03);
        ibpp.saveBlueprint(bp04);

        assertEquals(ibpp.getBlueprintsByAuthor("Camilo").size(), 2);
    }

    @Test
    public void blueprintByAuthorTest() throws BlueprintNotFoundException, BlueprintPersistenceException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Blueprint bp03 = new Blueprint("Alex", "Thirdbp");
        ibpp.saveBlueprint(bp03);

        assertEquals(ibpp.getBlueprint("Alex", "Thirdbp").getAuthor(), "Alex");
    }

    @Test
    public void blueprintFilterRedundancy() throws BlueprintPersistenceException, BlueprintNotFoundException {

        Point po1 = new Point(1, 1);
        Point po2 = new Point(1, 1);
        Point po3 = new Point(1, 2);
        Point po4 = new Point(2, 2);

        Point[] points = {po1, po2, po3, po4};

        Blueprint bp = new Blueprint("Camilo", "First", points);
        bpServices.addNewBlueprint(bp);

        assertEquals(bpServices.getBlueprint("Camilo", "First").getPoints().size(),
                3);
    }

    @Test
    public void blueprintFilterSubsampling() throws BlueprintPersistenceException, BlueprintNotFoundException {

        Point po1 = new Point(1, 1);
        Point po2 = new Point(1, 1);
        Point po3 = new Point(1, 2);
        Point po4 = new Point(2, 2);

        Point[] points = {po1, po2, po3, po4};

        Blueprint bp = new Blueprint("Camilo", "First", points);
        bpServices.addNewBlueprint(bp);

        assertEquals(bpServices.getBlueprint("Camilo", "First").getPoints().size(),
                2);
    }
}
