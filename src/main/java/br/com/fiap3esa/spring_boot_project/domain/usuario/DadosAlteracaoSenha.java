package br.com.fiap3esa.spring_boot_project.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAlteracaoSenha(
        @NotBlank(message = "Senha atual é obrigatória")
        String senhaAtual,
        
        @NotBlank(message = "Nova senha é obrigatória")
        String novaSenha
) {
}

