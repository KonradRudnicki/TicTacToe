package com.KonradRudnicki.TicTacToe;

import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@TypeDef(
        name = "field_status_array",
        typeClass = EnumArrayType.class
)

public class Board {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Type(
            type = "field_status_array",
            parameters = @org.hibernate.annotations.Parameter(
                    name = "sql_array_type",
                    value = "field_status"
            )
    )
    @Column(
            name = "field_grid",
            columnDefinition = "field_status[][]"
    )

    private FieldEnum[][] fieldGrid;

    private FieldEnum fieldChar = FieldEnum.X;

    public FieldEnum getFieldChar() {
        return fieldChar;
    }

    public void setFieldChar(FieldEnum fieldChar) {
        this.fieldChar = fieldChar;
    }

    public Board setFieldGrid(FieldEnum[][] fieldGrid) {
        this.fieldGrid = fieldGrid;

        return this;
    }

    public FieldEnum[][] getFieldGrid() {
        return fieldGrid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}