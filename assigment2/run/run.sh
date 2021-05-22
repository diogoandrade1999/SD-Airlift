#!bin/bash

machine="l040101-ws"
host=".ua.pt"
user="sd202"
pass="airlift202"
ports=(22210 22211 22212 22213 22214 22215 22216 22217 22218 22219)
machine_number=()

proccess_command_line() {
    if [[ $number_args -ne 7 ]]; then
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
    echo "put -r ../DepartureAirport" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[0]}${host}

    printf "\nSend DestinationAirport!\n"
    echo "put -r ../DestinationAirport" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[1]}${host}

    printf "\nSend Plane!\n"
    echo "put -r ../Plane" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[2]}${host}

    printf "\nSend Repository!\n"
    echo "put -r ../Repository" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[3]}${host}

    printf "\nSend Pilot!\n"
    echo "put -r ../Pilot" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[4]}${host}

    printf "\nSend Hostess!\n"
    echo "put -r ../Hostess" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[5]}${host}

    printf "\nSend Passengers!\n"
    echo "put -r ../Passenger" | sshpass -p $pass sftp ${user}"@"${machine}${machine_number[6]}${host}
}

start() {
    printf "\nStart DepartureAirport!\n"
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[0]}${host} " cd ./DepartureAirport && bash ./run.sh ${machine}${machine_number[0]}${host} ${machine}${machine_number[3]}${host} ${ports[0]} ${ports[3]} & " &

    printf "\nStart DestinationAirport!\n"
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[1]}${host} " cd ./DestinationAirport && bash ./run.sh ${machine}${machine_number[1]}${host} ${machine}${machine_number[3]}${host} ${ports[1]} ${ports[3]} & " &

    printf "\nStart Plane!\n"
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[2]}${host} " cd ./Plane && bash ./run.sh ${machine}${machine_number[2]}${host} ${machine}${machine_number[3]}${host} ${ports[2]} ${ports[3]} & " &

    printf "\nStart Repository!\n"
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[3]}${host} " cd ./Repository && bash ./run.sh ${machine}${machine_number[3]}${host} ${ports[3]} & " &

    # give some time to servers start
    sleep 5

    printf "\nStart Pilot!\n"
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[4]}${host} " cd ./Pilot && bash ./run.sh ${machine}${machine_number[0]}${host} ${machine}${machine_number[2]}${host} ${ports[0]} ${ports[2]} & " &

    printf "\nStart Hostess!\n"
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[5]}${host} " cd ./Hostess && bash ./run.sh ${machine}${machine_number[0]}${host} ${machine}${machine_number[2]}${host} ${ports[0]} ${ports[2]} & " &

    printf "\nStart Passengers!\n"
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[6]}${host} " cd ./Passenger && bash ./run.sh ${machine}${machine_number[0]}${host} ${machine}${machine_number[1]}${host} ${machine}${machine_number[2]}${host} ${ports[0]} ${ports[1]} ${ports[2]} & " &
}

get_result() {
    printf "\nGet Logging File!\n"
    sshpass -p $pass ssh ${user}"@"${machine}${machine_number[3]}${host} " cat ./Repository/logging.log "
}

number_args=$#
args=$@

proccess_command_line
send_code
start
sleep 60
get_result
