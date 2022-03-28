package com.example.spring_api_restful.service;

import com.example.spring_api_restful.rest.controller.dto.PedidoDTO;
import com.example.spring_api_restful.domain.entity.Pedido;
import com.example.spring_api_restful.domain.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {

    Pedido saveProduct(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void updateStatusPedido(Integer id, StatusPedido statusPedido);

}
