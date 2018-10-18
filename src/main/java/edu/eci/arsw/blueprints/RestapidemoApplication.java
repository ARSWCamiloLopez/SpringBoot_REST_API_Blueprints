/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author camilolopez
 */
@SpringBootApplication
public class RestapidemoApplication {

    static BlueprintsServices bpServices;
    
    /**
     * @param args the command line arguments
     * @throws edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException
     * @throws edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException
     */
    public static void main(String[] args) throws BlueprintPersistenceException, BlueprintNotFoundException {

        ApplicationContext bPrints = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        bpServices = bPrints.getBean(BlueprintsServices.class);
        
        Point po1 = new Point(1, 1);
        Point po2 = new Point(1, 1);
        Point po3 = new Point(1, 2);
        Point po4 = new Point(2, 2);

        Point[] points = {po1, po2, po3, po4};

        Blueprint bp = new Blueprint("Camilo", "First", points);
        bpServices.addNewBlueprint(bp);

        System.out.println(bpServices.getAllBlueprints());
    }

}
