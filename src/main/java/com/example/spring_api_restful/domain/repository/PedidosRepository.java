package com.example.spring_api_restful.domain.repository;

import com.example.spring_api_restful.domain.entity.Cliente;
import com.example.spring_api_restful.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidosRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
