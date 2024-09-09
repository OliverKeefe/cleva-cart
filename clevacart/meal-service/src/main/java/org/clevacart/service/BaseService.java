package org.clevacart.service;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T> {

    @Inject
    protected EntityManager entityManager;

    protected JsonObject createJsonError(String errorMessage) {
        return Json.createObjectBuilder()
                .add("error", errorMessage)
                .build();
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

    protected List<T> findAllByEntity(Class<T> entityClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        criteriaQuery.select(root);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Queries the Service database by optional field.
     * @param entityClass
     * @param field
     * @param value
     * @return {@link Optional}
     * @throws NoResultException
     */
    protected <V> Optional<T> findEntityByField(Class<T> entityClass, String field, V value) throws NoResultException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(field), value));

        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    protected <V> int deleteByField(Class<T> entityClass, String field, V value) throws NoResultException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<T> criteriaDelete = criteriaBuilder.createCriteriaDelete(entityClass);
        Root<T> root = criteriaDelete.from(entityClass);

        criteriaDelete.where(criteriaBuilder.equal(root.get(field), value));

        return entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    public abstract JsonObject getAll();

    public abstract JsonObject getById(int id);

    public abstract JsonObject getByName(String name);


}
