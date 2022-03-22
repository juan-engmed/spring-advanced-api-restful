package com.example.spring_api_restful.repository;

import entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto,Integer> {
}
