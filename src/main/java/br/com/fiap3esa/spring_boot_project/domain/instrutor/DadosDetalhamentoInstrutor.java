package br.com.fiap3esa.spring_boot_project.domain.instrutor;

import br.com.fiap3esa.spring_boot_project.domain.endereco.Endereco;

public record DadosDetalhamentoInstrutor(
        Long id,
        Boolean ativo,
        String nome,
        String email,
        String telefone,
        String cnh,
        Especialidade especialidade,
        Endereco endereco) {
    public DadosDetalhamentoInstrutor(Instrutor instrutor) {
        this(instrutor.getId(), instrutor.getAtivo(), instrutor.getNome(), instrutor.getEmail(), instrutor.getTelefone(), instrutor.getCnh(), instrutor.getEspecialidade(), instrutor.getEndereco());
    }
}