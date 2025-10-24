package br.com.fiap3esa.spring_boot_project.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull(message = "ID é obrigatório")
        Long id,
        
        Perfil perfil
) {
}

