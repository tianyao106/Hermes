package lwl.tianyao.hermes.manager;

import com.winfo.gdmsaec.app.hermes.core.anonotation.ClassId;

@ClassId("a")
public class UserManager {

    private static final UserManager instance = new UserManager();

    public static UserManager getInstance (){
        return instance;
    }

    String name;

    public String getUserName() {
        return name;
    }

    public void setUserName(String name) {
        this.name = name;
    }
}
