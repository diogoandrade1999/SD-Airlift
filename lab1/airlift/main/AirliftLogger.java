package airlift.main;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import airlift.entity.HostessState;
import airlift.entity.PassengerState;
import airlift.entity.PilotState;

public class AirliftLogger {

    private final static Logger logger = Logger.getLogger(AirliftLogger.class.getName());

    private static class MyCustomFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            StringBuffer sb = new StringBuffer();
            sb.append(record.getMessage());
            sb.append("\n");
            return sb.toString();
        }
    }

    public AirliftLogger() {
        try {
            MyCustomFormatter formatter = new MyCustomFormatter();
            FileHandler fh = new FileHandler("logging.log");
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (SecurityException e) {
            System.err.println("Log File error!");
        } catch (IOException e) {
            System.err.println("Log File error!");
        }
    }

    public void initialLog(int totalPassengers) {
        String out = "";
        for (int i = 0; i < totalPassengers; i++) {
            String passengerId = String.valueOf(i);
            if (i < 9) {
                passengerId = "0" + i;
            }
            out += "  P" + passengerId;
        }
        logger.info("Airlift - Description of the internal state\n");
        logger.info(" PT   HT " + out + " InQ InF PTAL");
    }

    public void messageLog(int numberFlights, String msg) {
        logger.info("\nFlight " + numberFlights + ": " + msg);
    }

    public void stateLog(PilotState pilotState, HostessState hostessState, PassengerState[] passengersStates,
            int numberPassengersInQueue, int numberPassengersInPlane, int numberPassengersInDestination) {
        String out = pilotState.getDescription();
        out += " " + hostessState.getDescription();
        for (PassengerState passengerStates : passengersStates) {
            out += " " + passengerStates.getDescription();
        }
        out += String.format(" %3d", numberPassengersInQueue);
        out += String.format(" %3d", numberPassengersInPlane);
        out += String.format(" %3d", numberPassengersInDestination);
        logger.info(out);
    }

    public void resumeLog(List<Integer> flightsResume) {
        String out = "\nAirlift sum up:";
        for (int i = 0; i < flightsResume.size(); i++) {
            out += "\nFlight " + (i + 1) + " transported " + flightsResume.get(i) + " passengers";
        }
        logger.info(out);
    }
}
