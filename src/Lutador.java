
public class Lutador {
    String nome;
    String pais;
    String estilo;
    String especial;
    int vida;
    int danoSoco;
    int danoChute;
    int danoEspecial;
    boolean defendendo; // true quando o lutador esta de guarda levantada

    // método construtor
    public Lutador(String nome, String pais, String estilo, String especial, int danoSoco, int danoChute, int danoEspecial) {
        this.nome = nome;
        this.pais = pais;
        this.estilo = estilo;
        this.especial = especial;
        this.danoSoco = danoSoco;
        this.danoChute = danoChute;
        this.danoEspecial = danoEspecial;
        this.vida = 100;
        this.defendendo = false;
    }

    // métodos
    void socar(Lutador inimigo) {
        // se o inimigo estiver defendendo, o dano é reduzido pela metade
        if (inimigo.defendendo) {
            inimigo.vida = inimigo.vida - danoSoco/2;
            System.out.println(" " + nome + " deu um SOCO e tirou " + danoSoco/2 + " de vida de " + inimigo.nome);
        } else {
            inimigo.vida = inimigo.vida - danoSoco;
            System.out.println(" " + nome + " deu um SOCO e tirou " + danoSoco + " de vida de " + inimigo.nome);
        }
        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }
    }

    void chutar(Lutador inimigo) {
        if (inimigo.defendendo) {
            inimigo.vida = inimigo.vida - danoChute / 2;
            System.out.println(" " + nome + " deu um CHUTE e tirou " + danoChute/2 + " de vida de " + inimigo.nome);
        } else {
            inimigo.vida = inimigo.vida - danoChute;
            System.out.println(" " + nome + " deu um CHUTE e tirou " + danoChute + " de vida de " + inimigo.nome);
        }
        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }
    }

    void golpeEspecial(Lutador inimigo) {
        if (inimigo.defendendo) {
            inimigo.vida = inimigo.vida - danoEspecial/2;
            System.out.println(" " + nome + " deu um GOLPE ESPECIAL e tirou " + danoEspecial/2 + " de vida de " + inimigo.nome);
        } else {
            inimigo.vida = inimigo.vida - danoEspecial;
            System.out.println(" " + nome + " deu um GOLPE ESPECIAL e tirou " + danoEspecial + " de vida de " + inimigo.nome);
        }
        if (inimigo.vida < 0) {
            inimigo.vida = 0;
        }
    }

    void defender() {
        defendendo = true;
        System.out.println(" " + nome + " esta defendendo");
    }

    void info() {
        System.out.println("Nome: " + nome);
        System.out.println("Pais: " + pais);
        System.out.println("Estilo: " + estilo);
        System.out.println("Especial: " + especial);
        System.out.println("Vida: " + vida);
        System.out.println("Dano do Soco: " + danoSoco);
        System.out.println("Dano do Chute: " + danoChute);
        System.out.println("Dano do Especial: " + danoEspecial);
    }
}