package com.metropolitan.prviprimer.baza

class StudentInfo(
    var id: Int = 0,
    var ime: String = "Ne postoji",
    var fakultet: String = "Ne postoji",
    var smer: String = "Ne postoji"
) {

    override fun toString(): String {
        return "StudentInfo(id=$id, ime='$ime', fakultet='$fakultet', smer='$smer')"
    }

    fun uzmiIme(): String {
        return "Ime tog studenta je: $ime"
    }

    fun uzmiFakultet(): String {
        return "Fakultet tog studenta: je $fakultet"
    }

    fun uzmiSmer(): String {
        return "Smer tog studenta je: $smer"
    }
}