import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class ConexaoBd {
    private static Connection conexao=null;
    private String fonte = "restaurante";
    private ConexaoBd() {
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + fonte,"root","1234");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("Ocorreu um erro ao tentar acessar o banco de dados");
        }
    }
public static Connection getInstance(){
        if (conexao==null){
            new ConexaoBd();
        }
        return conexao;
}
}
