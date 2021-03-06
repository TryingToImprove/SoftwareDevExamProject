package Migration;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class CitiesMigrateTests {
    @Test
    public void shouldCreateExpectSQLCommand() throws IOException {
        CitiesMigrate migrator = createMigrator();

        String command = migrator.createSqlString("1", "Test", 1.2, 2.1);

        assertThat(command, equalTo(
                "INSERT INTO Cities (id, name, location) VALUES (1, 'Test', GeomFromText(CONCAT('POINT (', 2.1, ' ', 1.2, ')')));"));
    }

    @Test
    public void shouldHandleQuoutes() throws IOException {
        CitiesMigrate migrator = createMigrator();

        String command = migrator.createSqlString("1", "Test2'2123", 1.2, 2.1);

        assertThat(command, equalTo(
                "INSERT INTO Cities (id, name, location) VALUES (1, 'Test2\\'2123', GeomFromText(CONCAT('POINT (', 2.1, ' ', 1.2, ')')));"));
    }

    @Test
    public void givenAStream_shouldCreateMultipleCommands() throws IOException {
        String dataset = "1132495,Nahrin,36.0649,69.13343\n1133453,Maymana,35.92139,64.78361";

        CitiesMigrate migrator = createMigrator();

        byte[] bytes = dataset.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        String commands = migrator.createMigration(new InputStreamReader(bais));

        assertThat(commands, equalTo(
                "INSERT INTO Cities (id, name, location) VALUES (1132495, 'Nahrin', GeomFromText(CONCAT('POINT (', 69.13343, ' ', 36.0649, ')')));\nINSERT INTO Cities (id, name, location) VALUES (1133453, 'Maymana', GeomFromText(CONCAT('POINT (', 64.78361, ' ', 35.92139, ')')));\n"));
    }

    private static CitiesMigrate createMigrator() {
        return new CitiesMigrate("jdbc:mysql://localhost:3306/testprojekt3", "root", "pwd");
    }
}