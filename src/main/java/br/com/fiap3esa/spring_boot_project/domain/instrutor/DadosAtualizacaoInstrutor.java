package br.com.fiap3esa.spring_boot_project.domain.instrutor;

import br.com.fiap3esa.spring_boot_project.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoInstrutor(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}