/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Entity.Etat;
import Entity.Ticket;
import Entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jbuffeteau
 */
public class FonctionsMetier implements IMetier
{
    @Override
    public User GetUnUser(String login, String mdp)
    {
        User us = null;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idUser, nomUser, prenomUser, statutUser from users where loginUser = ? and pwdUser = ?");
            ps.setString(1, login);
            ps.setString(2, mdp);
            ResultSet rs = ps.executeQuery();
            rs.next();
            us = new User(rs.getInt("idUser"), rs.getString("nomUser"), rs.getString("prenomUser"),rs.getString("statutUser"));
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return us;
    }

    @Override
    public ArrayList<Ticket> GetAllTickets()
    {
        
         ArrayList<Ticket> lesTickets = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select IdTicket,nomTicket, dateTicket from tickets");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Ticket act = new Ticket(rs.getInt("idTicket"), rs.getString("nomTicket"),rs.getString("dateTicket"),rs.getString("nomTicket"));
                lesTickets.add(act);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesTickets;
    }

    @Override
    public ArrayList<Ticket> GetAllTicketsByIdUser(int idUser)
    {
        ArrayList<Ticket> lesTickets = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("SELECT ti.idTicket, ti.nomTicket, ti.dateTicket, et.nomEtat from tickets ti, etats et where numUser = ? and ti.numEtat = et.idEtat");
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Ticket ti = new Ticket(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4));
                lesTickets.add(ti);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesTickets;
    }

    @Override
    public void InsererTicket(int idTicket, String nomTicket, String dateTicket, int idUser, int idEtat) 
    {
         try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("insert into tickets values (?,?,?,?,?)");
            ps.setInt(1, idTicket );
            ps.setString(2, nomTicket );
            ps.setString(3, dateTicket);
            ps.setInt(4, idUser);
            ps.setInt(5, idEtat);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void ModifierEtatTicket(int idTicket, int idEtat) 
    {
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("update tickets set numEtat = ? where idTicket = ?");
            ps.setInt(1, idEtat);
            ps.setInt(2, idTicket);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public ArrayList<User> GetAllUsers()
    {
        ArrayList<User> lesUsers = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idUser, nomUser, prenomUser, statutUser from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                User us = new User(rs.getInt("idUser"), rs.getString("nomUser"), rs.getString("prenomUser"),rs.getString("statutUser"));
                lesUsers.add(us);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesUsers;
    }

    @Override
    public int GetLastIdTicket()
    {
        int lastId = 0;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select max(idTicket) as num from tickets");
            ResultSet rs = ps.executeQuery();
            rs.next();
            lastId = rs.getInt("num") + 1;
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastId;
    }

    @Override
    public int GetIdEtat(String nomEtat)
    {
        int idEtat = 0;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select idEtat from etats where nomEtat = ?");
            ps.setString(1, nomEtat );
            ResultSet rs = ps.executeQuery();
            rs.next();
            idEtat = rs.getInt(1);
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idEtat;
    }

    @Override
    public ArrayList<Etat> GetAllEtats()
    {
        ArrayList<Etat> lesEtats = new ArrayList<>();
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("select * from etats");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                Etat et = new Etat(rs.getInt(1), rs.getString(2));
                lesEtats.add(et);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesEtats;
    }
}
