package com.example.spring_api_restful.service.impl;

import com.example.spring_api_restful.domain.dto.ItemPedidoDTO;
import com.example.spring_api_restful.domain.dto.PedidoDTO;
import com.example.spring_api_restful.domain.entity.ItemPedido;
import com.example.spring_api_restful.domain.entity.Pedido;
import com.example.spring_api_restful.domain.repository.ClientesRepository;
import com.example.spring_api_restful.domain.repository.ItemsPedidoRepository;
import com.example.spring_api_restful.domain.repository.PedidosRepository;
import com.example.spring_api_restful.domain.repository.ProdutosRepository;
import com.example.spring_api_restful.exception.RegraNegocioException;
import com.example.spring_api_restful.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidosRepository repository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItemsPedidoRepository itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido saveProduct(PedidoDTO dto) {

        var idCliente = dto.getCliente();
        var cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de Cliente inválido."));

        var pedido = new Pedido();

        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        var itemsPedido = convertItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> convertItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar o pedido");
        }

        return items.stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    var produto = produtosRepository.findById(idProduto).
                    orElseThrow(() -> new RegraNegocioException("Código de Produto inválido." + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
