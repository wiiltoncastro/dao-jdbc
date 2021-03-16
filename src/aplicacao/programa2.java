package aplicacao;


import java.util.Scanner;

import model.dao.DaoFabrica;
import model.dao.DepartamentoDao;

public class programa2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartamentoDao dD = DaoFabrica.criarDepartamentoDao();
		
		/*System.out.println("=== Teste 1: Departamento acharPorId ===");
		Departamento d1 = dD.acharPorId(3);
		System.out.println(d1);
		*/
		
		/*System.out.println("\n=== Teste 2: Departamento acharTodos ===");
		List<Departamento> lista = dD.acharTodos();
		lista.forEach(System.out::println);
		*/
		
		/*System.out.println("\n=== Teste 3: Departamento inserir ===");
		Departamento d1 = new Departamento(null, "Comida");
		dD.inserir(d1);
		System.out.println("Iserido! Novo Id: " + d1.getId());
		*/
		
		/*System.out.println("\n=== Teste 4: Departamento atualizar ===");
		Departamento d2 = dD.acharPorId(1);
		d2.setNome("Computadores");
		dD.atualizar(d2);
		System.out.println("Atualização completada!");
		*/
		
		/*System.out.println("\n=== Teste 5: Departamento deletar ===");
		System.out.println("Digite um Id: ");
		int id = sc.nextInt();
		dD.deletePorId(id);
		System.out.println("Excluido com sucesso!");
		*/
		
		sc.close();
	}
}