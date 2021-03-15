package aplicacao;

import java.util.List;

import model.dao.DaoFabrica;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class programa {

	public static void main(String[] args) {
	
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
		
	}

}
