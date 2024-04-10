package LoginSignUp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.*;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFIle, String title, String username){
        Parent root=null;
        if(username !=null ){
            try{
                FXMLLoader loader=new FXMLLoader(DBUtils.class.getResource(fxmlFIle));
                root=loader.load();
                LoggedinController loggedinController=loader.getController();
                loggedinController.setUserInformation(username);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            try{
                root=FXMLLoader.load(DBUtils.class.getResource(fxmlFIle));
            }
            catch (Exception e){
                e.printStackTrace();

            }
        }
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }
    public static void signUpUser(ActionEvent event, String username, String password, String salt){
        Connection connection=null;
        PreparedStatement psInsert=null;
        PreparedStatement psCheckUserExist=null;
        ResultSet resultSet=null;

        try{
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/DataSecurity","root","jupiter");
            psCheckUserExist=connection.prepareStatement("Select * from users where Username=?");
            psCheckUserExist.setString(1,username);
            resultSet=psCheckUserExist.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("This username is taken");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username is taken");
                alert.show();
            }else{
                psInsert=connection.prepareStatement("Insert into users (Username,HashedPassword, Salt) values (?, ?, ?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,salt);
                psInsert.executeUpdate();

                changeScene(event, "loggedin.fxml","Welcome", username);
            }
        }
        catch (SQLException e){
            e.printStackTrace();

        }
        finally{
            if(resultSet !=null){
                try{
                    resultSet.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExist !=null){
                try{
                    psCheckUserExist.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert !=null){
                try{
                    psInsert.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try{
                   connection.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try{
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/DataSecurity","root","jupiter");
            preparedStatement=connection.prepareStatement("Select HashedPassword,Salt from users where Username=?");
            preparedStatement.setString(1,username);
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                System.out.println("Credentials are not correct");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Credentials are not correct");
                alert.show();
            }
            else{
                while(resultSet.next()){
                    String retrievedPassword=resultSet.getString("HashedPassword");     //E merr psw me hash prej databazes
                    String salt = resultSet.getString("Salt");                          //E merr vleren e salt prej databazes
                    String hashedEnteredPassword = SIgnUpController.hashPassword(password, salt);  //Psw qe e jep useri kthehet ne hash me vleren e salt

                    if (retrievedPassword.equals(hashedEnteredPassword)) {
                        changeScene(event, "loggedin.fxml", "Welcome", username);
                    } else {
                        System.out.println("Passwords do not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Passwords do not match");
                        alert.show();
                    }
                }


            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            if(resultSet !=null){
                try{
                    resultSet.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement !=null){
                try{
                   preparedStatement.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try{
                    connection.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
