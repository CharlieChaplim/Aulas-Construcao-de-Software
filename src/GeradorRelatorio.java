public class GeradorRelatorio {

    public static abstract class Relatorio{
        public abstract void gerar();
    }

    public static class RelatorioPDF extends Relatorio{
        @Override
        public void gerar() {
            System.out.println("Gerando relatório em PDF");
        }
    }

    public static class RelatorioHTML extends Relatorio{
        @Override
        public void gerar() {
            System.out.println("Gerando relatório em HTML");
        }
    }
    public static void main(String[] args) {
        Relatorio pdf = new RelatorioPDF();
        Relatorio html = new RelatorioHTML();
        pdf.gerar();
        html.gerar();
    }
    }

