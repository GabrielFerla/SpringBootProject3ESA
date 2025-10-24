package br.com.fiap3esa.spring_boot_project.domain.usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String login,
        Perfil perfil
) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getLogin(), usuario.getPerfil());
    }
}

