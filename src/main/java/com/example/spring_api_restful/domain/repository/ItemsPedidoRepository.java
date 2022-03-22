package com.example.spring_api_restful.domain.repository;

import com.example.spring_api_restful.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
