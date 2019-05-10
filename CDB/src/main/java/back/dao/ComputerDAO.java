package back.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;

import back.connection.ConnexionManager;
import back.exception.BadCompanyIdException;
import back.exception.NotFoundException;
import back.mapper.ComputerMapper;
import back.model.Computer;
import ch.qos.logback.classic.Logger;

public class ComputerDAO {
	
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
	
	private static final String SQL_COUNT_ALL_COMPUTER = 
			"SELECT COUNT(*) AS count from computer";

	
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
		    +"SET "
		    +	"name = ?, "
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
			+	"C."+"fieldToReplace"+" "+"OrderToReplace "
			+"LIMIT "
			+	"? "
			+"OFFSET "
			+	"? ";

	
	private static final String SQL_SELECT_COMPUTER_BY_NAME = 
			SQL_SELECT_ALL_COMPUTER+
			"WHERE "
			+	"C.name = ?";

	

	private static ComputerMapper computerMapper;
	private static DataSource dataSource;
    private static ComputerDAO INSTANCE = null;
	private static Logger logger = (Logger) LoggerFactory.getLogger( ComputerDAO.class );

	static {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));};

	
	private ComputerDAO(){}
     
    public static synchronized ComputerDAO getInstance(){
    	computerMapper = ComputerMapper.getInstance();
    	dataSource = ConnexionManager.getDataSource();
        if (INSTANCE == null)
        	INSTANCE = new ComputerDAO(); 
        
        return INSTANCE;
    }
	
	// Create
	

	public long addComputer(Computer computer) {
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement state = conn.prepareStatement(SQL_CREATE_COMPUTER, 
					 										 Statement.RETURN_GENERATED_KEYS);) {	
			
			state.setString(1, computer.getName());
			state.setDate(2, computer.getLdIntroduced() != null ? Date.valueOf(computer.getLdIntroduced()) : null);
			state.setDate(3, computer.getLdDiscontinued() != null ? Date.valueOf(computer.getLdDiscontinued()) : null);
			state.setObject(4, computer.getCompany() != null ? computer.getCompany().getId() : null);

			state.executeUpdate();
			ResultSet generatedKeys = state.getGeneratedKeys();
			generatedKeys.first();
			long id = generatedKeys.getLong(1);
			return id;
		} catch (Exception e) {
			logger.debug(e.getMessage());
			logger.error(e.getMessage());
		}
		return -1;
	}


	// Read
	
	public int getNumberOfComputer() {
		int size = 0;
		try (Connection conn = dataSource.getConnection();
			 Statement state = conn.createStatement();) {	
						
			ResultSet result = state.executeQuery(SQL_COUNT_ALL_COMPUTER);
			return result.next() ? result.getInt("count") : 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
	
	public List<Computer> getComputerList() {
		List<Computer> resultList = new ArrayList<Computer>();
		try (Connection conn = dataSource.getConnection();
			 Statement state = conn.createStatement(
					 							ResultSet.TYPE_SCROLL_INSENSITIVE,
					 							ResultSet.CONCUR_UPDATABLE);) {	
							
			ResultSet result = state.executeQuery(SQL_SELECT_ALL_COMPUTER);
			
			while(result.next()){
				result.previous();
				Optional<Computer> computer = computerMapper.getComputer(result);
				if(computer.isPresent())
					resultList.add(computer.get());				
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			logger.error(e.getMessage());
		} 
		return resultList;
	}


	public Optional<Computer> getComputerById(long idL) {
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement state = conn.prepareStatement(SQL_SELECT_COMPUTER_BY_ID);){	

			state.setLong(1, idL);
			ResultSet result = state.executeQuery();

			return computerMapper.getComputer(result);
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			logger.error(e.getMessage());
		}		
		return Optional.empty();
	}
	
	public Optional<Computer> getComputerByName(String computerSearch) {
		try (Connection conn = dataSource.getConnection();
				 PreparedStatement state = conn.prepareStatement(SQL_SELECT_COMPUTER_BY_NAME);){	

				state.setString(1, computerSearch);
				ResultSet result = state.executeQuery();
				return computerMapper.getComputer(result);
			} catch (SQLException e) {
				logger.debug(e.getMessage());
				logger.error(e.getMessage());
			}
			return Optional.empty();
	}

	
	public List<Computer> getComputerPage(int page, int nbByPage, SortingField field, SortingOrder order) {
		String requestString = SQL_SELECT_COMPUTER_PAGE
				.replace("fieldToReplace", field.getIdentifier())
				.replace("OrderToReplace", order.name());
		 return getComputerPage(page, nbByPage, requestString);
	}
	
	private List<Computer> getComputerPage(int page, int nbByPage, String SQL_REQUEST){
		List<Computer> resultList = new ArrayList<Computer>();
		try (Connection conn = dataSource.getConnection();
				 PreparedStatement state = conn.prepareStatement(SQL_REQUEST,
						 										 ResultSet.TYPE_SCROLL_INSENSITIVE,
						 										ResultSet.CONCUR_UPDATABLE);){	
			
				state.setInt(1, nbByPage);
				state.setInt(2, (page-1)*nbByPage);
				ResultSet result = state.executeQuery();
		
				while(result.next()){
					result.previous();
					Optional<Computer> computer = computerMapper.getComputer(result);
					if(computer.isPresent())
						resultList.add(computer.get());				
				}
			} catch (SQLException e) {
				logger.debug(e.getMessage());
				logger.error(e.getMessage());
			}
		return resultList;
	}
	

	
	// Update
	
	public void update(Computer c) {
		Date ldateIntroduction = c.getLdIntroduced() != null ? Date.valueOf(c.getLdIntroduced()) : null;
		Date ldateDiscontinuation = c.getLdDiscontinued() != null ? Date.valueOf(c.getLdDiscontinued()) : null;
		
		try (Connection conn = dataSource.getConnection();
			PreparedStatement state = conn.prepareStatement(SQL_UPDATE_COMPUTER);){	

			state.setString(1, c.getName());
			state.setDate(2, ldateIntroduction);
			state.setDate(3, ldateDiscontinuation);
		
			if(c.getCompany()!=null)
				state.setLong(4, c.getCompany().getId());
			else
				state.setNull(4, java.sql.Types.BIGINT);

			state.setLong(5, c.getId());
			
			int resultCode = state.executeUpdate();
			if(resultCode == 0) 
				throw new NotFoundException("Id not found");
		}catch ( SQLIntegrityConstraintViolationException e) {
				throw new BadCompanyIdException("There is no company with id "+c.getCompany().getId());
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			logger.error(e.getMessage());
		}
	}

	// Delete
	
	public void deleteComputerById(long id) {
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement state = conn.prepareStatement(SQL_DELETE_COMPUTER_BY_ID);){
			
			state.setLong(1, id);
			int resultCode = state.executeUpdate();			
			if(resultCode == 0) 
				throw new NotFoundException("There is no computer with id "+id);
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			logger.error(e.getMessage());
		}
	}

}
