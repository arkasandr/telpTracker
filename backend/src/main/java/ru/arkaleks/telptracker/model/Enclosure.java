package ru.arkaleks.telptracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Alex Arkashev (arkasandr@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ENCLOSURES")
public class Enclosure {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENCLOSURE_ID")
    private long enclosureId;

    @Lob
    private byte[] unit;

    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID")
    private TaskMessage message;
}
