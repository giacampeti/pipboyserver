/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sapienza.softeng.api.withjson.and.dbms;

import java.net.URISyntaxException;
/**
 *
 * @author studente
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/fligths")
public class FligthsRepository {

    private Connection conn;
    // -- user ---    ---------------------------psw --------------------------------- ------------------------------ host-------------- -port- ---path------
    //"hjalwmcffbgsld:bdcb16cb8195fa3c28e35ff0fa296e7e8101f40b69f211476335dd39ed95f766@ec2-34-246-24-110.eu-west-1.compute.amazonaws.com:5432/df1mrbveti0c86"
    private static Connection getConnection() throws URISyntaxException, SQLException {
        
        String dbUrl = "jdbc:postgresql://" + "ec2-34-246-24-110.eu-west-1.compute.amazonaws.com" + ':' + 5432 + "/df1mrbveti0c86";
    
        return DriverManager.getConnection(dbUrl, "hjalwmcffbgsld", "bdcb16cb8195fa3c28e35ff0fa296e7e8101f40b69f211476335dd39ed95f766");
    }
    
    public void setConnection() throws URISyntaxException {
        try {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FligthsRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
           conn = getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FligthsRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @GET
    @Path("{fligthId}")
    @Produces("application/json")

    public Fligth getFligth(@PathParam("fligthId") int fligthId) {

        return findById(fligthId);
    }

    @PUT
    @Path("{fligthId}")
    @Consumes("application/json")
    public Response updateFligth(@PathParam("fligthId") int fligthId, Fligth fligth) {
        Fligth existing = findById(fligthId);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (existing.equals(fligth)) {
            return Response.notModified().build();
        }
        // fligths.put(fligthId, fligth);
        update(fligthId, fligth);
        return Response.ok().build();
    }

    private Fligth findById(int id) {
        
        PreparedStatement stat = null;
        Fligth fl = null;
        try {
            stat = conn.prepareStatement("select * from fligth where id = ?");
            stat.setString(1, String.valueOf(id));
        
        ResultSet rs = stat.executeQuery();
        if (rs.next()) {
            fl = new Fligth();
            fl.setId(Integer.parseInt(rs.getString("id")));
            fl.setName(rs.getString("name"));
            Logger.getLogger(FligthsRepository.class.getName()).log(Level.INFO, "Accessed : " + fl);
        }
        rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(FligthsRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        /* simple version 
        for (Map.Entry<Integer, Fligth> fligth : fligths.entrySet()) {
            if (fligth.getKey() == id) {
                return fligth.getValue();
            }
        }
        */
        return fl;   
    }
    
    private void update(int fligthId, Fligth fligth)
    {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement("update fligth set name = ? where id = ?");
            stat.setString(1, fligth.getName());
            stat.setString(2, String.valueOf(fligthId));
        
        int affectedRow = stat.executeUpdate();
        if (affectedRow == 1) {
            Logger.getLogger(FligthsRepository.class.getName()).log(Level.INFO, "Updated : " + fligth);
            return;
        }    
        else throw new RuntimeException();
        }
        catch (Exception ex) {
            Logger.getLogger(FligthsRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
