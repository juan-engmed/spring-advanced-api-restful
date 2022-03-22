package com.example.spring_api_restful.rest.controller;

import com.example.spring_api_restful.domain.entity.Cliente;
import com.example.spring_api_restful.domain.repository.ClientesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClientesRepository clientesRepository;

    @GetMapping("/hello/{nome}")
    @ResponseBody
    public String helloCliente(@PathVariable("nome") String nomeCliente){
        return String.format("Hello %s", nomeCliente);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") Integer id){

        Optional<Cliente> cliente = clientesRepository.findById(id);

        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity find(Cliente filtro){

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        List<Cliente> listaCliente = clientesRepository.findAll(example);
        return ResponseEntity.ok(listaCliente);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity salvarCliente(@RequestBody Cliente cliente){
        Cliente clienteSalvo = clientesRepository.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity updateCliente(@PathVariable("id") Integer id,
                                        @RequestBody Cliente cliente){

        return clientesRepository.findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientesRepository.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarCliente(@PathVariable("id") Integer id) {

        Optional<Cliente> cliente = clientesRepository.findById(id);

        if (cliente.isPresent()) {
            clientesRepository.deleteById(cliente.get().getId());

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
