package aplicacao;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFabrica;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class programa {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		
		VendedorDao vD = DaoFabrica.criarVendedorDao();
		
		System.out.println("=== Teste 1: Vendedor acharPorId ===");
		Vendedor vendedor = vD.acharPorId(3);
		System.out.println(vendedor);
		
		System.out.println("\n=== Teste 2: Vendedor acharPorDepartamento ===");
		Departamento departamento = new Departamento(2, null);
		List<Vendedor> lista = vD.acharPorDepartamento(departamento);
		lista.forEach(System.out::println);
		
		System.out.println("\n=== Teste 3: Vendedor acharTodos ===");
		lista = vD.acharTodos();
		lista.forEach(System.out::println);
		
		System.out.println("\n=== Teste 4: Vendedor inserir ===");
		Vendedor v2 = new Vendedor(null, "Greg", "greg@gmail.com", new Date(), 4000.0, departamento);
		vD.inserir(v2);
		System.out.println("Iserido! Novo Id: " + v2.getId());
		
		System.out.println("\n=== Teste 5: Vendedor atualizar ===");
		vendedor = vD.acharPorId(1);
		vendedor.setNome("Marta Waine");
		vD.atualizar(vendedor);
		System.out.println("Atualização completada!");
		
		System.out.println("\n=== Teste 6: Vendedor deletar ===");
		System.out.println("Digite um Id: ");
		int id = sc.nextInt();
		vD.deletePorId(id);
		System.out.println("Excluido com sucesso!");
		
		sc.close();
	}

}
