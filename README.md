# Jogo de Luta em Java — projeto de POO

Jogo de luta estilo Street Fighter que roda no terminal. Dois lutadores
desenhados um de frente para o outro, melhor de 3 rounds, golpes, defesa,
provocação, golpe especial animado e barras de vida que descem aos poucos.

## Como rodar

Jeito mais fácil: dar dois cliques em **`jogar.bat`**.
Ele compila tudo e já abre o jogo.

Pelo terminal, dentro da pasta do projeto:

```bash
javac -d bin src/*.java
java -cp bin App
```

O jogo usa só caracteres comuns do teclado (`#`, `=`, `|`, `/`, `\`), então
o desenho fica igual em qualquer terminal: `cmd`, PowerShell, VS Code ou
terminal do Linux.

## As classes do projeto

Cada classe é uma "coisa" do jogo, do mesmo jeito que `Pokemon` e `Pessoa`
eram nos exercícios da aula.

| Classe | O que ela é |
|---|---|
| `Lutador` | **O personagem.** Tem os atributos (nome, vida, força...), o construtor e os métodos (`soco`, `chute`, `defender`, `provocar`, `golpeEspecial`, `ficha`). Também sabe se desenhar e mostrar a própria barra de vida. |
| `Arena` | **A luta.** Guarda os dois lutadores, controla os rounds e os turnos, desenha a tela da luta e decide quem venceu. |
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
ganhar **2 rounds**.

No seu turno você escolhe uma ação:

| Ação | Efeito |
|---|---|
| **Soco** | Dano baixo, nunca erra. Enche 12 de fúria. |
| **Chute** | Dano alto, mas tem 25% de chance de errar. Enche 18 de fúria. |
| **Defender** | O próximo golpe recebido causa metade do dano. Enche 10 de fúria. |
| **Provocar** | Enche 35 de fúria de uma vez, mas se levar um golpe no mesmo turno o dano é 50% maior. |
| **Especial** | Só libera com 100 de fúria. Dano altíssimo e **ignora a defesa** do adversário. |

Detalhes que dão graça ao jogo:

- **Velocidade** dá chance de desviar do golpe (Chun-Li desvia muito, Zangief quase nunca).
- **Defesa** é descontada do dano de cada golpe normal.
- Levar dano também enche a fúria, então quem está apanhando fica perigoso.
- A guarda e a provocação só valem até o lutador agir de novo.

## Como funciona a animação

Não tem biblioteca nenhuma, é tudo `System.out.println` com pausa:

1. Cada lutador tem um atributo `pose` (`"parado"`, `"soco"`, `"chute"`,
   `"defesa"`, `"dano"`, `"ko"`, `"vitoria"`...). Quando ele usa um golpe, o
   método troca a pose dele; quando ele apanha, o `sofrerDano` troca para
   `"dano"` (ou `"ko"` se a vida zerar).
2. O método `desenho()` do `Lutador` devolve as 4 linhas do bonequinho.
   Os desenhos estão escritos duas vezes, um virado para cada lado, porque
   um lutador olha para o outro. São só textos: dá para editar e ver mudar.
3. O método `desenharCena` da `Arena` escreve **sempre 14 linhas**, nem uma a
   mais. Isso é o segredo: dá para redesenhar por cima sem bagunçar o texto
   que está embaixo.
4. A barra de vida desce de pouquinho em pouquinho porque o `animarDano`
   desenha a cena 7 vezes, cada uma com um valor entre a vida antiga e a nova.
5. O golpe especial `(*)` atravessa a tela porque o `animarEspecial` desenha
   a mesma cena várias vezes mudando a coluna onde o `(*)` aparece.

Os únicos comandos "de terminal" usados são três: um para mudar a cor (na
classe `Cores`), um para mandar o cursor lá para cima (`voltarAoTopo`) e um
para apagar o resto da tela (`apagarORestante`).

## Os lutadores

| # | Nome | País | Força | Defesa | Velocidade | Especial |
|---|---|---|---|---|---|---|
| 1 | Ryu | Japão | 11 | 5 | 6 | HADOUKEN |
| 2 | Ken | EUA | 13 | 4 | 7 | SHORYUKEN |
| 3 | Chun-Li | China | 9 | 4 | 9 | SPINNING BIRD KICK |
| 4 | Blanka | Brasil | 14 | 3 | 6 | ELECTRIC THUNDER |
| 5 | Zangief | Rússia | 16 | 7 | 2 | SPINNING PILEDRIVER |
| 6 | Dhalsim | Índia | 10 | 3 | 8 | YOGA FLAME |

## Conceitos de POO usados (para explicar na apresentação)

- **Classe e objeto**: `Lutador` é a classe (a "forma"); `new Lutador("Ryu", ...)`
  cria um objeto, um lutador de verdade com os dados dele.
- **Atributos**: as características de cada objeto (`nome`, `vida`, `forca`...).
- **Construtor**: `public Lutador(...)` define como um lutador nasce. Todo
  lutador já começa com 100 de vida e 0 de fúria, isso está escrito no construtor.
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
- Mais poses no `Lutador` (por exemplo, uma pose de pulo ou de agachar).
- Um segundo golpe especial por personagem.
- Salvar o placar das lutas em um arquivo.
