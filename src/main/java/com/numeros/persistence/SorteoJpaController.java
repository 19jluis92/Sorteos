/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.numeros.persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.numeros.entity.Numeros;
import com.numeros.entity.Sorteo;
import com.numeros.persistence.exceptions.IllegalOrphanException;
import com.numeros.persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jluis
 */
public class SorteoJpaController implements Serializable {

    public SorteoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sorteo sorteo) {
        if (sorteo.getNumerosCollection() == null) {
            sorteo.setNumerosCollection(new ArrayList<Numeros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Numeros> attachedNumerosCollection = new ArrayList<Numeros>();
            for (Numeros numerosCollectionNumerosToAttach : sorteo.getNumerosCollection()) {
                numerosCollectionNumerosToAttach = em.getReference(numerosCollectionNumerosToAttach.getClass(), numerosCollectionNumerosToAttach.getIdnumeros());
                attachedNumerosCollection.add(numerosCollectionNumerosToAttach);
            }
            sorteo.setNumerosCollection(attachedNumerosCollection);
            em.persist(sorteo);
            for (Numeros numerosCollectionNumeros : sorteo.getNumerosCollection()) {
                Sorteo oldSorteoIdOfNumerosCollectionNumeros = numerosCollectionNumeros.getSorteoId();
                numerosCollectionNumeros.setSorteoId(sorteo);
                numerosCollectionNumeros = em.merge(numerosCollectionNumeros);
                if (oldSorteoIdOfNumerosCollectionNumeros != null) {
                    oldSorteoIdOfNumerosCollectionNumeros.getNumerosCollection().remove(numerosCollectionNumeros);
                    oldSorteoIdOfNumerosCollectionNumeros = em.merge(oldSorteoIdOfNumerosCollectionNumeros);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sorteo sorteo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sorteo persistentSorteo = em.find(Sorteo.class, sorteo.getIdsorteo());
            Collection<Numeros> numerosCollectionOld = persistentSorteo.getNumerosCollection();
            Collection<Numeros> numerosCollectionNew = sorteo.getNumerosCollection();
            List<String> illegalOrphanMessages = null;
            for (Numeros numerosCollectionOldNumeros : numerosCollectionOld) {
                if (!numerosCollectionNew.contains(numerosCollectionOldNumeros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Numeros " + numerosCollectionOldNumeros + " since its sorteoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Numeros> attachedNumerosCollectionNew = new ArrayList<Numeros>();
            for (Numeros numerosCollectionNewNumerosToAttach : numerosCollectionNew) {
                numerosCollectionNewNumerosToAttach = em.getReference(numerosCollectionNewNumerosToAttach.getClass(), numerosCollectionNewNumerosToAttach.getIdnumeros());
                attachedNumerosCollectionNew.add(numerosCollectionNewNumerosToAttach);
            }
            numerosCollectionNew = attachedNumerosCollectionNew;
            sorteo.setNumerosCollection(numerosCollectionNew);
            sorteo = em.merge(sorteo);
            for (Numeros numerosCollectionNewNumeros : numerosCollectionNew) {
                if (!numerosCollectionOld.contains(numerosCollectionNewNumeros)) {
                    Sorteo oldSorteoIdOfNumerosCollectionNewNumeros = numerosCollectionNewNumeros.getSorteoId();
                    numerosCollectionNewNumeros.setSorteoId(sorteo);
                    numerosCollectionNewNumeros = em.merge(numerosCollectionNewNumeros);
                    if (oldSorteoIdOfNumerosCollectionNewNumeros != null && !oldSorteoIdOfNumerosCollectionNewNumeros.equals(sorteo)) {
                        oldSorteoIdOfNumerosCollectionNewNumeros.getNumerosCollection().remove(numerosCollectionNewNumeros);
                        oldSorteoIdOfNumerosCollectionNewNumeros = em.merge(oldSorteoIdOfNumerosCollectionNewNumeros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sorteo.getIdsorteo();
                if (findSorteo(id) == null) {
                    throw new NonexistentEntityException("The sorteo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sorteo sorteo;
            try {
                sorteo = em.getReference(Sorteo.class, id);
                sorteo.getIdsorteo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sorteo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Numeros> numerosCollectionOrphanCheck = sorteo.getNumerosCollection();
            for (Numeros numerosCollectionOrphanCheckNumeros : numerosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sorteo (" + sorteo + ") cannot be destroyed since the Numeros " + numerosCollectionOrphanCheckNumeros + " in its numerosCollection field has a non-nullable sorteoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sorteo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sorteo> findSorteoEntities() {
        return findSorteoEntities(true, -1, -1);
    }

    public List<Sorteo> findSorteoEntities(int maxResults, int firstResult) {
        return findSorteoEntities(false, maxResults, firstResult);
    }

    private List<Sorteo> findSorteoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sorteo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Sorteo findSorteo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sorteo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSorteoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sorteo> rt = cq.from(Sorteo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
