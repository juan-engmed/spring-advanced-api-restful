package com.example.spring_api_restful.repository;

import entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
