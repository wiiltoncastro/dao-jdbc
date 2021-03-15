package model.dao;

import db.DB;
import model.dao.implementacao.VendedorDaoJDBC;

public class DaoFabrica {

	public static VendedorDao criarVendedorDao() {
		return new VendedorDaoJDBC(DB.getConnection());
	}
}
