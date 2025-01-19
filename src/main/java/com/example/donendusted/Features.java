package com.example.donendusted;

/**
 * Interface defines the methods those are common among all features
 */
public interface Features
{

    public void initialize();

    public void updateTheme(boolean isLightMode);

    public void loadUserData();

    public void startClock();

    public void saveTasksToFile(String username);

    public void loadTasksFromFile(String username);
    @Override
    String toString();
}
