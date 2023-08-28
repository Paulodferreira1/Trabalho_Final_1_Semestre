import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Connection conexao = null;
    static Scanner ler = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        int opcao = 0;
        String nome,cpf,dataNascimento,email,sexo;
        long telefone;
        double salarioFixo;
        while(opcao != 6){
            System.out.println("Digite o numero da função que deseja executar");
            System.out.println("1-Adicionar garçom ");
            System.out.println("2-Remover garçom ");
            System.out.println("3-Buscar garçom cadastrado");
            System.out.println("4-Atulizar dados do garçom");
            System.out.println("5-Calcular quantos garçom está cadastrado no sistema ");
            System.out.println("6-Sair");
            opcao = ler.nextInt();
            switch (opcao){
                case 1 :
                    System.out.println("Digite os dados do garçom");
                    System.out.println("Digite o nome");
                    nome = ler.next();
                    System.out.println("Digite o cpf");
                    cpf = ler.next();
                    System.out.println("Digite a data de nascimento");
                    dataNascimento = ler.next();
                    System.out.println("Digite o email");
                    email= ler.next();
                    System.out.println("Digite o Sexo");
                    sexo = ler.next();
                    System.out.println("Digite o telefone");
                    telefone= ler.nextLong();
                    System.out.println("Digite o salario fixo");
                    salarioFixo = ler.nextDouble();
                    Garcom g = new Garcom(nome,cpf,dataNascimento,email,sexo,telefone,salarioFixo);
                    inserirGarcom(g);
                   break;
                case 2 :
                    System.out.println(" Dgite o cpf do garçom que deseja  remover");
                    String removerPessoa = ler.next();
                    removerPessoaPelocpf(removerPessoa);
                   break;
                case 3 :
                    System.out.println("Digite o cpf do garçom que deseja procurar");
                   String buscarGarcom = ler.next();
                    Garcom gEncontrado = buscarGarcomPeloCpf(buscarGarcom);
                    if(gEncontrado == null) {
                        System.out.println("Garçom não encontrado");

                    }
                    else {
                        System.out.println("Nome:"+ gEncontrado.getNome());
                        System.out.println("cpf:" +gEncontrado.getCpf());
                        System.out.println("Data de Nascimento:"+ gEncontrado.getDataNascimento());
                        System.out.println("Email:"+ gEncontrado.getEmail());
                        System.out.println("Sexo:"+ gEncontrado.getSexo());
                        System.out.println("Telefone:"+ gEncontrado.getTelefone());
                        System.out.println("Salario Fixo"+ gEncontrado.getSalarioFixo());
                        break;
            }
                case 4 :
                    System.out.println("Digite os dados atuais do garçom");
                    System.out.println("Nome");
                    nome = ler.next();
                    System.out.println("Cpf:");
                    cpf = ler.next();
                    System.out.println("Data de nascimento:");
                    dataNascimento = ler.next();
                    System.out.println("Email:");
                    email= ler.next();
                    System.out.println("Sexo:");
                    sexo = ler.next();
                    System.out.println("Telefone:");
                    telefone= ler.nextLong();
                    System.out.println("Salario fixo:");
                    salarioFixo = ler.nextDouble();
                    Garcom gDadosAtualizados = new Garcom(nome,cpf,dataNascimento,email,sexo,telefone,salarioFixo);
                    alterarGarcom(gDadosAtualizados);
                    break;
                case 5 :
                    int totalGarcons = contarGarcons();
                    System.out.println("Total de garçons cadastrados: " + totalGarcons);
                    break;

                case 6 :
                    System.out.println("Você saiu");
        }

        }

    }

    private static int contarGarcons() throws SQLException {
        conexao = ConexaoBd.getInstance();
        String sql = "SELECT COUNT(*) AS total FROM garcom";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet resultado = stmt.executeQuery();
        int totalGarcons = 0;
        if (resultado.next()) {
            totalGarcons = resultado.getInt("total");
        }
        resultado.close();
        stmt.close();
        return totalGarcons;
    }

    public static void inserirGarcom(Garcom glido) {
        try {

            conexao = ConexaoBd.getInstance();
            String sql = "Insert into garcom (nome,cpf,dataNascimento,email,sexo,telefone,salarioFixo) values ( ?,?,?,?,?,?,?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, glido.getNome());
            stmt.setString(2,glido.getCpf());
            stmt.setString(3,glido.getDataNascimento());
            stmt.setString(4,glido.getEmail());
            stmt.setString(5,glido.getSexo());
            stmt.setLong(6,glido.getTelefone());
            stmt.setDouble(7,glido.getSalarioFixo());
            stmt.execute();
            stmt.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ocorreu um erro ao adiconar a pessoa no banco de dados");
        }
    }

    public static void removerPessoaPelocpf(String cpfDoGarcomRemovido) throws SQLException {
        conexao =  ConexaoBd.getInstance();
        String sql = "delete from garcom where cpf LIKE? ";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1,cpfDoGarcomRemovido);
        stmt.execute();
        stmt.close();

    }

    public static Garcom buscarGarcomPeloCpf(String cpfBuscado) throws SQLException {
        conexao = ConexaoBd.getInstance();
        String sql = "Select * from garcom where cpf = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1,cpfBuscado);
        ResultSet resultado = stmt.executeQuery();
        Garcom g = null;
        if(resultado.next()) {
            g = new Garcom(resultado.getString("nome"), resultado.getString("cpf"),resultado.getString("dataNascimento"),resultado.getString("email"),resultado.getString("sexo"), resultado.getLong("telefone"), resultado.getDouble("salarioFixo"));
        }
        resultado.close();
        stmt.close();
        return g;
    }

    public static void alterarGarcom(Garcom gSendoAlterado) throws SQLException {
        conexao = ConexaoBd.getInstance();
        String sql = "Update garcom Set nome = ? , cpf = ? , dataNascimento = ? , email = ? , sexo = ? , telefone = ? , SalarioFixo = ? " + "Where cpf = ?";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, gSendoAlterado.getNome());
        stmt.setString(2,gSendoAlterado.getCpf());
        stmt.setString(3,gSendoAlterado.getDataNascimento());
        stmt.setString(4,gSendoAlterado.getEmail());
        stmt.setString(5,gSendoAlterado.getSexo());
        stmt.setLong(6,gSendoAlterado.getTelefone());
        stmt.setDouble(7,gSendoAlterado.getSalarioFixo());
        stmt.setString(8,gSendoAlterado.getCpf());
        stmt.execute();
        stmt.close();
    }
}