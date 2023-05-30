    package ru.rudXson.serverCommands;

    import ru.rudXson.exceptions.ExitException;
    import ru.rudXson.exceptions.NotEnoughArgsException;
    import ru.rudXson.exceptions.WrongArgsException;

    import javax.naming.NoPermissionException;
    import java.io.IOException;

    public interface Command {
        void execute(String[] args) throws NoPermissionException, IOException, WrongArgsException, NotEnoughArgsException, ExitException; // throws NEA, WA
    String getDescription();
}
