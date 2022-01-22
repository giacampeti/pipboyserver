/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sapienza.softeng.dbsimplemanager;

import java.sql.*;


public class DBManager {

    public static void main(String[] args) throws Exception {

        String dbUrl = "jdbc:postgresql://" + "ec2-34-246-24-110.eu-west-1.compute.amazonaws.com" + ':' + 5432 + "/df1mrbveti0c86";

        Class.forName("org.postgresql.Driver");
        Connection conn
                = DriverManager.getConnection(dbUrl, "hjalwmcffbgsld", "bdcb16cb8195fa3c28e35ff0fa296e7e8101f40b69f211476335dd39ed95f766");
        Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select * from weapons;");
            while (rs.next()) {
                System.out.println(rs);
                System.out.print("Weapon = " + rs.getString("id") + " is : ");
                System.out.println(rs.getString("name"));
            }
        rs.close();
        conn.close();
    }
}
