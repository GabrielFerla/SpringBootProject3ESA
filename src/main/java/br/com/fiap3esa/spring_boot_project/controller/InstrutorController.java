package br.com.fiap3esa.spring_boot_project.controller;

import br.com.fiap3esa.spring_boot_project.domain.instrutor.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/instrutor")
public class InstrutorController {

    @Autowired
    InstrutorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarInstrutor(@RequestBody @Valid DadosCadastroInstrutor dados, UriComponentsBuilder uriBuilder) {
        //System.out.println(dados);
        Instrutor instrutor = new Instrutor(dados);
        repository.save(instrutor);
        URI uri = uriBuilder.path("/instrutor/{id}").buildAndExpand(instrutor.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoInstrutor(instrutor));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemInstrutor>> listarInstrutor(@PageableDefault(size = 5, sort = {"nome"}) Pageable paginacao) {
        Page page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemInstrutor::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarInstrutor(@RequestBody @Valid DadosAtualizacaoInstrutor dados) {
        Instrutor instrutor = repository.getReferenceById(dados.id());
        instrutor.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoInstrutor(instrutor));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity deletarInstrutor(@RequestParam Long id){
        Instrutor instrutor = repository.getReferenceById(id);
        instrutor.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharInstrutor(@PathVariable Long id) {
        Instrutor instrutor = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoInstrutor(instrutor));
    }
}