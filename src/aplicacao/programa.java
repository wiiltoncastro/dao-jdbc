package aplicacao;

import java.util.Date;

import model.dao.DaoFabrica;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class programa {

	public static void main(String[] args) {

		Departamento d = new Departamento(1, "Livros");
		System.out.println(d.toString());
		
		Vendedor v = new Vendedor(21, "Bob", "bob@gmail.com", new Date(), 3000.00, d);
		
		System.out.println(v.toString());
	
		VendedorDao vD = DaoFabrica.criarVendedorDao();
		
	}

}
