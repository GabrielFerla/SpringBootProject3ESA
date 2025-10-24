package br.com.fiap3esa.spring_boot_project.domain.aluno;

import br.com.fiap3esa.spring_boot_project.domain.endereco.Endereco;

public record DadosDetalhamentoAluno(
        Long id,
        Boolean ativo,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {
    public DadosDetalhamentoAluno(Aluno aluno) {
        this(aluno.getId(), aluno.getAtivo(), aluno.getNome(), aluno.getEmail(), aluno.getTelefone(), aluno.getCpf(), aluno.getEndereco());
    }
}