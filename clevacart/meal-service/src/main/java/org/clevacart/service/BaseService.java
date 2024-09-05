package org.clevacart.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public abstract class BaseService<T> {

    @Inject
    protected EntityManager entityManager;

    protected JsonObjectBuilder createJsonError(String errorMessage) {
        return Json.createObjectBuilder()
                .add("error", errorMessage);
    }

    protected JsonObject createJson(JsonObjectBuilder jsonBuilder) {
        return Json.createObjectBuilder()
                .add("success", true)
                .add("data", jsonBuilder)
                .build();
    }

    protected T findEntityById(Class<T> entityClass, int id) {
        return entityManager.find(entityClass, id);
    }

    protected T findEntityByField(Class<T> entityClass, String field, String value) throws NoResultException {
        //TypedQuery<T> query = entityManager.createQuery(
        //        "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + field + " = :value", entityClass);
        //query.setParameter("value", value);
        //return query.getSingleResult();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(field), value));

        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();

    }

    public abstract JsonObject getAll();

    public abstract JsonObject getById(int id);

    public abstract JsonObject getByName(String name);
}
