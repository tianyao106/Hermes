package lwl.tianyao.hermes.manager;

import com.winfo.gdmsaec.app.hermes.core.anonotation.ClassId;

@ClassId("a")
public interface IUserManager {

    void setUserName(String name);

    String getUserName();
}
