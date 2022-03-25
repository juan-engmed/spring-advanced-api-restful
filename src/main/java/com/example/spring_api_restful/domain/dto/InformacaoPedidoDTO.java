package com.example.spring_api_restful.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoPedidoDTO {

    private Integer codigo;
    private String nomeCliente;
    private String cpf;
    private BigDecimal total;
    private String dataPedido;
    private List<InformacaoItemPedidoDTO> items;
}
