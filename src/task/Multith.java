package task;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class MainThread implements Runnable {

	static Scanner scan = new Scanner(System.in);

	public void select() {
		try (Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/empData?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
				"root", "Aya7112000"); Statement stmt = con.createStatement();) {
			System.out.println( Thread.currentThread().getName());

			stmt.executeUpdate("start transaction");

			String sqlSelUpdate = "select first_name,gender from employees " + "where emp_no= 1062 " + " for update";
			System.out.println("The SQL statement is: " + sqlSelUpdate + "\n");

			stmt.execute(sqlSelUpdate);

			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String strSelectOne = "select first_name,last_name,gender,hire_date from emp where emp_no= 1062";
			System.out.println("The SQL statement is: " + strSelectOne + "\n"); // Echo For debugging

			ResultSet rset = stmt.executeQuery(strSelectOne);

			while (rset.next()) {
				String fNAme = rset.getString("first_name"); // retrieve a 'String'-cell in the row
				String lName = rset.getString("last_name");
				String gender = rset.getString("gender");
				Date hireDate = rset.getDate("hire_date");
				int salary = rset.getInt("salary");

				System.out.println(fNAme + ", " + lName + ", " + gender + ", " + hireDate + ", " + salary);
			}

			System.out.println(" record selected. from thread:  " + Thread.currentThread().getName());

			stmt.execute("commit");

		} catch (SQLException r) {
			r.getStackTrace();
		}

	}

	public void retrieveAllData() {
		try (Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/empData?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
				"root", "Aya7112000"); Statement stmt = con.createStatement();) {

			String strSelect = "select* from employees";
			System.out.println("The SQL statement is: " + strSelect + "\n"); 

			ResultSet rset = stmt.executeQuery(strSelect);

//     System.out.println("The records selected are:");
			int rowCount = 0;

			while (rset.next()) {
				int id = rset.getInt("emp_no");
				String fNAme = rset.getString("first_name"); 
				String lName = rset.getString("last_name");
				String gender = rset.getString("gender");
				Date hireDate = rset.getDate("hire_date");

				int salary = rset.getInt("salary");

				System.out.println(fNAme + ", " + lName + ", " + gender + ", " + hireDate + ", " + salary);
				++rowCount;
			}
			System.out.println("Total number of records = " + rowCount + "from " + Thread.currentThread().getName());
		} catch (SQLException e) {
		}
	}

	public void retrieveRec() {
		try (Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/empData?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
				"root", "Aya7112000"); Statement stmt = con.createStatement();) {

			String strSelect = "select * from emp where emp_no=1062";
			System.out.println("The SQL statement is: " + strSelect + "\n"); 

			ResultSet rset = stmt.executeQuery(strSelect);

//     System.out.println("The records selected are:");
			int rowCount = 0;

			while (rset.next()) {
				int id = rset.getInt("emp_no");
				String fNAme = rset.getString("first_name"); // retrieve a 'String'-cell in the row
				String lName = rset.getString("last_name");
				String gender = rset.getString("gender");
				Date hireDate = rset.getDate("hire_date");
				int salary = rset.getInt("salary");

				System.out.println(fNAme + ", " + lName + ", " + gender + ", " + hireDate + ", " + salary);
				++rowCount;
			}
			System.out.println("Total number of records = " + rowCount +"  from thread: "+ Thread.currentThread().getName());
		} catch (SQLException e) {
		}
	}

	public void updateValue() {
		try (Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/empData?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
				"root", "Aya7112000"); Statement stmt = con.createStatement();) {
			stmt.execute("update emp set salary =salary+100 where emp_no = 1062");

			System.out.println(" record Updated from thread: " + Thread.currentThread().getName());
		} catch (SQLException r) {
			r.getStackTrace();
		}

	}

	public void retrieveForMain() {
		System.out.println("From Main..");
		retrieveRec();

	}

	@Override
	public void run() {
		select();
		retrieveAllData();
		updateValue();
		retrieveRec();
	}

//	
}

public class Multith {

	public static void main(String[] args) {
		Runnable r = new MainThread();
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		t1.start();
		t2.start();

		MainThread mTh = new MainThread();
		mTh.retrieveForMain();

	}

}