package java8.OCP.C10_JDBC;

public class JDBC {
	// Java Database Connectivity Language (JDBC): Accesses data as rows and columns.
	// Java Persistence API (JPA): Accesses data through Java objects using a
	// concept called object-relational mapping (ORM). Is not in the exam.
	
	// Introducing the Interfaces of JDBC
	// For the exam you need to know four key interfaces of JDBC. 
	// The interfaces are declared in the JDK. 
	/* 
		Driver: Knows how to get a connection to the database
		Connection: Knows how to communicate with the database
		Statement: Knows how to run the SQL
		ResultSet: Knows what was returned by a SELECT query
			All database classes are in the package java.sql	 
	 */
	// As you know, interfaces need a concrete class to implement them in order to be useful. 
	// These concrete classes come from the JDBC driver.
	// Example: PostgreSQL’s JAR is called something like postgresql-9.4–1201.jdbc4.jar.
	// This driver JAR contains an implementation of these key interfaces along with a number of others.
	
	// With JDBC, you use only the interfaces in your code and never the implementation classes directly.
	
	// First step: Connecting to a Database.
	// a JDBC URL has a variety of formats. They have three parts in common:
	// - The first piece is always the same. It is the protocol jdbc. 
	// - The second part is the name of the database such as derby, mysql, or postgres. 
	// - The third part is “the rest of it,” which is a database-specific format. 
	// Colons separate the three parts.
	// The third part typically contains the location (port is optional) and the name of the database.
	// 		jdbc:derby:zoo
	// 		jdbc:postgresql://localhost/zoo
	// 		jdbc:oracle:thin:@123.123.123.123:1521:zoo
	// 		jdbc:mysql://localhost:3306/zoo?profileSQL=true
	
	// see what is wrong with each of the following?
	// 		jdbc:postgresql://local/zoo
	//		jdbc:mysql://123456/zoo
	//		jdbc;oracle;thin;/localhost/zoo
	
	// Getting a Database Connection
	// There are two main ways to get a Connection
	// - DriverManager is the one covered on the exam. Do not use a DriverManager in code.
	// - DataSource is a factory, and it has more features than DriverManager.
	//   For ex: it can pool connections or store the database connection info outside the application.
	/*
		import java.sql.*;
		public class TestConnect {
			public static void main(String[] args) throws SQLException {
				Class.forName("org.postgresql.Driver"); // for legacy code. Not needed JDBC >= 4.0 driver.
				Connection conn = DriverManager.getConnection("jdbc:derby:zoo");
				// the same but with credentials.
				Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ocp-book", 
					"username", "password");
				System.out.println(conn);
			}
		}
		Prints out:
			org.apache.derby.impl.jdbc.EmbedConnection40@1372082959
			(XID = 156), (SESSIONID = 1), (DATABASE = zoo), (DRDAID = null)
		Just notice that the class is not Connection. The factory creates the class for you.
	 */
	
