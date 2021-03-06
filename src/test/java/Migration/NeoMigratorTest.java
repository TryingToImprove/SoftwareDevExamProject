package Migration;

import java.sql.*;
import java.io.*;

import org.junit.Test;

import java.util.UUID;

import TxtFormat.*;

import org.neo4j.driver.v1.*;

public class NeoMigratorTest {

        @Test
        public void performMigration_shouldInsertAllData() throws Exception, IOException {
                // // DELETE ALL DATA 
                // org.neo4j.driver.v1.Driver driver = GraphDatabase.driver("bolt://localhost:7687",
                //                 AuthTokens.basic("neo4j", "123123qwe"));

                // try (Session session = driver.session()) {
                //         session.run("MATCH (n:Author) DETACH DELETE n");
                // }

                NeoCitiesMigrate cityMigrator = new NeoCitiesMigrate("bolt://localhost:7687",
                                AuthTokens.basic("neo4j", "123123qwe"));

                // /data/cities.csv
                cityMigrator.performMigration("/data/cities.csv");

                NeoBookMigrate bookMigrator = new NeoBookMigrate("bolt://localhost:7687",
                                AuthTokens.basic("neo4j", "123123qwe"), new IBookIdentifierProvider() {
                                        public UUID getNextIdentifier() {
                                                return UUID.randomUUID();
                                        }
                                });

                // "/data/allformatted.txt"
                bookMigrator.performMigration("/data/allformatted.txt");

                // FileIO fio = new FileIO();

                // ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
                // fio.createFormattedTxtAuthor(fio.read("data/test-data/books.csv"),
                //         new OutputStreamWriter(outputStream1, "UTF8"));
                // byte[] content1 = outputStream1.toByteArray();

                // ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
                // fio.createFormattedTxtBook(fio.read("data/test-data/books.csv"),
                //         new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content1), "UTF8")),
                //         new OutputStreamWriter(outputStream2, "UTF8"), new BookIdentifierProviderStub());
                // byte[] content2 = outputStream2.toByteArray();

                // ByteArrayOutputStream outputStream3 = new ByteArrayOutputStream();
                // fio.createFormattedTxtBookCity(fio.read("data/test-data/books.csv"), fio.read("data/test-data/cities.csv"),
                //         new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content2), "UTF8")),
                //         new OutputStreamWriter(outputStream3, "UTF8"));
                // byte[] content3 = outputStream3.toByteArray();

                // CitiesMigrateNeo cityMigrator = new CitiesMigrateNeo("bolt://localhost:7687",
                //         AuthTokens.basic("neo4j", "123123qwe"));

                // // /data/cities.csv
                // cityMigrator.performMigration("/data/test-data/cities.csv");

                // AuthorMigrateNeo amn = new AuthorMigrateNeo("bolt://localhost:7687", AuthTokens.basic("neo4j", "123123qwe"));
                // amn.performMigration(new ByteArrayInputStream(content1));

                // BooksMigrateNeo bmn = new BooksMigrateNeo("bolt://localhost:7687", AuthTokens.basic("neo4j", "123123qwe"));
                // bmn.performMigration(new ByteArrayInputStream(content2));

                // CreateRelationshipsNeo crn = new CreateRelationshipsNeo("bolt://localhost:7687",
                //         AuthTokens.basic("neo4j", "123123qwe"));
                // crn.performMigration(new ByteArrayInputStream(content3));
        }
}