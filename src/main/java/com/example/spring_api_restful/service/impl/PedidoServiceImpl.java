package com.example.spring_api_restful.service.impl;

import com.example.spring_api_restful.domain.repository.PedidosRepository;
import com.example.spring_api_restful.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidosRepository repository;
}
