/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.numeros.bll;

import com.numeros.entity.Numeros;
import com.numeros.persistence.NumerosJpaController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jluis
 */
public class CoreBll {
    EntityManagerFactory emf;

    public CoreBll() {
        emf = Persistence.createEntityManagerFactory("SorteosPU");
    }

    public void create(Numeros item) {
        NumerosJpaController controller = new NumerosJpaController(emf);
        controller.create(item);
    }

    public List<Numeros> getAll() {

        NumerosJpaController controller = new NumerosJpaController(emf);
        return controller.findNumerosEntities();
    }

    public List<Numeros> getAllBySorteo(int sorteoId) {

        NumerosJpaController controller = new NumerosJpaController(emf);
        return controller.findNumerosEntitiesBySorteId(sorteoId, null, null);
    }

    public List<Numeros> getAllBySorteo(int sorteoId, Date inicio, Date fin) {

        NumerosJpaController controller = new NumerosJpaController(emf);
        return controller.findNumerosEntitiesBySorteId(sorteoId, inicio, fin);
    }

    public List<Numeros> getAllbyNumero(String numero, int sorteoTipo) {
        NumerosJpaController controller = new NumerosJpaController(emf);
        return controller.findNumerosByNumero(numero, sorteoTipo);
    }

    public Numeros getLastNumero(int sorteoTipo) {
        NumerosJpaController controller = new NumerosJpaController(emf);
        return controller.findLastNumeros(sorteoTipo);
    }
}
