package br.com.fiap3esa.spring_boot_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap3esa.spring_boot_project.domain.usuario.DadosAlteracaoSenha;
import br.com.fiap3esa.spring_boot_project.domain.usuario.DadosAtualizacaoUsuario;
import br.com.fiap3esa.spring_boot_project.domain.usuario.DadosCadastroUsuario;
import br.com.fiap3esa.spring_boot_project.domain.usuario.DadosDetalhamentoUsuario;
import br.com.fiap3esa.spring_boot_project.domain.usuario.DadosListagemUsuario;
import br.com.fiap3esa.spring_boot_project.domain.usuario.Usuario;
import br.com.fiap3esa.spring_boot_project.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(
            @RequestBody @Valid DadosCadastroUsuario dados,
            UriComponentsBuilder uriBuilder) {
        
        // Verificar se o login já existe
        if (repository.existsByLogin(dados.login())) {
            throw new IllegalArgumentException("Login já cadastrado no sistema");
        }

        // Encriptar a senha antes de salvar
        String senhaEncriptada = passwordEncoder.encode(dados.senha());
        
        // Criar o usuário
        Usuario usuario = new Usuario(dados.login(), senhaEncriptada, dados.perfil());
        repository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listar(
            @PageableDefault(size = 10, sort = {"login"}) Pageable paginacao) {
        
        var page = repository.findAll(paginacao).map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(@PathVariable Long id) {
        Usuario usuario = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        
        Usuario usuario = repository.getReferenceById(id);
        usuario.atualizarPerfil(dados.perfil());
        
        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/alterar-senha")
    @Transactional
    public ResponseEntity<Void> alterarSenha(
            @RequestBody @Valid DadosAlteracaoSenha dados,
            Authentication authentication) {
        
        // Buscar usuário autenticado
        String login = authentication.getName();
        Usuario usuario = repository.getReferenceByLogin(login);

        // Verificar se a senha atual está correta
        if (!passwordEncoder.matches(dados.senhaAtual(), usuario.getSenha())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        // Encriptar e atualizar a nova senha
        String novaSenhaEncriptada = passwordEncoder.encode(dados.novaSenha());
        usuario.atualizarSenha(novaSenhaEncriptada);

        return ResponseEntity.ok().build();
    }
}

