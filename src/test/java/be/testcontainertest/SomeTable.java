package be.testcontainertest;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("SOME_TABLE")
public record SomeTable(
        @Id @Column("ID")
        Long id,
        @Column("CONTENT")
        byte[] content
) {
}
