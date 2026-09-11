# Jogo de Luta em Java — projeto de POO

Jogo de luta estilo Street Fighter que roda no terminal. Melhor de 3 rounds,
com soco, chute, defesa, provocação e golpe especial. As barras de vida descem
aos poucos na tela, feito animação.

## Como rodar

Jeito mais fácil: dar dois cliques em **`jogar.bat`**.
Ele compila tudo e já abre o jogo.

Pelo terminal, dentro da pasta do projeto:

```bash
javac -d bin src/*.java
java -cp bin App
```

O jogo usa só caracteres comuns do teclado (`#`, `=`, `|`), então a tela fica
igual em qualquer terminal: `cmd`, PowerShell, VS Code ou terminal do Linux.

## As classes do projeto

Cada classe é uma "coisa" do jogo, do mesmo jeito que `Pokemon` e `Pessoa`
eram nos exercícios da aula.

| Classe | O que ela é |
|---|---|
| `Lutador` | **O personagem.** Tem os atributos (nome, vida, dano dos golpes...), o construtor e os métodos (`soco`, `chute`, `defender`, `provocar`, `golpeEspecial`, `ficha`). |
| `Arena` | **A luta.** Guarda os dois lutadores, controla os rounds e os turnos, desenha o placar e decide quem venceu. |
| `Jogo` | **O jogo.** Guarda a lista de lutadores, mostra o menu e deixa o jogador escolher o personagem. |
| `Cores` | Só guarda os códigos de cor do terminal, para o resto do código escrever `cor.vermelho` em vez de um monte de símbolo. |
| `App` | Classe principal com o `main`. Só cria o objeto `Jogo` e manda ele abrir o menu. |

O `App` ficou igual ao das aulas: cria um objeto e chama um método dele.

```java
public class App {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.abrirMenu();
    }
}
```

## Regras da luta

Cada lutador começa com **100 de vida** e **0 de fúria**. Vence a luta quem
ganhar **2 rounds**. Quem tem mais **velocidade** começa atacando.

No seu turno você escolhe uma ação:

| Ação | Efeito |
|---|---|
| **Soco** | Dano menor, mas a guarda continua fechada. Enche 12 de fúria. |
| **Chute** | Dano maior, só que para girar o corpo o lutador **abre a guarda**. Enche 18 de fúria. |
| **Defender** | O próximo golpe recebido causa metade do dano. Enche 10 de fúria. |
| **Provocar** | Enche 35 de fúria de uma vez, mas abre a guarda. |
| **Especial** | Só libera com 100 de fúria. Dano altíssimo e **ignora a defesa** do adversário. |

Outros detalhes:

- **Nada é sorteado.** O dano de cada golpe já vem definido no construtor do
  lutador, então o mesmo golpe sempre tira o mesmo tanto de vida.
- Quem está com a **guarda aberta** leva 50% a mais no golpe seguinte.
- A **defesa** é descontada do dano dos golpes normais.
- Levar dano também enche a fúria, então quem está apanhando fica perigoso.
- A guarda levantada e a guarda aberta valem só até o lutador agir de novo.

## Os lutadores

| # | Nome | País | Soco | Chute | Especial | Defesa | Velocidade | Golpe |
|---|---|---|---|---|---|---|---|---|
| 1 | Ryu | Japão | 14 | 22 | 45 | 5 | 6 | HADOUKEN |
| 2 | Ken | EUA | 16 | 24 | 48 | 4 | 7 | SHORYUKEN |
| 3 | Chun-Li | China | 13 | 20 | 42 | 4 | 9 | SPINNING BIRD KICK |
| 4 | Blanka | Brasil | 17 | 26 | 50 | 3 | 6 | ELECTRIC THUNDER |
| 5 | Zangief | Rússia | 19 | 30 | 55 | 7 | 2 | SPINNING PILEDRIVER |
| 6 | Dhalsim | Índia | 14 | 23 | 46 | 3 | 8 | YOGA FLAME |

## Como funciona a animação da barra de vida

Não tem biblioteca nenhuma, é tudo `System.out.println` com pausa:

1. O método `desenharPlacar` da `Arena` escreve **sempre 7 linhas**, nem uma a
   mais. Isso é o segredo: dá para redesenhar por cima sem bagunçar o texto
   que está embaixo.
2. O `animarDano` desenha o placar 7 vezes seguidas, cada uma com um valor
   entre a vida antiga e a vida nova, com uma pausa de 60 milésimos entre elas.
   Um monte de quadros parados em sequência vira animação.

Os únicos comandos "de terminal" usados são três: um para mudar a cor (na
classe `Cores`), um para mandar o cursor lá para cima (`voltarAoTopo`) e um
para apagar o resto da tela (`apagarORestante`).

## Conceitos de POO usados (para explicar na apresentação)

- **Classe e objeto**: `Lutador` é a classe (a "forma"); `new Lutador("Ryu", ...)`
  cria um objeto, um lutador de verdade com os dados dele.
- **Atributos**: as características de cada objeto (`nome`, `vida`, `danoDoSoco`...).
- **Construtor**: `public Lutador(...)` define como um lutador nasce. Todo
  lutador já começa com 100 de vida e 0 de fúria, isso está escrito no construtor.
  É no construtor também que entra o dano dos golpes de cada personagem.
- **Métodos**: as ações do objeto (`soco`, `chute`, `defender`, `ficha`).
- **Um objeto agindo sobre outro**: `ryu.soco(ken)` — o método recebe outro
  `Lutador` como parâmetro e mexe na vida dele, igualzinho ao exercício do
  `atacarOutroPersonagem`.
- **Objetos que trabalham juntos**: a `Arena` guarda dois `Lutador` dentro
  dela; o `Jogo` guarda um array de `Lutador` e cria a `Arena` na hora da luta.
- **Array de objetos**: `Lutador[] lutadores` guarda os 6 personagens juntos,
  e o `for` passa por todos para mostrar a lista.
- **`this`**: usado dentro da classe para deixar claro que estamos falando do
  atributo daquele objeto, e não de uma variável qualquer.
- **Cópia de objeto**: o método `criarCopia()` existe porque, sem ele, dois
  jogadores escolhendo o mesmo personagem estariam mexendo no MESMO objeto
  e dividindo a mesma barra de vida.

## Ideias para aumentar o projeto depois

- Mais lutadores (é só acrescentar uma linha no construtor do `Jogo`).
- Um segundo golpe especial por personagem.
- Um cenário que mude alguma regra da luta.
- Salvar o placar das lutas em um arquivo.
