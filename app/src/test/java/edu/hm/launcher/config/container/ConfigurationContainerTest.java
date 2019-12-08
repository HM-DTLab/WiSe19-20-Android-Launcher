package edu.hm.launcher.config.container;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationContainerTest {

    @Test
    public void addingOneLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));

        assertEquals(1, container.getApps().size());
    }

    @Test
    public void addingOneElement() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));

        assertEquals("test1", container.getApps().get(0).getAppName());
    }

    @Test
    public void removingOneLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.removeAt(0);

        assertEquals(0, container.getApps().size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removingOneException() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.removeAt(0);
    }

    @Test
    public void changeOneLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.changeAt(0, new AppContainer("test2"));

        assertEquals(1, container.getApps().size());
    }

    @Test
    public void changeOneElement() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.changeAt(0, new AppContainer("test2"));

        assertEquals("test2", container.getApps().get(0).getAppName());
    }

    @Test
    public void addingMultiLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.add(new AppContainer("test2"));
        container.add(new AppContainer("test3"));

        assertEquals(3, container.getApps().size());
    }

    @Test
    public void addingMultiElement() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.add(new AppContainer("test2"));
        container.add(new AppContainer("test3"));

        assertEquals("test1", container.getApps().get(0).getAppName());
        assertEquals("test2", container.getApps().get(1).getAppName());
        assertEquals("test3", container.getApps().get(2).getAppName());
    }

    @Test
    public void changeMultiLength() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.add(new AppContainer("test2"));
        container.add(new AppContainer("test3"));
        container.changeAt(0, new AppContainer("test2"));

        assertEquals(3, container.getApps().size());
    }

    @Test
    public void changeMultiElement() {
        ConfigurationContainer container = new ConfigurationContainer();

        container.add(new AppContainer("test1"));
        container.add(new AppContainer("test2"));
        container.add(new AppContainer("test3"));
        container.changeAt(0, new AppContainer("test3"));
        container.changeAt(1, new AppContainer("test1"));
        container.changeAt(2, new AppContainer("test2"));

        assertEquals("test1", container.getApps().get(1).getAppName());
        assertEquals("test2", container.getApps().get(2).getAppName());
        assertEquals("test3", container.getApps().get(0).getAppName());
    }
}