package br.com.fiap3esa.spring_boot_project.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroUsuario(
        @NotBlank(message = "Login é obrigatório")
        String login,
        
        @NotBlank(message = "Senha é obrigatória")
        String senha,
        
        @NotNull(message = "Perfil é obrigatório")
        Perfil perfil
) {
}

