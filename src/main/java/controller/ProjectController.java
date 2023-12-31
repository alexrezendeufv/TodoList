package controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;
import java.sql.Date;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alexu
 */
public class ProjectController {
    
    
    public void save(Project project) throws ClassNotFoundException{
        
        String sql = "INSERT INTO projects (name,description, createAt, updateAt) VALUES (?,?,?,?)";
    
        Connection c = null;
        PreparedStatement statement = null;
    
        try {
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setString(1,project.getName());
            statement.setString(2,project.getDescription());
            statement.setDate(3,  new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdateAt().getTime())); 
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro em salva o projeto", ex);
        } finally{
            ConnectionFactory.closeConnection(c, statement);   
        }
    }
    
    public void updated(Project project){
     
        String sql = "UPDATE projects SET "
                + "name=?, "
                + "description =?, "
                + "createAt=?, "
                + "updateAt=?, "
                + "WHERE id=?";
    
        Connection connection=null;
        PreparedStatement statement=null;
        
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdateAt().getTime()));
            statement.setInt(5,project.getId());
            statement.execute();
        }catch(Exception e){
            throw new RuntimeException("N�o estpa atualizando", e);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
    public void remove(int id){
    
        
        String sql = "DELETE FROM projects WHERE id=?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.execute();
        }catch(Exception e){
            throw new RuntimeException("Ocorreu um erro na remo��o", e);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
        
        
    }
    
    public List<Project> getAll(){
        
        String sql = "SELECT * FROM projects";
        
        List<Project> projects = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createAt"));
                project.setUpdateAt(resultSet.getDate("updateAt"));
                projects.add(project);
            }
            
        }catch(Exception e){
            throw new RuntimeException("Erro em buscar projecto", e);
        }finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return projects;
    }
}

