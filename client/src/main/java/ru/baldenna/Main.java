package ru.baldenna;

import feign.Feign;
import feign.codec.StringDecoder;
import feign.jackson.JacksonEncoder;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static ExecutorService threadPool = Executors.newVirtualThreadPerTaskExecutor();

    static String appServerUrl = System.getenv("APP_SERVER_URL") == null ? "http://localhost:8080": System.getenv("APP_SERVER_URL");

    static AppClient appClient = Feign.builder()
            .encoder(new JacksonEncoder())
            .decoder(new StringDecoder())
            .target(AppClient.class, appServerUrl);

    public static void main(String[] args) throws InterruptedException {
        String usersCountEnv = System.getenv("USERS_COUNT");
        // by default 5 random and 5 fixed users.
        int randomUsersCount = usersCountEnv == null ? 5 : Math.max(Integer.parseInt(usersCountEnv) - 5, 5);

        while (true) {

            var users = generateUsers(randomUsersCount);
            users.forEach(Main::checkIfElseBasedFeatureEnabled);
            Thread.sleep(500);
            users.forEach(Main::checkBeansBasedFeatureEnabled);
            Thread.sleep(500);
            users.forEach(Main::checkVariantBasedFeatureEnabled);
            System.out.println("Features called by " + users.size() + " random users");
        }

    }

    private static ArrayList<User> generateUsers(int randomUsersCount) {
        var users = new ArrayList<User>();

        // fixed usernames for blacklist/whitelist strategies
        users.add(new User("user1", UserType.FL, generateRandomAge()));
        users.add(new User("user2", UserType.FL, generateRandomAge()));
        users.add(new User("user3", UserType.UL, generateRandomAge()));
        users.add(new User("user4", UserType.UL, generateRandomAge()));
        users.add(new User("user5", UserType.IP, generateRandomAge()));

        for (int i = 0; i < randomUsersCount; i++) {
            String randomString = generateRandomString(10);
            users.add(new User(randomString, UserType.NOT_CLIENT, generateRandomAge()));
        }
        return users;
    }

    private static void checkIfElseBasedFeatureEnabled(User user) {
        scheduleTask(() -> appClient.checkIfElseBasedFeatureEnabled(user.usermame(), user.type().name(), user.age()));
    }

    private static void checkBeansBasedFeatureEnabled(User user) {
        scheduleTask(() -> appClient.checkBeansBasedFeatureEnabled(user.usermame(), user.type().name()));
    }

    private static void checkVariantBasedFeatureEnabled(User user) {
        scheduleTask(() -> appClient.checkVariantBasedFeatureEnabled(user.usermame(), user.type().name()));
    }

    private static void scheduleTask(Runnable task) {
        threadPool.submit(() -> {
            try {
                task.run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private static String generateRandomString(int stringLength) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stringLength; i++) {
            int index = (int) (Math.random() * characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

        private static int generateRandomAge() {
            return (int) (Math.random() * (100 - 20 + 1)) + 20;
        }

}