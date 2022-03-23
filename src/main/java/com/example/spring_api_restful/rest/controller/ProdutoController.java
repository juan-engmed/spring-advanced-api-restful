package com.example.spring_api_restful.rest.controller;

import com.example.spring_api_restful.domain.entity.Produto;
import com.example.spring_api_restful.domain.repository.ProdutosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    private final ProdutosRepository produtosRepository;

    @GetMapping("/{id}")
    public Produto getProductById(@PathVariable("id") Integer id){
        return produtosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado."
                ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto addNewProduct(@RequestBody Produto produto){
        return produtosRepository.save(produto);
    }

    @PutMapping("/{id}")
    public Produto updateProduct(@PathVariable("id") Integer id,
                                 @RequestBody Produto produto){
        return produtosRepository.findById(id)
                .map( produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtosRepository.save(produto);
                    return produtoExistente;
                }).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado."
                ));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Integer id){
        produtosRepository.findById(id)
                .map(produto -> {
                    produtosRepository.delete(produto);
                    return produto;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @GetMapping("/all")
    public List<Produto> findAllProducts(){
        return produtosRepository.findAll();
    }

    @GetMapping
    public List<Produto> findProductsWithFilter(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        return produtosRepository.findAll(example);
    }
}
