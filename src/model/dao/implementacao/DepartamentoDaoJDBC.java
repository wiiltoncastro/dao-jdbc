package model.dao.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entidades.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao{

	private Connection conn;
	
	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void inserir(Departamento dep) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO departamento "
					+ "(Nome) "
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, dep.getNome());
			
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					dep.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("ERRO! Nenhuma linha foi afetada.");
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void atualizar(Departamento dep) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE departamento "
					+ "SET Nome = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, dep.getNome());
			st.setInt(2, dep.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deletePorId(Integer id) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM departamento WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	

	@Override
	public Departamento acharPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM departamento WHERE Id = ?");
				
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Departamento dep = new Departamento();
				dep.setId(rs.getInt("Id"));
				dep.setNome(rs.getString("Nome"));
				return dep;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Departamento> acharTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM departamento ORDER BY Nome");
			
			rs = st.executeQuery();
			
			List<Departamento> lista = new ArrayList<>();
			
			while (rs.next()) {
				Departamento dep = new Departamento();
				dep.setId(rs.getInt("Id"));
				dep.setNome(rs.getString("Nome"));
				lista.add(dep);
			}
			return lista;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
