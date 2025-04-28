package com.grandcapital.transactor.DAO;

import com.grandcapital.transactor.DAO.entity.EmailData;
import com.grandcapital.transactor.DAO.entity.PhoneData;
import com.grandcapital.transactor.DAO.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UsersSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findUsersWithFilters(LocalDate dateOfBirth, String phone, String name, String email, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        List<Predicate> predicates = buildPredicates(criteriaBuilder, root, dateOfBirth, phone, name, email);
        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    public long countUsersWithFilters(LocalDate dateOfBirth, String phone, String name, String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count(root));

        List<Predicate> predicates = buildPredicates(criteriaBuilder, root, dateOfBirth, phone, name, email);
        if (!predicates.isEmpty()) {
            countQuery.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private List<Predicate> buildPredicates(CriteriaBuilder criteriaBuilder, Root<User> root,
                                            LocalDate dateOfBirth, String phone, String name, String email) {

        List<Predicate> predicates = new ArrayList<>();

        if (dateOfBirth != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("dateOfBirth"), dateOfBirth));
        }
        if (phone != null) {
            Join<User, PhoneData> phoneJoin = root.join("phones", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(phoneJoin.get("phone"), phone));
        }
        if (name != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), name + "%"));
        }
        if (email != null) {
            Join<User, EmailData> emailJoin = root.join("emails", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(emailJoin.get("email"), email));
        }

        return predicates;
    }
}
