@HtmlForm(method = "post", action = "/usersssssss")
public class Userr {
    @HtmlInput(name = "nickname", placeholder = "Ваш ник")
    private String nickname;
    @HtmlInput(name = "email",type = "email", placeholder = "Ваша почта")
    private String email;
    @HtmlInput(name = "password", type = "password", placeholder = "Пароль")
    private String password;
    @HtmlInput(name = "firstName", placeholder = "Ваше имя")
    private String firstName;
}
