package br.com.fiap3esa.spring_boot_project.domain.instrutor;

import br.com.fiap3esa.spring_boot_project.domain.endereco.Endereco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "instrutores")
@Entity(name = "Instrutor")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Instrutor {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private boolean ativo = true;
    private String nome;
    private String email;
    private String telefone;
    private String cnh;

    @Enumerated(EnumType.STRING)
    Especialidade especialidade;

    @Embedded
    Endereco endereco;

    public Instrutor(DadosCadastroInstrutor dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cnh = dados.cnh();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoInstrutor dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if(dados.endereco() != null) {
            this.endereco.atualizarInformações(dados.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }

    //Lombok não funcionou para esse GETTER
    public boolean getAtivo() {
        return ativo;
    }
}