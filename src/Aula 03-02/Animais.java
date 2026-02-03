class AnimalBase {
    public void emitirSom(){
        System.out.println("Emitido Som");
    }
}

class Cachorro extends AnimalBase{
    @Override
    public void emitirSom(){
        System.out.println("Au au");
    }
}

class Gato extends AnimalBase{
    @Override
    public void emitirSom(){
        System.out.println("Miau");
    }
}

class Passaro extends AnimalBase{
    @Override
    public void emitirSom(){
        System.out.println("Piu Piu");
    }
}

public class Animais {
    public static void main(String[] args) {
        Cachorro cachorro = new Cachorro();
        cachorro.emitirSom();
        Gato gato = new Gato();
        gato.emitirSom();
        Passaro passarro = new Passaro();
        passarro.emitirSom();
    }
}