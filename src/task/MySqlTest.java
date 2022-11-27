package task;

import java.sql.*;
import java.util.Scanner;

public class MySqlTest {

	public static void main(String[] args) throws SQLException {

		char y = 1;
		Scanner scan = new Scanner(System.in);

		do {
			try (Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/empData?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
					"root", "Aya7112000"); Statement stmt = con.createStatement();) {

				System.out.println(" 1- Insert Record \n 2- Delete record \n 3- Retrive by ID \n 4- Retrive all data ");
				int x = scan.nextInt();
				switch (x) {

				///////////////////////////////////////////// insert record
				case 1: {

					System.out.println("Enter employee's First name");
					String fir_name = scan.next();

					System.out.println("Enter employee's last name");
					String las_name = scan.next();

					System.out.println("Select gender (M,F)");
					String genderr = scan.next();

					System.out.println("Enter hire date in YYYY-MM-DD format");
					String date = scan.next();

					String sqlInsert = "insert into employees(first_name,last_name,gender,hire_date) \r\n" + "values(\""
							+ fir_name + "\",\"" + las_name + "\",\"" + genderr + "\",\"" + date + "\")";

					System.out.println("The SQL statement is: " + sqlInsert + "\n"); // Echo for debugging
					System.out.println(" Record inserted.\n");

				}
					break;
				//////////////////////// delete record

				case 2: {
					System.out.println("Enter employee's ID");
					String scannedId = scan.next();

					String sqlDelete = "delete from employees where emp_no =" + scannedId;
					System.out.println("The SQL statement is: " + sqlDelete + "\n"); // Echo for debugging

					stmt.executeUpdate(sqlDelete);

					System.out.println(" record deleted.\n");
				}

					break;

				//////////////////////// select record

				case 3: {
					System.out.println("Enter employee's ID");
					String scannedId = scan.next();

					String strSelectOne = "select first_name,last_name,gender,hire_date from employees where emp_no="
							+ scannedId;
					System.out.println("The SQL statement is: " + strSelectOne + "\n"); // Echo For debugging

					ResultSet rset = stmt.executeQuery(strSelectOne);

					while (rset.next()) {
						String fNAme = rset.getString("first_name"); // retrieve a 'String'-cell in the row
						String lName = rset.getString("last_name");
						String gender = rset.getString("gender");
						Date hireDate = rset.getDate("hire_date");

						System.out.println(fNAme + ", " + lName + ", " + gender + ", " + hireDate);
					}
				}

					break;

				case 4: {
					String strSelect = "select* from employees";
					System.out.println("The SQL statement is: " + strSelect + "\n"); // Echo For debugging

					ResultSet rset = stmt.executeQuery(strSelect);

//			         System.out.println("The records selected are:");
					int rowCount = 0;

					while (rset.next()) {
						int id = rset.getInt("emp_no");
						String fNAme = rset.getString("first_name"); // retrieve a 'String'-cell in the row
						String lName = rset.getString("last_name");
						String gender = rset.getString("gender");
						Date hireDate = rset.getDate("hire_date");

						System.out.println(id + ", " + fNAme + ", " + lName + ", " + gender + ", " + hireDate);
						++rowCount;
					}
					System.out.println("Total number of records = " + rowCount);
					break;

				}
				default:
					System.out.println("You entered wrong value");

				}

				System.out.println("Do you want another process(Y,N)");

				y = scan.next().charAt(0);

			} catch (SQLException e) {
				System.out.println(e);

			}

		} while (y == 'y' | y == 'Y');

		scan.close();

	}
}
