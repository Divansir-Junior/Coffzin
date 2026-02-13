package com.coffzin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity
@Table(name = "messages")
public class Message {

    @Column(name = "content", nullable = false)
    private String content;

}
