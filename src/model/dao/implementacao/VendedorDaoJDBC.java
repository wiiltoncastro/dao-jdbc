package model.dao.implementacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

public class VendedorDaoJDBC implements VendedorDao{

	private Connection conn;
	
	public VendedorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(Vendedor vend) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO vendedor "
					+ "(Nome, Email, DataNascimento, SalarioBase, DepartamentoId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, vend.getNome());
			st.setString(2, vend.getEmail());
			st.setDate(3, new java.sql.Date(vend.getDataNascimento().getTime()));
			st.setDouble(4, vend.getSalarioBase());
			st.setInt(5, vend.getDepartamento().getId());
			
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					vend.setId(id);
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
	public void atualizar(Vendedor vend) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE vendedor "
					+ "SET Nome = ?, Email = ?, DataNascimento = ?, SalarioBase = ?, DepartamentoId = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, vend.getNome());
			st.setString(2, vend.getEmail());
			st.setDate(3, new java.sql.Date(vend.getDataNascimento().getTime()));
			st.setDouble(4, vend.getSalarioBase());
			st.setInt(5, vend.getDepartamento().getId());
			st.setInt(6, vend.getId());
			
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
		
	}

	@Override
	public Vendedor acharPorId(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT vendedor.*,departamento.Nome as DepNome "
					+ "FROM vendedor INNER JOIN departamento "
					+ "ON vendedor.DepartamentoId = departamento.Id "
					+ "WHERE vendedor.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Departamento dep = instanciarDepartamento(rs);
				Vendedor vend = instanciarVendedor(rs, dep);
				return vend;
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

	private Vendedor instanciarVendedor(ResultSet rs, Departamento dep) throws SQLException {
		Vendedor vend = new Vendedor();
		vend.setId(rs.getInt("Id"));
		vend.setNome(rs.getString("Nome"));
		vend.setEmail(rs.getString("Email"));
		vend.setSalarioBase(rs.getDouble("SalarioBase"));
		vend.setDataNascimento(rs.getDate("DataNascimento"));
		vend.setDepartamento(dep);
		return vend;
		
	}

	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartamentoId"));
		dep.setNome(rs.getString("DepNome"));
		return dep;
	}

	@Override
	public List<Vendedor> acharTodos() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT vendedor.*,departamento.Nome as DepNome "
					+ "FROM vendedor INNER JOIN departamento "
					+ "ON vendedor.DepartamentoId = departamento.Id "
					+ "ORDER BY Nome");
			
			rs = st.executeQuery();
			
			List<Vendedor> lista = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				
				Departamento dep = map.get(rs.getInt("DepartamentoId"));
				
				if (dep == null) {
					dep = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartamentoId"), dep);
				}
				
				Vendedor vend = instanciarVendedor(rs, dep);
				lista.add(vend);
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

	@Override
	public List<Vendedor> acharPorDepartamento(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT vendedor.*,departamento.Nome as DepNome "
					+ "FROM vendedor INNER JOIN departamento "
					+ "ON vendedor.DepartamentoId = departamento.Id "
					+ "WHERE DepartamentoId = ? "
					+ "ORDER BY Nome");
			
			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			
			List<Vendedor> lista = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<>();
			
			while (rs.next()) {
				
				Departamento dep = map.get(rs.getInt("DepartamentoId"));
				
				if (dep == null) {
					dep = instanciarDepartamento(rs);
					map.put(rs.getInt("DepartamentoId"), dep);
				}
				
				Vendedor vend = instanciarVendedor(rs, dep);
				lista.add(vend);
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
