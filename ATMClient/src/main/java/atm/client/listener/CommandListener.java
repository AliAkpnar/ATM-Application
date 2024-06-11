package atm.client.listener;

import atm.client.constant.Command;
import atm.client.strategy.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandListener {
    private static final Map<String, Option> commands = new HashMap<>();
    private static final Options options = new Options();

    static {
        commands.put("display", new Option("d", "display", false, "Ex: atm -d"));
        commands.put("select", new Option("s", "select", true, "Ex: atm -s"));
        commands.put("login", new Option("lo", "login", true, "Ex: atm -lo 1234567890 1234"));
        commands.put("view_balance", new Option("vb", "view_balance", false, "Ex: atm --view_balance 1234567890"));
        commands.put("withdraw_cash", new Option("wc", "withdraw_cash", true, "Ex: atm --withdraw_cash 1234567890 300"));
        commands.put("deposit_cash", new Option("dc", "deposit_cash", true, "Ex: atm --deposit_cash 1234567890 100"));
        commands.put("change_pin", new Option("cp", "change_pin", true, "Ex: atm -cp 1234567890 4321"));
        commands.put("logoff", new Option("lf", "logoff", false, "Ex: atm logoff"));

        commands.forEach((key, value) -> {
            switch (Command.getValueFrom(value.getOpt())) {
                case LOGIN, CHANGE_PIN -> {
                    value.setArgs(2);
                    value.setArgName("card number and pin");
                }
                case VIEW_BALANCE -> {
                    value.setArgs(1);
                    value.setArgName("card number");
                }
                case WITHDRAW_CASH, DEPOSIT_CASH -> {
                    value.setArgs(2);
                    value.setArgName("card number and amount");
                }
                default -> {
                    value.setArgs(0);
                }
            }
            options.addOption(value);
        });
    }

    public void listen(WebSocketListener listener) {

        StrategyContext strategyContext = new StrategyContext(List.of(
                Login.builder(),
                AvailableCredit.builder(),
                ChangePin.builder(),
                DepositCash.builder(),
                LogOff.builder(),
                WithdrawCash.builder()));

        while (true) {
            System.out.println("Please enter your command. For more write 'display'");
            String command = "";

            while (!command.equalsIgnoreCase(Command.LOGOFF.name())) {
                try {
                    command = new BufferedReader(new InputStreamReader(System.in)).readLine();
                    String[] args = getArgs(command.trim());
                    Option selectedOption = options.getOption(args[1]);
                    if (selectedOption != null) {
                        Strategy strategy = strategyContext.select(selectedOption.getLongOpt());
                        if (strategy != null) {
                            listener.getSession().send("/app/process", new ObjectMapper().writeValueAsString(strategy.execute(args)));
                            if (strategy.getType().equals(Command.LOGOFF)) {
                                Thread.sleep(1000);
                            }
                        } else {
                            printHelp();
                        }
                    } else {
                        printHelp();
                    }
                } catch (Exception e) {
                    printHelp();
                }
            }
        }
    }

    private String[] getArgs(String commandLine) {
        return Arrays.stream(commandLine.split(" ")).filter(s -> !s.isEmpty()).toArray(String[]::new);
    }

    private static void printHelp() {
        final PrintWriter writer = new PrintWriter(System.out);
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(2000);
        formatter.printHelp("atm", "-----------", options, "------------");
        writer.flush();
    }
}
