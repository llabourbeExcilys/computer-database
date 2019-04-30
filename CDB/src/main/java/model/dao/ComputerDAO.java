package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exception.BadCompanyIdException;
import exception.NotFoundException;
import model.Computer;
import model.dao.mapper.ComputerMapper;

public class ComputerDAO extends DAO{

	//private static Logger logger = LoggerFactory.getLogger( Main.class );

	
	private final static String SQL_SELECT_ALL_COMPUTER = 
			"SELECT "
			+	"C.id AS computer_id, "
			+	"C.name AS computer_name, "
			+	"C.introduced AS computer_introduced, "
			+	"C.discontinued AS computer_discontinued, "
			+	"B.id AS company_id, "
			+	"B.name AS company_name "
			+"FROM "
			+	"computer C LEFT JOIN company B "
			+"ON "
			+	"C.company_id = B.id ";
	
	private final static String SQL_SELECT_COMPUTER_BY_ID =  
			SQL_SELECT_ALL_COMPUTER
			+"WHERE "
			+ 	"C.id = ? ";
	
	private final static String SQL_CREATE_COMPUTER = 
			"INSERT INTO "
			+ 	"computer (name,introduced,discontinued,company_id) "
			+"VALUES "
			+ 	"(?,?,?,?) ";

	private final static String SQL_UPDATE_COMPUTER = 
			"UPDATE "
			+ 	"computer " 
		    +"SET name = ?, "
			+	"introduced = ?, "
			+	"discontinued = ?, "
			+	"company_id = ? "
			+"WHERE "
			+	"id = ? ";
	
	private final static String SQL_DELETE_COMPUTER_BY_ID = 
			"DELETE FROM "
			+	"computer "
			+"WHERE "
			+	"id = ? ";

	private static final String SQL_SELECT_COMPUTER_PAGE = 
			SQL_SELECT_ALL_COMPUTER
			+"ORDER BY "
			+	"C.id "
			+"ASC LIMIT "
			+	"? "
			+"OFFSET "
			+	"? ";

	
	private static ComputerMapper computerMapper;
	
	/** Constructeur privé */
    private ComputerDAO(){}
     
    /** Instance unique non préinitialisée */
    private static ComputerDAO INSTANCE = null;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized ComputerDAO getInstance(){
    	computerMapper = ComputerMapper.getInstance();

        if (INSTANCE == null)
        	INSTANCE = new ComputerDAO(); 
        
        return INSTANCE;
    }
	
	// Create
	

	public long addComputer(String name, LocalDate ldateIntroduction, LocalDate ldateDiscontinuation, Optional<Long> idL) {
		try (Connection conn = DriverManager.getConnection(url, user, passwd);
			 PreparedStatement state = conn.prepareStatement(SQL_CREATE_COMPUTER, 
					 										 Statement.RETURN_GENERATED_KEYS);) {	
			
			state.setString(1, name);
			state.setDate(2, ldateIntroduction != null ? Date.valueOf(ldateIntroduction) : null);
			state.setDate(3, ldateDiscontinuation != null ? Date.valueOf(ldateDiscontinuation) : null);
			state.setLong(4, idL.isPresent() ? idL.get() : null);


			state.executeUpdate();
			ResultSet generatedKeys = state.getGeneratedKeys();
			generatedKeys.first();
			long id = generatedKeys.getLong(1);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	// Read
	
	public List<Computer> getComputerList() {
		List<Computer> resultList = new ArrayList<Computer>();
		try (Connection conn = DriverManager.getConnection(url, user, passwd);
			 Statement state = conn.createStatement();) {	
			
			//L'objet ResultSet contient le résultat de la requête SQL					
			ResultSet result = state.executeQuery(SQL_SELECT_ALL_COMPUTER);
			
			while(result.next()){ 
				Optional<Computer> computer = computerMapper.getComputer(result);
				if(computer.isPresent())
					resultList.add(computer.get());				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}


	public Optional<Computer> getComputerById(long idL) {

		try (Connection conn = DriverManager.getConnection(url, user, passwd);
			 PreparedStatement state = conn.prepareStatement(SQL_SELECT_COMPUTER_BY_ID);){	

			state.setLong(1, idL);
			ResultSet result = state.executeQuery();
			
			result.next();
			//if(!next)
			//	throw new NotFoundException("Id not found");
			
			return computerMapper.getComputer(result);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	public List<Computer> getComputerPage(int page, int nbByPage) {
		List<Computer> resultList = new ArrayList<Computer>();
		try (Connection conn = DriverManager.getConnection(url, user, passwd);
				 PreparedStatement state = conn.prepareStatement(SQL_SELECT_COMPUTER_PAGE);){	

				state.setInt(1, nbByPage);
				state.setInt(2, (page-1)*nbByPage);

				ResultSet result = state.executeQuery();
				
				while(result.next()){ 
					Optional<Computer> computer = computerMapper.getComputer(result);
					if(computer.isPresent())
						resultList.add(computer.get());				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return resultList;
	}

	// Update
	
	public void update(Computer c) {
		Date ldateIntroduction = c.getLdIntroduced() != null ? Date.valueOf(c.getLdIntroduced()) : null;
		Date ldateDiscontinuation = c.getLdDiscontinued() != null ? Date.valueOf(c.getLdDiscontinued()) : null;
		
		try (Connection conn = DriverManager.getConnection(url, user, passwd);
			PreparedStatement state = conn.prepareStatement(SQL_UPDATE_COMPUTER);){	

			//Création d'un objet prepared statement
			//On renseigne le paremetre
			state.setString(1, c.getName());
			state.setDate(2, ldateIntroduction);
			state.setDate(3, ldateDiscontinuation);
			state.setLong(4, c.getCompany().getId());
			state.setLong(5, c.getId());
			int resultCode = state.executeUpdate();
			if(resultCode == 0) 
				throw new NotFoundException("Id not found");
			
		}catch ( SQLIntegrityConstraintViolationException e) {
				throw new BadCompanyIdException("There is no company with id "+c.getCompany().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	


	// Delete
	
	public void deleteComputerById(long id) {
		try (Connection conn = DriverManager.getConnection(url, user, passwd);
			 PreparedStatement state = conn.prepareStatement(SQL_DELETE_COMPUTER_BY_ID);){

			//Création d'un objet prepared statement
			//On renseigne le paremetre
			state.setLong(1, id);
			int resultCode = state.executeUpdate();			
			if(resultCode == 0) 
				throw new NotFoundException("There is no computer with id "+id);
			
			//logger.debug("Delete OK !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}




}
