package br.com.fiap3esa.spring_boot_project.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap3esa.spring_boot_project.domain.instrutor.DadosAtualizacaoInstrutor;
import br.com.fiap3esa.spring_boot_project.domain.instrutor.DadosCadastroInstrutor;
import br.com.fiap3esa.spring_boot_project.domain.instrutor.DadosDetalhamentoInstrutor;
import br.com.fiap3esa.spring_boot_project.domain.instrutor.DadosListagemInstrutor;
import br.com.fiap3esa.spring_boot_project.domain.instrutor.Instrutor;
import br.com.fiap3esa.spring_boot_project.domain.instrutor.InstrutorRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/instrutor")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Instrutores", description = "Gerenciamento de instrutores")
public class InstrutorController {

    @Autowired
    InstrutorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoInstrutor> cadastrarInstrutor(@RequestBody @Valid DadosCadastroInstrutor dados, UriComponentsBuilder uriBuilder) {
        //System.out.println(dados);
        Instrutor instrutor = new Instrutor(dados);
        repository.save(instrutor);
        URI uri = uriBuilder.path("/instrutor/{id}").buildAndExpand(instrutor.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoInstrutor(instrutor));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemInstrutor>> listarInstrutor(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemInstrutor> page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemInstrutor::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoInstrutor> atualizarInstrutor(@RequestBody @Valid DadosAtualizacaoInstrutor dados) {
        Instrutor instrutor = repository.getReferenceById(dados.id());
        instrutor.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoInstrutor(instrutor));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deletarInstrutor(@RequestParam Long id){
        Instrutor instrutor = repository.getReferenceById(id);
        instrutor.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoInstrutor> detalharInstrutor(@PathVariable Long id) {
        Instrutor instrutor = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoInstrutor(instrutor));
    }
}