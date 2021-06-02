package logger;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import entity.HostessState;
import entity.PassengerState;
import entity.PilotState;

/**
 * Airlift Logger
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class AirliftLogger {

    private final static Logger logger = Logger.getLogger(AirliftLogger.class.getName());

    /**
     * My Custom Logger Formatter
     *
     * @author Diogo Andrade 89265
     * @author Rodrigo Oliveira 90514
     * @see Formatter
     */
    private static class MyCustomFormatter extends Formatter {
        /**
         * This method is used to format the logger text.
         *
         * @return This returns the logger text.
         */
        @Override
        public String format(LogRecord record) {
            StringBuffer sb = new StringBuffer();
            sb.append(record.getMessage());
            sb.append("\n");
            return sb.toString();
        }
    }

    /**
     * Creates an Airlift Logger.
     */
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

    /**
     * This method is used write the initial log.
     *
     * @param totalPassengers The total Passengers;
     */
    public void initialLog(int totalPassengers) {
        String out = "";
        for (int i = 0; i < totalPassengers; i++) {
            String passengerId = String.valueOf(i);
            if (i <= 9) {
                passengerId = "0" + i;
            }
            out += "  P" + passengerId;
        }
        logger.info("Airlift - Description of the internal state\n");
        logger.info(" PT   HT " + out + " InQ InF PTAL");
    }

    /**
     * This method is used to write the message log.
     *
     * @param numberFlights The number of flight;
     * @param msg           The number message;
     */
    public void messageLog(int numberFlights, String msg) {
        logger.info("\nFlight " + numberFlights + ": " + msg);
    }

    /**
     * This method is used to write the state log.
     *
     * @param pilotState                    The Pilot State;
     * @param hostessState                  The Hostess State;
     * @param passengersStates              The passengers States;
     * @param numberPassengersInQueue       The number of Passengers In Queue;
     * @param numberPassengersInPlane       The number of Passengers In Plane;
     * @param numberPassengersInDestination The number of Passengers In Destination;
     */
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

    /**
     * This method is used to write the resume log.
     *
     * @param flightsResume The resume of fligths.
     */
    public void resumeLog(List<Integer> flightsResume) {
        String out = "\nAirlift sum up:";
        for (int i = 0; i < flightsResume.size(); i++) {
            out += "\nFlight " + (i + 1) + " transported " + flightsResume.get(i) + " passengers";
        }
        logger.info(out);
    }
}
