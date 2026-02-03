public class SistemaBancario {
    private double saldo;
    public SistemaBancario(double saldo) {this.saldo = saldo;}
    public void depositar(double valor){
        if(saldo > 0){
            saldo = saldo + valor;
        } else {
            System.out.println("Saldo insuficiente");
        }
    }
    public void sacar(double valor){
        if(saldo > 0){
            saldo = saldo - valor;
        }else {
            System.out.println("Saldo insuficiente");
        }
    }
    public double getSaldo(){return saldo;}
    public void setSaldo(double saldo){this.saldo = saldo;}

    static void main(String args[]){
        SistemaBancario Pan = new SistemaBancario(1000);
        Pan.depositar(5000);
        Pan.sacar(2000);
        System.out.println("Saldo da conta Pan: " + Pan.getSaldo());
    }
}
