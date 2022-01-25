package it.sapienza.softeng.api.withjson.and.dbms;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;

/**
 * Repository class to expose web services methods
 * @author : pablohp
 */
@Path("weapons")
public class PipBoyRepository {
    private Connection conn;
    private final static int port = 5432; 

    /**
     * fetch connection to an existing DB
     * @return connection 
     * @throws URISyntaxException
     * @throws SQLException
     */
    private static Connection getConnection() throws URISyntaxException, SQLException {
        /* needed to establish connection, check URL format from DriverManager.getConnection() */
        String dbUrl = "jdbc:postgresql://" +
                       "ec2-34-246-24-110.eu-west-1.compute.amazonaws.com" +
                       ':' +
                       port + 
                       "/df1mrbveti0c86";
        return DriverManager.getConnection( dbUrl, 
                                            "hjalwmcffbgsld",
                                            "bdcb16cb8195fa3c28e35ff0fa296e7e8101f40b69f211476335dd39ed95f766");
    }

    /**
     * establish connection 
     * @throws URISyntaxException
     */
    public void setConnection() throws URISyntaxException {
        try {
            try {
                /* driver depends on DBMS used */
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PipBoyRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn = getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PipBoyRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @return List<Weapon> 
     */
    @GET
    @Path("")
    @Produces("application/json")
    public List<Weapon> getWeapons() { return findAll(); }

    private List<Weapon> findAll() {

        Statement stat = null;
        List<Weapon> wl = new ArrayList<>();

        try {
            /* if you want to get a single row */
            //stat = conn.prepareStatement("select * from weapons where id = ?");
            //stat.setLong(1, Integer.parseInt(String.valueOf(id)));
            //ResultSet rs = stat.executeQuery();
            
            stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select id, name, imagesrc, level, range, p1name, p1icon, p2name, p2icon, p3name, p3icon from ((( weapons as w JOIN perk1 as p1 on w.perk1 = p1.p1id)as j1 JOIN perk2 as p2 on j1.perk2 = p2.p2id)as j2 JOIN perk3 as p3 on j2.perk3 = p3.p3id);");

            while (rs.next()) {
                Weapon w = new Weapon();
                w.setId(Integer.parseInt(rs.getString("id")));
                w.setName(rs.getString("name"));
                w.setImageSrc(rs.getString("imagesrc"));
                w.setLevel(rs.getString("level"));
                w.setRange(rs.getString("range"));
                w.setP1Name(rs.getString("p1name"));
                w.setP1Icon(Integer.parseInt(rs.getString("p1icon")));  
                w.setP2Name(rs.getString("p2name"));
                w.setP2Icon(Integer.parseInt(rs.getString("p2icon")));
                w.setP3Name(rs.getString("p3name"));
                w.setP3Icon(Integer.parseInt(rs.getString("p3icon")));
                wl.add(w);
                Logger.getLogger(PipBoyRepository.class.getName()).log(Level.INFO, "Accessed : " + w);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PipBoyRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wl;
    }
}
