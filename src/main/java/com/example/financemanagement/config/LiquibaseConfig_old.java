package com.example.financemanagement.config;

import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class LiquibaseConfig_old {

    private static final Logger logger = LoggerFactory.getLogger(LiquibaseConfig_old.class);

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initializeLiquibase() {
        logger.info("Starting Liquibase initialization...");
        try {
            syncLiquibase();
        } catch (Exception e) {
            logger.error("Failed to initialize Liquibase: " + e.getMessage(), e);
            throw new RuntimeException("Liquibase initialization failed", e);
        }
        logger.info("Liquibase initialization completed.");
    }

    private void syncLiquibase() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            logger.info("Checking if databasechangelog table exists...");
            String checkTableSql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'finance_management' AND table_name = 'databasechangelog'";
            logger.debug("Executing query: {}", checkTableSql);
            int tableCount = jdbcTemplate.queryForObject(checkTableSql, Integer.class);
            logger.info("Table count result: {}", tableCount);

            if (tableCount > 0) {
                logger.info("Checking if initial schema is tracked...");
                String checkSql = "SELECT COUNT(*) FROM finance_management.databasechangelog WHERE id = '1740114982733-1'";
                logger.debug("Executing query: {}", checkSql);
                int count = jdbcTemplate.queryForObject(checkSql, Integer.class);
                logger.info("Changeset count result: {}", count);

                if (count == 0) {
                    logger.info("Syncing DATABASECHANGELOG with existing schema...");
                    String insertSql = "INSERT INTO finance_management.databasechangelog " +
                                      "(id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, liquibase) " +
                                      "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, 'EXECUTED', '4.20.0')";

                    List<Object[]> batchArgs = Arrays.asList(
                        new Object[] {"1740114982733-1", "today (generated)", "db/changelog/db.changelog-master.yaml", 1, "8:dummy-md5", "createTable", ""},
                        new Object[] {"1740114982733-2", "today (generated)", "db/changelog/db.changelog-master.yaml", 2, "8:dummy-md5", "createTable", ""},
                        new Object[] {"1740114982733-3", "today (generated)", "db/changelog/db.changelog-master.yaml", 3, "8:dummy-md5", "createTable", ""},
                        new Object[] {"1740114982733-4", "today (generated)", "db/changelog/db.changelog-master.yaml", 4, "8:dummy-md5", "createTable", ""},
                        new Object[] {"1740114982733-5", "today (generated)", "db/changelog/db.changelog-master.yaml", 5, "8:dummy-md5", "createTable", ""},
                        new Object[] {"1740114982733-6", "today (generated)", "db/changelog/db.changelog-master.yaml", 6, "8:dummy-md5", "createTable", ""},
                        new Object[] {"1740114982733-7", "today (generated)", "db/changelog/db.changelog-master.yaml", 7, "8:dummy-md5", "createTable", ""},
                        new Object[] {"1740114982733-8", "today (generated)", "db/changelog/db.changelog-master.yaml", 8, "8:dummy-md5", "createTable", ""},
                        new Object[] {"1740114982733-9", "today (generated)", "db/changelog/db.changelog-master.yaml", 9, "8:dummy-md5", "addUniqueConstraint", ""},
                        new Object[] {"1740114982733-10", "today (generated)", "db/changelog/db.changelog-master.yaml", 10, "8:dummy-md5", "addUniqueConstraint", ""},
                        new Object[] {"1740114982733-11", "today (generated)", "db/changelog/db.changelog-master.yaml", 11, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-12", "today (generated)", "db/changelog/db.changelog-master.yaml", 12, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-13", "today (generated)", "db/changelog/db.changelog-master.yaml", 13, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-14", "today (generated)", "db/changelog/db.changelog-master.yaml", 14, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-15", "today (generated)", "db/changelog/db.changelog-master.yaml", 15, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-16", "today (generated)", "db/changelog/db.changelog-master.yaml", 16, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-17", "today (generated)", "db/changelog/db.changelog-master.yaml", 17, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-18", "today (generated)", "db/changelog/db.changelog-master.yaml", 18, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-19", "today (generated)", "db/changelog/db.changelog-master.yaml", 19, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-20", "today (generated)", "db/changelog/db.changelog-master.yaml", 20, "8:dummy-md5", "addForeignKeyConstraint", ""},
                        new Object[] {"1740114982733-21", "today (generated)", "db/changelog/db.changelog-master.yaml", 21, "8:dummy-md5", "addForeignKeyConstraint", ""}
                    );

                    logger.debug("Executing batch update with SQL: {}", insertSql);
                    jdbcTemplate.batchUpdate(insertSql, batchArgs);
                    logger.info("DATABASECHANGELOG synced with existing schema.");
                } else {
                    logger.info("DATABASECHANGELOG already synced with initial schema. No action needed.");
                }
            } else {
                logger.info("DATABASECHANGELOG table does not exist. Letting Liquibase create it and apply changes.");
            }

            // Configure and run Liquibase
            logger.info("Configuring and running Liquibase...");
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog("classpath:/db/changelog/db.changelog-master.yaml");
            liquibase.setDefaultSchema("finance_management");
            liquibase.afterPropertiesSet(); // Explicitly run Liquibase after sync
            logger.info("Liquibase execution completed successfully.");
        } catch (Exception e) {
            logger.error("Error during Liquibase initialization: " + e.getMessage(), e);
            throw new RuntimeException("Failed to initialize Liquibase", e);
        }
    }
}