	// The DriverManager class looks through the classpath for JARs that contain a Driver.
	// DriverManager knows that a JAR is a driver because it contains a file called java.sql.Driver
	// in the directory META-INF/services. In other words, a driver might contain this information:
	// META-INF (mandatory with JDBC >= 4.0 Driver in Java 6)
	//		−service
	//		―java.sql.Driver
	// 		com
	// 		−wiley
	// 		―MyDriver.class
	// Inside the java.sql.Driver file is one line. It is the fully qualified package name of the
	// Driver implementation class.
	
	
	// Second step: Obtaining a Statement
	// In order to run SQL, you need to tell a Statement about it. 
	// It represents a SQL statement that you want to run using the Connection.
	// Getting a Statement from a Connection is easy:
	//      Statement stmt = conn.createStatement();
	// 		Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	
	// Choosing a ResultSet Type
	// By default, a ResultSet is in TYPE_FORWARD_ONLY mode. 
	// You can go through the data once in the order in which it was retrieved.
	// Two other modes are TYPE_SCROLL_INSENSITIVE and TYPE_SCROLL_SENSITIVE. 
	// Both allow you to go through the data in any order. You can go both forward and backward. 
	// You can even go to a specific spot in the data. 
	// The difference is what happens when data changes in the actual database while you are busy scrolling.
	/*
	 ResultSet type options
		ResultSet Type			Can Go 		See Latest Data			Supported by
								Backward	from Database Table		Most Drivers
		TYPE_FORWARD_ONLY		No 			No 						Yes
		TYPE_SCROLL_INSENSITIVE	Yes 		No 						Yes
		TYPE_SCROLL_SENSITIVE	Yes 		Yes 					No
	 */
	// Choosing a ResultSet Concurrency Mode
	// By default, a ResultSet is in CONCUR_READ_ONLY mode. 
	// It means that you can’t update the result set. 
	// The driver can downgrade you. This means that if you ask for CONCUR_UPDATABLE, 
	//  you will likely get a Statement that is CONCUR_READ_ONLY .
	/*
	 ResultSet concurrency mode options
		ResultSet Type			Can Read 		Can update			Supported by
								Data			Data				All Drivers
		CONCUR_READ_ONLY		Yes 			No					Yes
		CONCUR_UPDATABLEYes 	Yes 			Yes					No
	 */
	
	// Third step: Executing a Statement
	// method: executeUpdate(). The name is a little tricky because the SQL UPDATE statement
	// is not the only statement that uses this method.
	// It returns the number of rows that were inserted, deleted, or changed.
	/*
	 	Statement stmt = conn.createStatement();
		int result = stmt.executeUpdate("insert into species values(10, 'Deer', 3)");
		result = stmt.executeUpdate("update species set name = '' where name = 'None'");
		result = stmt.executeUpdate("delete from species where id = 10");
	 */
	
	// method: executeQuery()
	//  ResultSet rs = stmt.executeQuery("select * from species");

	// method: execute() that can run either a query or an update. 
	// It returns a boolean so that we know whether there is a ResultSet. 
	/*
		boolean isResultSet = stmt.execute(sql);
		if (isResultSet) {
			ResultSet rs = stmt.getResultSet();
			System.out.println("ran a query");
		} else {
			int result = stmt.getUpdateCount();
			System.out.println("ran an update");
		}
	*/
	/*
	 SQL runnable by execute method
		Method 					DELETE 	INSERT 	SELECT 	UPDATE
		stmt.execute() 			Yes 	Yes 	Yes 	Yes
		stmt.executeQuery() 	No 		No 		Yes 	No
		stmt.executeUpdate() 	Yes 	Yes 	No 		Yes
		
		
	 Return types of executes
		Method 					Return Type		What Is Returned for	What Is Returned for
												SELECT					DELETE/INSERT/UPDATE
		stmt.execute() 			boolean 		true 					false
		stmt.executeQuery() 	ResultSet 		The rows and columns	n/a
												returned
		stmt.executeUpdate() 	int 			n/a 					Number of rows 
																		added/changed/removed
	 */
	
	// In real life, you should not use Statement directly. 
	// You should use a subclass called PreparedStatement. 
	// This subclass has three advantages: performance, security, and readability
	
	// Forth step: Getting Data from a ResultSet
	// Reading a ResultSet. When working with a forward-only ResultSet, most of the time you 
	// will write a loop to look at each row. 
	/*
		Map<Integer, String> idToNameMap = new HashMap<>();
		ResultSet rs = stmt.executeQuery("select id, name from species");
		while(rs.next()) {
			int id = rs.getInt("id"); // by column name. Best way.
			int id = rs.getInt(1);    // by columna nuber. Starts from 1.
			String name = rs.getString("name");
			idToNameMap.put(id, name);
		}
	*/
	// A ResultSet has a cursor, which points to the current location in the data.
	// On the first loop iteration, rs.next() returns true and the cursor moves to point to the 
	// first row of data. Amnd so on until rs.next() returns false.
	
