package com.example.spring_api_restful.rest.controller;

import com.example.spring_api_restful.domain.enums.StatusPedido;
import com.example.spring_api_restful.rest.controller.dto.AtualizacaoStatusDTO;
import com.example.spring_api_restful.rest.controller.dto.InformacaoItemPedidoDTO;
import com.example.spring_api_restful.rest.controller.dto.InformacaoPedidoDTO;
import com.example.spring_api_restful.rest.controller.dto.PedidoDTO;
import com.example.spring_api_restful.domain.entity.ItemPedido;
import com.example.spring_api_restful.domain.entity.Pedido;
import com.example.spring_api_restful.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){

        var pedido = pedidoService.saveProduct(dto);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacaoPedidoDTO getById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompleto(id)
                .map(pedido -> converterPedido(pedido))
                .orElseThrow( ()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    private InformacaoPedidoDTO converterPedido(Pedido pedido){
        return InformacaoPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .statusPedido
                        (pedido.getStatusPedido() == null
                                ? "Sem status"
                                : pedido.getStatusPedido().name())
                .items(converterItens(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converterItens(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream()
                .map(item -> InformacaoItemPedidoDTO.builder()
                    .descricaoProduto(item.getProduto().getDescricao())
                    .precoUnitario(item.getProduto().getPreco())
                    .quantidade(item.getQuantidade())
                    .build()
                ).collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable("id") Integer id,
                             @RequestBody AtualizacaoStatusDTO dto){

        String novoStatus = dto.getNovoStatus();
        pedidoService.updateStatusPedido(id, StatusPedido.valueOf(novoStatus));

    }
}
