# DMutex Simulation Program

This is a simulation program for a distributed mutual exclusion (DMutex) algorithm. It uses the DMutex class and the Process class to simulate the algorithm with a given number of processes and a given execution time.

## Features

- Simulates a distributed mutual exclusion algorithm.
- Allows for a configurable number of processes.
- Allows for a configurable execution time.
- Starts the processes as Runnable threads.
- Interrupts the processes after the given execution time.

## Usage

The program expects two arguments:

1. The number of processes to simulate.
2. The execution time for the simulation.

To run the program, use the following command:

```bash
java -jar DMutexSimulator.jar -p=<number_of_processes> -t=<execution_time>
```

Replace `<number_of_processes>` with the number of processes you want to simulate and `<execution_time>` with the desired execution time.

### Params

- `<number_of_processes>`: The number of processes to simulate.
- `<execution_time>`: The execution time for the simulation in milliseconds.
- `<help>`: Display the help message.

### Example

To simulate 5 processes for 10 seconds, use the following command:

```bash
java -jar DmutexSimulator.jar -p=5 -t=10000
```

## Requirements

This program requires Java 17 (openJDK 17) or later to run. You can download the latest version of Java from the [official website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).

This simulator does not require any additional libraries or dependencies (maven or gradle).

## Installation

To install the program, you can clone the repository using the following command:

```bash
git clone https://github.com/leon-junio/Dmutex-simulator.git
```

This will create a new directory called `Dmutex-simulator` with the program files.

To compile the program, navigate to the `Dmutex-simulator` directory and run the following command:

> cd build

- Linux or MAC

> ./build.sh

- Windows

> ./build.bat

## Author

This program was created by [Leon Junio](https://github.com/leon-junio).

## Version

This is version 1.0 of the DMutex Simulation Program.#   D m u t e x - s i m u l a t o r  
 # Dmutex-simulator
