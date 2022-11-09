package activities;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Activity2 {

    @Test
    public void notEnoughFunds(){
        // Create an object for BankAccount class
        BankAccount account = new BankAccount(10);

        // Assertion for exception
        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(11),"Balance must be greater than amount of withdrawal");

    }



    @Test
    public void enoughFunds(){

        // Create an object for BankAccount class
        BankAccount account = new BankAccount(100);

        // Assertion for no exceptions
        assertDoesNotThrow(() -> account.withdraw(100));


    }
}
