package com.example.spring_api_restful.domain.repository;

import com.example.spring_api_restful.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
}