	// When you want only one row, you use an if statement rather than a while loop.
	// Very important to check that rs.next() returns true before trying to call a getter on the ResultSet
	/*
	 	ResultSet rs = stmt.executeQuery("select count(*) from animal");
		if(rs.next())
			System.out.println(rs.getInt("count"));		
	 */
	// Always use an if statement or while loop when calling rs.next().
	// Column indexes begin with 1.
	
	// Getting Data for a Column: the getTYPE methods.
	// not all the primitives are covered. Fir instance getChar() does not exist.
	// getDate() and getTimestamp()
	/*
	  	java.sql.Date sqlDate = rs.getDate(1);
		LocalDate localDate = sqlDate.toLocalDate();
		System.out.println(localDate); // 2001―05―06
		
		java.sql.Time sqlTime = rs.getTime(1);
		LocalTime localTime = sqlTime.toLocalTime();
		System.out.println(localTime); // 02:15
		
		java.sql.Timestamp sqlTimeStamp = rs.getTimestamp(1);
		LocalDateTime localDateTime = sqlTimeStamp.toLocalDateTime();
		System.out.println(localDateTime); // 2001―05―06T02:15
	 */
	// getObject() return any type. A cast is needed.
	
	// Scrolling ResultSet
	// A scrollable ResultSet allows you to position the cursor at any row.
	/*
	 Navigating a ResultSet
		Method 					Description 					Requires Scrollable ResultSet
		boolean absolute(int	Move cursor to the specified	Yes
		rowNum)					row number
		void afterLast() 		Move cursor to a location		Yes
								immediately after the last row
		void beforeFirst() 		Move cursor to a location		Yes
								immediately before the first
								row
		boolean first() 		Move cursor to the first row 	Yes
		boolean last() 			Move cursor to the last row 	Yes
		boolean next() 			Move cursor one row forward 	No
		boolean previous() 		Move cursor one row backward	Yes
		boolean relative(int	Move cursor forward or backward	Yes
		rowNum)					the specified number of
								rows
	 */
	/*
	Now let’s look at an example where the query doesn’t return any rows:
		Statement stmt = conn.createStatement(
		ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery("select id from species where id = -99");
		System.out.println(rs.first()); // false
		System.out.println(rs.last()); // false
	 */
	
	// absolute(): 
	// A negative number means to start counting from the end of the ResultSet rather than from the beginning.
	// absolute(-1) in a ResultSet of 5 tuples is the tuple 5. absolute(-2) is tuple 4. 
	// relative() 
	// that moves forward or backward the requested number of rows. 
	// It returns a boolean if the cursor is pointing to a row with data.
	// if cursor in tuple 3, the relative(2) is tuple 5.
	
	
	// Closing Database Resources
	// It is important to close resources when you are finished with them. 
	// JDBC resources. not closing them creates a resource leak that will eventually slow down your program.
	// in try-with-resources
	// 		Remember that a try-with-resources statement closes the resources in the
	// 		reverse order from which they were opened.
	// in finally.
	/* 
	 	} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			closeConnection(conn);
			}
		}
		private static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			} catch (SQLException e) { }
		} and so on
	 */
	// - Closing a Connection also closes the Statement and ResultSet. <- NOTE
	// - Closing a Statement also closes the ResultSet.
	
	// There’s another way to close a ResultSet. JDBC automatically closes a ResultSet when
	// you run another SQL statement from the same Statement.
	
	/*
	 How many resources are closed in this code?
		String url = jdbc:derby:zoo";
		try (Connection conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select count(*) from animal")) {
				if (rs.next()) System.out.println(rs.getInt(1));
				ResultSet rs2 = stmt.executeQuery("select count(*) from animal");
				int num = stmt.executeUpdate("update animal set name = 'clear' where name = 'other'");
			}
		The correct answer is four.
	 */
	
	// Dealing with Exceptions
	/*
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
			System.out.println(e.getErrorCode());
		}
		
		outputs
			ERROR: column "not_a_column" does not exist
				Position: 8
			42703
			0
	 */
	

}
