package data_access;

import entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.login.LoginUserDataAccessInterface;
import okhttp3.*;
import use_case.register.RegisterUserDataAccessInterface;

import java.io.IOException;

public class UserDataAccessObject implements LoginUserDataAccessInterface, RegisterUserDataAccessInterface {

    private final JSONArray USERINFO = getUserData();
    private String username;
    private String email;

    public UserDataAccessObject() throws IOException {
    }

    @Override
    public boolean userExists(String userIdentifier) {
        for (int i = 0; i < USERINFO.length(); i++) {
            JSONObject user = USERINFO.getJSONObject(i);
            if (user.getString("Email").equals(userIdentifier) || user.getString("Username").equals(userIdentifier)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUser(String userIdentifier) {
        JSONObject userData = new JSONObject();
        for (int i = 0; i < USERINFO.length(); i++) {
            JSONObject user = USERINFO.getJSONObject(i);
            if (user.getString("Email").equals(userIdentifier) || user.getString("Username").equals(userIdentifier)) {
                userData = USERINFO.getJSONObject(i);
                break;
            }
        }

        try {
            String username = userData.getString("Username");
            String email = userData.getString("Email");
            String password = userData.getString("Password");

            return new User(username, email, password);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public void save(User user) throws IOException {

        JSONArray currentUsers = getUserData();
        JSONObject userData = new JSONObject();
        userData.put("Username", user.get_username());
        userData.put("Email", user.get_email());
        userData.put("Password", user.get_password());
        currentUsers.put(userData);
        JSONObject updatedUsers = new JSONObject();
        updatedUsers.put("Users", currentUsers);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, updatedUsers.toString());
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/USERS")
                .method("PUT", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

    }

    public void setUsername(String username) {this.username = username;}

    public String getUsername() {return username;}

    public void setEmail(String email) {this.email = email;}

    public String getEmail() {return email;}



    private JSONArray getUserData() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://getpantry.cloud/apiv1/pantry/c8a932ca-ce25-4926-a92c-d127ecb78809/basket/USERS")
                .get()
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        JSONObject users = new JSONObject(response.body().string());
        return users.getJSONArray("Users");

    }
}
