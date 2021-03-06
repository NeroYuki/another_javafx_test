package exception;

public class ErrorCode {
    private int code = 0;
    private String message = "";
    private static final String[] errorMessagePool = {
            "Unknown failure when performing action",       //0
            "Failed to establish connection to database",   //1
            "Incorrect Username or Password",               //2
            "Error happen when registering new user",       //3
            "Error happen when registering new balance",    //4
            "Error happen when registering new budget",     //5
            "Error happen when registering new loan",       //6
            "Error happen when registering new saving",     //7
            "Error happen when registering new transaction",     //8
            "Error happen when registering new category",   //9
            "Error happen when removing balance",           //10
            "Error happen when removing budget",            //11
            "Error happen when removing category",          //12
            "Error happen when removing loan",              //13
            "Error happen when removing saving",            //14
            "Error happen when removing transaction",       //15
            "Error happen when removing user",              //16
            "Error happen when updating balance",           //17
            "Error happen when updating categories",        //18
            "Error happen when updating loan",              //19
            "Error happen when updating saving",            //20
            "Error happen when updating transaction",       //21
            "Error happen when updating user",              //22
            "Can't find the budget id binded to this user", //23
            "Error happen when removing financial goal",    //24
            "Error happen when updating financial goal",    //25
            "Error happen when registering new financial goal"  //26
    };

    public ErrorCode(int code) {
        if (code >= 0 && code < errorMessagePool.length) this.code = code;
        this.message = errorMessagePool[this.code];
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
