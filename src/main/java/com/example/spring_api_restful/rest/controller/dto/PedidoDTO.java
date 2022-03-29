package com.example.spring_api_restful.rest.controller.dto;

import com.example.spring_api_restful.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    @NotNull(message = "Informe o código do Cliente.")
    private Integer cliente;

    @NotNull(message = "Campo total é obrigatório")
    private BigDecimal total;

    @NotEmptyList(message = "Pedido não ser pode ser realizado sem incluir itens")
    private List<ItemPedidoDTO> items;
}
