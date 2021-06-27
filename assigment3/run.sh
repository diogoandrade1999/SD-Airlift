#!bin/bash

# execution example: bash ./run.sh 9 2 3 4 5 6 7 8

machine="l040101-ws"
host=".ua.pt"
user="sd202"
pass="airlift202"
ports=(22210 22211 22212 22213 22214 22215 22216 22217 22218 22219)
machine_number=()

proccess_command_line() {
    if [[ $number_args -ne 8 ]]; then
        echo "Error: Number of Args is Wrong!"
        exit 1
    fi

    for i in $args; do
        if [ $i -eq "1" ]; then
            machine_number+=("01")
        elif [ $i -eq "2" ]; then
            machine_number+=("02")
        elif [ $i -eq "3" ]; then
            machine_number+=("03")
        elif [ $i -eq "4" ]; then
            machine_number+=("04")
        elif [ $i -eq "5" ]; then
            machine_number+=("05")
        elif [ $i -eq "6" ]; then
            machine_number+=("06")
        elif [ $i -eq "7" ]; then
            machine_number+=("07")
        elif [ $i -eq "8" ]; then
            machine_number+=("08")
        elif [ $i -eq "9" ]; then
            machine_number+=("09")
        elif [ $i -eq "10" ]; then
            machine_number+=("10")
        else
            echo "Error: Unacceptable Arg! - "$i
            exit 1
        fi
    done
}

send_code() {
    printf "\nSend DepartureAirport!\n"
    echo "put -r ./DepartureAirport" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[0]}${host}

    printf "\nSend DestinationAirport!\n"
    echo "put -r ./DestinationAirport" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[1]}${host}

    printf "\nSend Plane!\n"
    echo "put -r ./Plane" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[2]}${host}

    printf "\nSend Repository!\n"
    echo "put -r ./Repository" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[3]}${host}

    printf "\nSend Pilot!\n"
    echo "put -r ./Pilot" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[4]}${host}

    printf "\nSend Hostess!\n"
    echo "put -r ./Hostess" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[5]}${host}

    printf "\nSend Passengers!\n"
    echo "put -r ./Passenger" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[6]}${host}

    printf "\nSend Register!\n"
    echo "put -r ./Register" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[7]}${host}
}

start() {
    printf "\nStart Register!\n"
    gnome-terminal -- /bin/sh -c 'sshpass -p '$pass' ssh '${user}'"@"'${machine}${machine_number[7]}${host}' "cd Register/src; echo Registry; rmiregistry -J-Djava.rmi.server.codebase="http://localhost/sd202/Register/src" -J-Djava.rmi.server.useCodebaseOnly=true '${ports[4]}'"; bash' &

    sleep 5;

    gnome-terminal -- /bin/sh -c 'sshpass -p '$pass' ssh '${user}'"@"'${machine}${machine_number[7]}${host}' "cd Register; bash ./run.sh '${ports[5]}' '${machine}${machine_number[7]}${host}' '${ports[4]}'"; bash' &

    sleep 10;

    printf "\nStart Repository!\n"
    gnome-terminal -- /bin/sh -c 'sshpass -p '$pass' ssh '${user}'"@"'${machine}${machine_number[3]}${host}' "cd Repository; bash ./run.sh '${ports[3]}' '${machine}${machine_number[7]}${host}' '${ports[4]}'"; bash' &

    sleep 5;

    printf "\nStart DepartureAirport!\n"
    gnome-terminal -- /bin/sh -c 'sshpass -p '$pass' ssh '${user}'"@"'${machine}${machine_number[0]}${host}' "cd DepartureAirport; bash ./run.sh '${ports[0]}' '${machine}${machine_number[7]}${host}' '${ports[4]}'"; bash' &

    printf "\nStart DestinationAirport!\n"
    gnome-terminal -- /bin/sh -c 'sshpass -p '$pass' ssh '${user}'"@"'${machine}${machine_number[1]}${host}' "cd DestinationAirport; bash ./run.sh '${ports[1]}' '${machine}${machine_number[7]}${host}' '${ports[4]}'"; bash' &

    printf "\nStart Plane!\n"
    gnome-terminal -- /bin/sh -c 'sshpass -p '$pass' ssh '${user}'"@"'${machine}${machine_number[2]}${host}' "cd Plane; bash ./run.sh '${ports[2]}' '${machine}${machine_number[7]}${host}' '${ports[4]}'"; bash' &

    # give some time to servers start
    sleep 10

    printf "\nStart Pilot!\n"
    gnome-terminal -- /bin/sh -c 'sshpass -p '$pass' ssh '${user}'"@"'${machine}${machine_number[4]}${host}' "cd Pilot; echo Pilot; bash ./run.sh '${machine}${machine_number[7]}${host}' '${ports[4]}'"; bash' &

    printf "\nStart Hostess!\n"
    gnome-terminal -- /bin/sh -c 'sshpass -p '$pass' ssh '${user}'"@"'${machine}${machine_number[5]}${host}' "cd Hostess; echo Hostess; bash ./run.sh '${machine}${machine_number[7]}${host}' '${ports[4]}'"; bash' &

    printf "\nStart Passengers!\n"
    gnome-terminal -- /bin/sh -c 'sshpass -p '$pass' ssh '${user}'"@"'${machine}${machine_number[6]}${host}' "cd Passenger; echo Passengers; bash ./run.sh '${machine}${machine_number[7]}${host}' '${ports[4]}'"; bash' &
}

get_result() {
    printf "\nWait for the end of execution!\n"
    sleep 35

    printf "\nGet Logging File!\n"
    echo "get Repository/logging.log" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[3]}${host}

    cat ./logging.log
}

force_shutdown() {
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[0]}${host} "pkill -f main.Main" >/dev/null 2>&1
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[1]}${host} "pkill -f main.Main" >/dev/null 2>&1
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[2]}${host} "pkill -f main.Main" >/dev/null 2>&1
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[3]}${host} "pkill -f main.Main" >/dev/null 2>&1
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[4]}${host} "pkill -f main.Main" >/dev/null 2>&1
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[5]}${host} "pkill -f main.Main" >/dev/null 2>&1
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[6]}${host} "pkill -f main.Main" >/dev/null 2>&1

    printf "\nShutdown Registry!\n"
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[7]}${host} "pkill -f main.Main" >/dev/null 2>&1
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[7]}${host} "pkill -f rmiregistry" >/dev/null 2>&1
}

number_args=$#
args=$@

proccess_command_line
send_code
start
get_result
force_shutdown
