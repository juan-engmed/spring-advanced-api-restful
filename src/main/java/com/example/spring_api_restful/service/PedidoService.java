package com.example.spring_api_restful.service;

import com.example.spring_api_restful.domain.dto.PedidoDTO;
import com.example.spring_api_restful.domain.entity.Pedido;

public interface PedidoService {

    Pedido saveProduct(PedidoDTO dto);

}
