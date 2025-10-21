package test;

import database.core.Sequence;
import database.core.Affectation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenericDAOTest {

    @Test
    void testGetTableNameForSequence() {
        Sequence sequence = new Sequence();
        assertEquals("sequence", sequence.getTableName(), "Le nom de la table pour Sequence est incorrect.");
    }
    
    @Test
    void testGetTableNameForAffectation() {
        Affectation affectation = new Affectation();
        assertEquals("affectation", affectation.getTableName(), "Le nom de la table pour Affectation est incorrect.");
    }
}