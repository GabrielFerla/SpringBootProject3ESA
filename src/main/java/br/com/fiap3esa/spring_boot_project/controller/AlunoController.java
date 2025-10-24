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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap3esa.spring_boot_project.domain.aluno.Aluno;
import br.com.fiap3esa.spring_boot_project.domain.aluno.AlunoRepository;
import br.com.fiap3esa.spring_boot_project.domain.aluno.DadosAtualizacaoAluno;
import br.com.fiap3esa.spring_boot_project.domain.aluno.DadosCadastroAluno;
import br.com.fiap3esa.spring_boot_project.domain.aluno.DadosDetalhamentoAluno;
import br.com.fiap3esa.spring_boot_project.domain.aluno.DadosListagemAluno;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/alunos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Alunos", description = "Gerenciamento de alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoAluno> cadastrarAluno(@RequestBody @Valid DadosCadastroAluno dados, UriComponentsBuilder uriBuilder) {
        Aluno aluno = new Aluno(dados);
        alunoRepository.save(aluno);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(aluno.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoAluno(aluno));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemAluno>> listarAlunos(@PageableDefault(size = 10 ,sort = {"nome"}) Pageable paginacao) {
        Page<DadosListagemAluno> page = alunoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemAluno::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoAluno> atualizarAluno(@RequestBody @Valid DadosAtualizacaoAluno dados) {
        Aluno aluno = alunoRepository.getReferenceById(dados.id());
        aluno.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoAluno(aluno));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluirAluno(@PathVariable Long id) {
        Aluno aluno = alunoRepository.getReferenceById(id);
        aluno.excluir();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoAluno> detalharAluno(@PathVariable Long id) {
        Aluno aluno = alunoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoAluno(aluno));
    }
}