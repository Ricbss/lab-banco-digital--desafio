import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Cliente {
    private String nome;

    public Cliente(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

abstract class Conta {
    private static int SEQUENCIAL = 1;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;
    private static final DecimalFormat df = new DecimalFormat("#.00");

    public Conta(Cliente cliente) {
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public void sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
        } else {
            System.out.println("Saldo insuficiente!");
        }
    }

    public void transferir(double valor, Conta destino) {
        if (saldo >= valor) {
            this.sacar(valor);
            destino.depositar(valor);
        } else {
            System.out.println("Saldo insuficiente para transferência!");
        }
    }

    public void imprimirExtrato() {
        System.out.println("Conta: " + numero);
        System.out.println("Titular: " + cliente.getNome());
        System.out.println("Saldo: R$ " + df.format(saldo));
    }
}

class ContaCorrente extends Conta {
    private static final double TAXA_SAQUE = 2.50;
    
    public ContaCorrente(Cliente cliente) {
        super(cliente);
    }
    
    @Override
    public void sacar(double valor) {
        double valorComTaxa = valor + TAXA_SAQUE;
        if (saldo >= valorComTaxa) {
            saldo -= valorComTaxa;
        } else {
            System.out.println("Saldo insuficiente para saque!");
        }
    }
}

class ContaPoupanca extends Conta {
    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }
}

class Banco {
    private String nome;
    private List<Conta> contas;

    public Banco(String nome) {
        this.nome = nome;
        this.contas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public void listarContas() {
        System.out.println("Contas do Banco " + nome + ":");
        for (Conta conta : contas) {
            conta.imprimirExtrato();
            System.out.println("-----------------------");
        }
    }
}
