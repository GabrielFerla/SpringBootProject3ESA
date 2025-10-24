package br.com.fiap3esa.spring_boot_project.controller;

import br.com.fiap3esa.spring_boot_project.domain.aluno.*;
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
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarAluno(@RequestBody @Valid DadosCadastroAluno dados, UriComponentsBuilder uriBuilder) {
        Aluno aluno = new Aluno(dados);
        alunoRepository.save(aluno);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoAluno(aluno));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemAluno>> listarAlunos(@PageableDefault(size = 10 ,sort = {"nome"}) Pageable paginacao) {
        Page page = alunoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemAluno::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarAluno(@RequestBody @Valid DadosAtualizacaoAluno dados) {
        Aluno aluno = alunoRepository.getReferenceById(dados.id());
        aluno.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoAluno(aluno));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirAluno(@PathVariable Long id) {
        Aluno aluno = alunoRepository.getReferenceById(id);
        aluno.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharAluno(@PathVariable Long id) {
        Aluno aluno = alunoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoAluno(aluno));
    }
}