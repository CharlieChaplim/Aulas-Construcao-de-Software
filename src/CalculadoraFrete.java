public class CalculadoraFrete {

    public static abstract class Frete{
        public abstract double calcularFrete(double peso);
    }

    static class Sedex extends Frete{
        @Override
        public double calcularFrete(double peso) {
            return peso * 10;
        }
    }

    static class PAC extends Frete{
        @Override
        public double calcularFrete(double peso) {
            return peso * 5;
        }
    }


    public static void main(String[] args) {
        PAC pac = new PAC();
        Sedex sedex = new Sedex();
        System.out.println(pac.calcularFrete(10));
        System.out.println(sedex.calcularFrete(10));
    }
}