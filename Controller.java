package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    Connection conn;

    public Controller() {
        conn = ConnectionUtil.connDB();
    }

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Label txtStatus;
    String email;
    String password;

    @FXML
    public void pressInsert(ActionEvent event) throws SQLException {
        String Insert = "INSERT INTO user(name, lastName, age, email, password) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(Insert);
        statement.setString(1, txtName.getText());
        statement.setString(2, txtLastname.getText());
        statement.setInt(3, Integer.parseInt(txtAge.getText()));
        statement.setString(4, txtEmail.getText());
        statement.setString(5, txtPass.getText());

        int result = statement.executeUpdate();

        if (result == 1) {
            txtStatus.setText("Данные успешно внесены.");
        }
    }

    @FXML
    public void pressUpdate(ActionEvent event) throws SQLException {
        String Update = "UPDATE user set name=?, lastName=?, age=?, email=?, password=? WHERE id_user=?";

        PreparedStatement statement = conn.prepareStatement(Update);
        statement.setString(1, txtName.getText());
        statement.setString(2, txtLastname.getText());
        statement.setInt(3, Integer.parseInt(txtAge.getText()));
        statement.setString(4, txtEmail.getText());
        statement.setString(5, txtPass.getText());
        statement.setInt(6, Integer.parseInt(txtID.getText()));
        int result = statement.executeUpdate();

        if (result == 1) {
            txtStatus.setText("Данные успешно обновлены.");
        }
    }

    @FXML
    public void pressDelete(ActionEvent event) throws SQLException {
        String Delete = "DELETE FROM user WHERE id_user=?";

        PreparedStatement statement = conn.prepareStatement(Delete);
        statement.setInt(1, Integer.parseInt(txtID.getText()));
        int result1 = statement.executeUpdate();

        if (result1 == 1) {
            txtStatus.setText("Данные успешно удалены.");
        }
    }

    @FXML
    public void pressSelect(ActionEvent event) throws SQLException {
        String Select = "SELECT * FROM user WHERE id_user=?";

        PreparedStatement statement = conn.prepareStatement(Select);

        statement.setString(1, txtID.getText());

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            txtName.setText(result.getString("name"));
            txtLastname.setText(result.getString("lastName"));
            txtAge.setText(result.getString("age"));
            txtEmail.setText(result.getString("email"));
            txtPass.setText(result.getString("password"));
        }
        txtStatus.setText("Сущестует");
    }

    @FXML
    public void pressClear(ActionEvent event) {
        txtID.setText("");
        txtName.setText("");
        txtLastname.setText("");
        txtAge.setText("");
        txtEmail.setText("");
        txtPass.setText("");
        txtStatus.setText("");
    }

    @FXML
    public void pressSignIn(ActionEvent event) throws SQLException {
        String sign = "SELECT * FROM user WHERE email=?";

        PreparedStatement statement = conn.prepareStatement(sign);
        statement.setString(1, txtEmail.getText());

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            email = result.getString("email");
            password = result.getString("password");
        }

        if (txtEmail.getText().isEmpty() && txtPass.getText().isEmpty()){
            txtStatus.setText("Введите логин и пароль.");
        } else if (txtEmail.getText().isEmpty()){
            txtStatus.setText("Введите Email.");
        } else if (txtPass.getText().isEmpty()) {
            txtStatus.setText("Введите пароль.");
        } else if (!(txtEmail.getText().isEmpty() && txtPass.getText().isEmpty())) {
            if (email.equals(txtEmail.getText()) && password.equals(txtPass.getText())) {
                txtStatus.setText("Вы авторизованы.");
            } else {
                txtStatus.setText("Авторизация не выполнена.");
            }
        }
    }
}
