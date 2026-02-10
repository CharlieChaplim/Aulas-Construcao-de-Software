package entity;

import java.util.Objects;

public class Funcionario {
    private Integer id;
    private String nome;
    private Double salario;
    private String cpf;
    private String cargo;
    private Integer matricula;
    private String telefone;

    public Funcionario() {
    }

    public Funcionario(String nome, Double salario, String cpf, String cargo, Integer matricula, String telefone) {
        this.nome = nome;
        this.salario = salario;
        this.cpf = cpf;
        this.cargo = cargo;
        this.matricula = matricula;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", salario=" + salario +
                ", cpf='" + cpf + '\'' +
                ", cargo='" + cargo + '\'' +
                ", matricula=" + matricula +
                ", telefone='" + telefone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(nome, that.nome) && Objects.equals(salario, that.salario) && Objects.equals(cpf, that.cpf) && Objects.equals(cargo, that.cargo) && Objects.equals(matricula, that.matricula) && Objects.equals(telefone, that.telefone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, salario, cpf, cargo, matricula, telefone);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}