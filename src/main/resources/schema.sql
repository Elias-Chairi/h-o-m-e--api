CREATE TABLE IF NOT EXISTS Home (
    home_id VARCHAR(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Resident (
    resident_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    home_id VARCHAR(16) NOT NULL,
    FOREIGN KEY (home_id) REFERENCES Home(home_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Recurrence (
    recurrence_id INT PRIMARY KEY AUTO_INCREMENT,
    interval_days INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE
);

CREATE TABLE IF NOT EXISTS Task (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    assignedTo INT,
    due DATE,
    created DATE NOT NULL DEFAULT (CURRENT_DATE),
    createdBy INT NOT NULL,
    done BOOLEAN NOT NULL DEFAULT FALSE,
    recurrence_id INT,
    FOREIGN KEY (assignedTo) REFERENCES Resident(resident_id) ON DELETE SET NULL,
    FOREIGN KEY (createdBy) REFERENCES Resident(resident_id) ON DELETE CASCADE,
    FOREIGN KEY (recurrence_id) REFERENCES Recurrence(recurrence_id) ON DELETE SET NULL
);