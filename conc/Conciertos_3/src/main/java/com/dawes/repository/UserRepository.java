package com.dawes.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dawes.modelo.UsuarioVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

@Repository
@Transactional
public class UserRepository {

   @Autowired
   private EntityManager entityManager;   
   
   public UsuarioVO findUserAccount(String userName) {
       try {
           Query query = entityManager.createQuery("select u from UsuarioVO u where u.userName=:nombre", UsuarioVO.class);
           query.setParameter("nombre", userName);
           return (UsuarioVO) query.getSingleResult();
       } catch (NoResultException e) {
           return null;
       }
   }
  
}