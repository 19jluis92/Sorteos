/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.numeros.persistence;

import com.numeros.entity.Numeros;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.numeros.entity.Sorteo;
import com.numeros.persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;

/**
 *
 * @author jluis
 */
public class NumerosJpaController implements Serializable {

    public NumerosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Numeros numeros) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sorteo sorteoId = numeros.getSorteoId();
            if (sorteoId != null) {
                sorteoId = em.getReference(sorteoId.getClass(), sorteoId.getIdsorteo());
                numeros.setSorteoId(sorteoId);
            }
            em.persist(numeros);
            if (sorteoId != null) {
                sorteoId.getNumerosCollection().add(numeros);
                sorteoId = em.merge(sorteoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Numeros numeros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Numeros persistentNumeros = em.find(Numeros.class, numeros.getIdnumeros());
            Sorteo sorteoIdOld = persistentNumeros.getSorteoId();
            Sorteo sorteoIdNew = numeros.getSorteoId();
            if (sorteoIdNew != null) {
                sorteoIdNew = em.getReference(sorteoIdNew.getClass(), sorteoIdNew.getIdsorteo());
                numeros.setSorteoId(sorteoIdNew);
            }
            numeros = em.merge(numeros);
            if (sorteoIdOld != null && !sorteoIdOld.equals(sorteoIdNew)) {
                sorteoIdOld.getNumerosCollection().remove(numeros);
                sorteoIdOld = em.merge(sorteoIdOld);
            }
            if (sorteoIdNew != null && !sorteoIdNew.equals(sorteoIdOld)) {
                sorteoIdNew.getNumerosCollection().add(numeros);
                sorteoIdNew = em.merge(sorteoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = numeros.getIdnumeros();
                if (findNumeros(id) == null) {
                    throw new NonexistentEntityException("The numeros with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Numeros numeros;
            try {
                numeros = em.getReference(Numeros.class, id);
                numeros.getIdnumeros();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The numeros with id " + id + " no longer exists.", enfe);
            }
            Sorteo sorteoId = numeros.getSorteoId();
            if (sorteoId != null) {
                sorteoId.getNumerosCollection().remove(numeros);
                sorteoId = em.merge(sorteoId);
            }
            em.remove(numeros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Numeros> findNumerosEntities() {
        return findNumerosEntities(true, -1, -1);
    }

    public List<Numeros> findNumerosEntitiesBySorteId(int sorteoId) {
        return this.findNumerosEntitiesBySorteId(sorteoId, null, null);
    }

    public List<Numeros> findNumerosEntitiesBySorteId(int sorteoId, Date inicio, Date fin) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Numeros> criteria = em.getCriteriaBuilder().createQuery(Numeros.class);
            Root<Numeros> numerosRoot = criteria.from(Numeros.class);
            criteria.select(numerosRoot);
            Sorteo sorteo = new Sorteo(sorteoId);
            if (inicio != null && fin != null) {
                criteria.where(em.getCriteriaBuilder().equal(numerosRoot.get("sorteoId"), sorteo),
                        em.getCriteriaBuilder().between(numerosRoot.get("date"), inicio, fin));
            } else {
                criteria.where(em.getCriteriaBuilder().equal(numerosRoot.get("sorteoId"), sorteo));
            }

            Query q = em.createQuery(criteria);
            List<Numeros> result = q.getResultList();
            return result;
        } finally {
            em.close();
        }
    }

    public List<Numeros> findNumerosEntities(int maxResults, int firstResult) {
        return findNumerosEntities(false, maxResults, firstResult);
    }

    private List<Numeros> findNumerosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Numeros.class));
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

    public Numeros findNumeros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Numeros.class, id);
        } finally {
            em.close();
        }
    }

    public int getNumerosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Numeros> rt = cq.from(Numeros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Numeros> findNumerosByNumero(String numero, int sorteoTipo) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Numeros> criteria = cb.createQuery(Numeros.class);
            Root<Numeros> numerosRoot = criteria.from(Numeros.class);
            criteria.select(numerosRoot);
            Sorteo sorteo = new Sorteo(sorteoTipo);
            Query q = null;
            if (sorteoTipo == 3) {
                criteria.where(
                        em.getCriteriaBuilder().like(numerosRoot.get("numero"),
                                cb.parameter(String.class, "likeCondition")),
                        em.getCriteriaBuilder().equal(numerosRoot.get("sorteoId"), sorteo));
                q = em.createQuery(criteria);
                q.setParameter("likeCondition", numero + "%");
            } else {
                criteria.where(em.getCriteriaBuilder().equal(numerosRoot.get("numero"), numero),
                        em.getCriteriaBuilder().equal(numerosRoot.get("sorteoId"), sorteo));
                q = em.createQuery(criteria);
            }
            // criteria.where(em.getCriteriaBuilder().equal(numerosRoot.get("numero"),
            // numero),em.getCriteriaBuilder().equal(numerosRoot.get("sorteoId"), sorteo));

            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Numeros findLastNumeros(Integer sorteoId) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Numeros> criteria = em.getCriteriaBuilder().createQuery(Numeros.class);
            List<Order> orderList = new ArrayList();
            Root<Numeros> numerosRoot = criteria.from(Numeros.class);
            criteria.select(numerosRoot);
            Sorteo sorteo = new Sorteo(sorteoId);

            criteria.where(em.getCriteriaBuilder().equal(numerosRoot.get("sorteoId"), sorteo));
            orderList.add(em.getCriteriaBuilder().desc(numerosRoot.get("date")));
            criteria.orderBy(orderList);

            Query q = em.createQuery(criteria).setMaxResults(1);
            Numeros result = (Numeros) q.getSingleResult();
            return result;
        } finally {
            em.close();
        }
    }
}
