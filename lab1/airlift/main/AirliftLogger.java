package airlift.main;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import airlift.departureairport.DepartureAirport;
import airlift.destinationairport.DestinationAirport;
import airlift.entity.Hostess;
import airlift.entity.Passenger;
import airlift.entity.Pilot;
import airlift.plane.Plane;

public class AirliftLogger {

    private final ReentrantLock lock = new ReentrantLock(true);
    private final static Logger logger = Logger.getLogger(AirliftLogger.class.getName());
    private FileHandler fh;
    private DepartureAirport departureAirport;
    private DestinationAirport destinationAirport;
    private Plane plane;
    private Hostess hostess;
    private Pilot pilot;
    private Passenger[] passengers;

    private static class MyCustomFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            StringBuffer sb = new StringBuffer();
            sb.append(record.getMessage());
            sb.append("\n");
            return sb.toString();
        }
    }

    public void setDepartureAirport(DepartureAirport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public void setDestinationAirport(DestinationAirport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public void setHostess(Hostess hostess) {
        this.hostess = hostess;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public void setPassengers(Passenger[] passengers) {
        this.passengers = passengers;
    }

    public void initLog() {
        try {
            fh = new FileHandler("logging.log");
            logger.setUseParentHandlers(false);
            logger.addHandler(fh);
            MyCustomFormatter formatter = new MyCustomFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
            System.err.println("Log File error!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Log File error!");
        }
        String out = "";
        for (Passenger passenger : this.passengers) {
            out += "  P" + passenger.getId();
        }
        logger.info("Airlift - Description of the internal state\n");
        logger.info(" PT   HT " + out + " InQ InF PTAL");
    }

    public void writeLog() {
        writeLog(-1);
    }

    public void writeLog(int code) {
        this.lock.lock();
        try {
            String msg = null;
            switch (code) {
            case 0:
                msg = "departed with " + (this.plane.numberPassengersInPlane() + this.departureAirport.inCheck())
                        + " passengers.";
                break;
            case 1:
                msg = "boarding started.";
                break;
            case 2:
                msg = "arrived.";
                break;
            case 3:
                msg = "returning.";
                break;
            case 4:
                msg = "started.";
                break;
            case 5:
                msg = "passenger " + this.departureAirport.getPassengerInCheck() + " checked.";
                break;
            case 6:
                String out = "\nAirlift sum up:";
                out += "\nFlight 1 transported 5 passengers";
                logger.info(out);
                return;
            default:
                break;
            }
            if (msg != null) {
                logger.info("\nFlight " + this.plane.getFlights() + ": " + msg);
            }
            String out = this.pilot.getState().getDescription();
            out += " " + this.hostess.getState().getDescription();
            for (Passenger passenger : this.passengers) {
                out += " " + passenger.getState().getDescription();
            }
            out += String.format(" %3d", this.departureAirport.numberPassengersInQueue());
            out += String.format(" %3d", this.plane.numberPassengersInPlane());
            out += String.format(" %3d", this.destinationAirport.numberPassengersInDestination());
            logger.info(out);
        } finally {
            this.lock.unlock();
        }
    }
}
