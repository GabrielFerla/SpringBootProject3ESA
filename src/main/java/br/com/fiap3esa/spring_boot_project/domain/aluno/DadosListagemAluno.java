package br.com.fiap3esa.spring_boot_project.domain.aluno;

public record DadosListagemAluno(
        Long id,
        String nome,
        String email,
        String cpf) {
    public DadosListagemAluno(Aluno aluno) {
        this(aluno.getId(),  aluno.getNome(), aluno.getEmail(), aluno.getCpf());
    }
}