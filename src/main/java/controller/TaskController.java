/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author alexu
 */
public class TaskController {
    
    
    public void Save(Task task){
        
        String sql = "INSERT INTO task (idProject,"
                + " name,"
                + " description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createAt,"
                + "updateAt) VALUES (?,?,?,?,?,?,?,?)";
        Connection c= null;
        PreparedStatement statement = null;
        
        try{
            c = ConnectionFactory.getConnection();
            statement = c.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3,task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        }catch(Exception ex){
            throw new RuntimeException( "Erro ao salvar tarefa"+ ex.getMessage(), ex);
        }finally{
            ConnectionFactory.closeConnection(c,statement);
        }
    }
    
    public void updated(Task task){
        
        String sql = "UPDATE task SET idProject=?, "
                + "name=?, "
                + "description=?,"
                + "notes=?,"
                + "completed =?,"
                + "deadine=?,"
                + "createAt=?"
                + "updateAt=?"
                + "WHERE id=?";
        
        
        Connection connection =null;
        PreparedStatement statement= null;
        
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1,task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3,task.getDescription());
            statement.setString(4,task.getNotes());
            statement.setBoolean(5,task.isIsCompleted());
            statement.setDate(6, (java.sql.Date) new Date(task.getDeadline().getTime()));
            statement.setDate(7, (java.sql.Date) new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, (java.sql.Date) new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9,task.getId());
            statement.execute();    
        }catch(Exception e){
            throw new RuntimeException("Deu erro aqui", e);
        }finally{
            ConnectionFactory.closeConnection(connection);
        }
        
        
    }
    
    public void removebyId(int taskId){
        
        String sql = "DELETE FROM task WHERE id =?";
        Connection conn = null;
        PreparedStatement statement =null;
        
        try{
        
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareCall(sql);
            statement.setInt(1, taskId);
            statement.execute();
            
        }catch(Exception e){
            throw new RuntimeException("Ocorreu um erro na remo��o", e);
        }finally{
            ConnectionFactory.closeConnection(conn,statement);
        }
        
    }
    
    public List<Task> getAll(int idProject){ //Vai retornar uma lista de tarefas se baseando no id do projeto
        
        String sql = "SELECT * FROM  task WHERE idProject =?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;  //Resposta no banco de dados para trazer algo de l�
        
        List<Task> tasks = new ArrayList<>();
        
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("idProject"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createAt"));
                task.setUpdatedAt(resultSet.getDate("updateAt"));
                
                tasks.add(task);
            }
            
        }catch(Exception e){
             throw new RuntimeException("Erro em chamar as tarefas");
        }finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
       
        //Lista de tarefas carregada do banco de dados
        return tasks;
    }

    
}
