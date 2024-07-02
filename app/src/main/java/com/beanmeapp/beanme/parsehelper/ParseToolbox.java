package com.beanmeapp.beanme.parsehelper;

import com.beanmeapp.beanme.databaseclasses.Drink;
import com.beanmeapp.beanme.databaseclasses.DrinkAttributes;
import com.beanmeapp.beanme.databaseclasses.Run;
import com.beanmeapp.beanme.databaseclasses.Store;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bolts.Task;

/**
 * Helper class with function to do interactions with the parse database.
 */
public class ParseToolbox {

    // variables  and limitations for group names
    public static final String GROUP_NAME_DELMITER = "-_-_";
    public static final int GROUP_NAME_MAX_LENGTH = 30;
    private static final String TAG = "ParseToolbox";
    public static final String SEND_DRINK = "send_drink";
    public static final String NOTIFY_GROUP = "notify_group";
    public static final String GROUP_INVITE = "group_invite";
    public static final String PURPOSE = "purpose";

    /**
     * Retrieves all of the blends from the database;
     * @return DrinkAttributes.Blend, all of the blends.
     */
    public static List<DrinkAttributes.Blend> getBlends() {
        return new ArrayList<>(Arrays.asList(DrinkAttributes.Blend.values()));
    }

    /**
     * Retrieves all of the drinks from the database;
     * @return Drink, all of the drinks.
     */
    public static List<Drink> getDrinks(ParseObject store) {
        //Get relation based on tables you are looking at.
        ParseRelation drinkRelation = store.getRelation("drinks");
        ParseQuery query = drinkRelation.getQuery();

        //Includes all of its pointers
        query.include("blend");
        query.include("flavor");
        query.include("temperature");
        query.include("milk");
        query.include("size");

        try {
            List<ParseObject> drinkParseObjects = query.find();
            ArrayList<Drink> drinks = new ArrayList<>();
            for (ParseObject d : drinkParseObjects) {
                Drink drink = new Drink();
                //TODO: changing name to a enum class borked this, placeholder for now
                //drink.setName(d.getString("name"));
                drink.setName(DrinkAttributes.Name.COFFEE);
                drink.setBlend(DrinkAttributes.Blend
                        .valueOf(d.getParseObject("blend").getString("blend").toUpperCase()));
                drink.setFlavor(DrinkAttributes.Flavor
                        .valueOf(d.getParseObject("flavor").getString("flavor").toUpperCase()));
                drink.setMilk(DrinkAttributes.Milk
                        .valueOf(d.getParseObject("milk").getString("type").toUpperCase()));
                drink.setNumShots(d.getInt("numberOfShots"));
                drink.setSize(DrinkAttributes.Size
                        .valueOf(d.getParseObject("size").getString("size").toUpperCase()));
                drink.setTemperature(DrinkAttributes.Temperature.valueOf(d.getParseObject("temperature").getString("temperature").toUpperCase()));
                List addons = d.getList("addons");
                /*for (int i = 0; i < addons.size(); ++i) {
                    drink.addAddon(DrinkAttributes.Addon.valueOf(((String)addons.get(i)).toUpperCase()));
                }*/

                drinks.add(drink);
            }

            return drinks;
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Returns al of the stores from the database;
     * @return List<Store>, all of the stores.
     */
    public static List<Store> getStores() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Store");

        try {
            List<ParseObject> parseObjects = query.find();
            ArrayList<Store> stores = new ArrayList<>();

            for (ParseObject parseObject : parseObjects) {
                Store store = new Store();
                store.setName(parseObject.getString("name"));
                store.setDrinks(getDrinks(parseObject));
                stores.add(store);
            }
            return stores;
        } catch (com.parse.ParseException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all of the Milk options from the database;
     * @return DrinkAttributes.Milk, all of the milk options.
     */
    public static List<DrinkAttributes.Milk> getMilks() {
        return new ArrayList<>(Arrays.asList(DrinkAttributes.Milk.values()));
    }

    /**
     * Get a list of all active runs from the database
     * @return List<Run>, a list of all of the runs.
     */
    public static List<Run> getRuns() {
        //TODO
        return null;
    }

    /**
     * Retrieves all of the sizes from the database;
     * @return DrinkAttributes.Size, all of the sizes.
     */
    public static List<DrinkAttributes.Size> getSizes() {
        return new ArrayList<>(Arrays.asList(DrinkAttributes.Size.values()));
    }

    /**
     * Retrieves all of the flavors from the database;
     * @return DrinkAttributes.Flavor, all of the flavors.
     */
    public static List<DrinkAttributes.Flavor> getFlavors() {
        return new ArrayList<>(Arrays.asList(DrinkAttributes.Flavor.values()));
    }

    /**
     * Retrieves all of the Addons from the database;
     * @return DrinkAttributes.Addon, all of the Addons.
     */
    public static List<DrinkAttributes.Addon> getAddons() {
        return new ArrayList<>(Arrays.asList(DrinkAttributes.Addon.values()));
    }

    /**
     * Retrieves all of the temperatures from the database;
     * @return DrinkAttributes.Temperature, all of the temperatures.
     */
    public static List<DrinkAttributes.Temperature> getTemperatures() {
        return new ArrayList<>(Arrays.asList(DrinkAttributes.Temperature.values()));
    }

    /**
     * Creates a group with the name.
     * @param groupName String, the name of the group to create.
     * @return String, name of the group created, null if couldn't create.
     */
    public static String createGroup(String groupName) {
        ParseObject group = new ParseObject("Group");

        boolean hasGroupNameError = false;

        // replace all whitespaces with underscores
        groupName = groupName.replaceAll(" ", "_");
        group.put("name", groupName);

        // Limited to 30* characters in length.
        if (groupName.length() > GROUP_NAME_MAX_LENGTH) {
            hasGroupNameError = true;
        }

        // First character must be a letter.
        if (!Character.isLetter(groupName.charAt(0))) {
            hasGroupNameError = true;
        }

        // Must contain only letters, numbers, _ , and - .
        for (int i = 1; i < groupName.length(); i++) {
            char ch = groupName.charAt(i);

            // One of these conditions must be satisfied, else error
            if (!(Character.isLetter(ch) || Character.isDigit(ch) || ch == '-' || ch == '_')) {
                hasGroupNameError = true;
                break;
            }
        }

        if (hasGroupNameError) return null;

        try {
            group.save();
            return group.getObjectId() + GROUP_NAME_DELMITER + groupName;
        } catch (ParseException e) {
            Log.d(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Subscribes a user to a group.
     * @param groupId String, the id of the group.
     * @param groupName String, the name of the group.
     * @return boolean, if they were able to subscribe.
     */
    public static boolean subscribeToGroup(String groupId, String groupName) {
        Task result = ParsePush.subscribeInBackground(groupId + GROUP_NAME_DELMITER + groupName);

        while (!result.isCompleted());

        if (result.isFaulted()) {
            Log.d(TAG, result.getError().getMessage());
            return false;
        } else if (result.isCancelled()) {
            Log.d(TAG, "Subscribing to group cancelled.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Unsubscribes a user to a group.
     * @param groupId String, the id of the group.
     * @param groupName String, the name of the group.
     * @return boolean, if they were able to unsubscribe.
     */
    public static boolean unsubscribeToGroup(String groupId, String groupName) {
        Task result = ParsePush.unsubscribeInBackground(groupId + GROUP_NAME_DELMITER + groupName);

        while (!result.isCompleted());

        if (result.isFaulted()) {
            Log.d(TAG, result.getError().getMessage());
            return false;
        } else if (result.isCancelled()) {
            Log.d(TAG, "Subscribing to group cancelled.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Creates a new run and hosts it.
     * @param user ParseUser, the user to host the run.
     * @param groupName String, the name of the group that is receiving the run.
     * @return boolean, if they were able to hose the run.
     */
    public static boolean hostRun(ParseUser user, String groupName) {
        //TODO
        return false;
    }

    /**
     * Closes the current run that the user was on.
     * @param user ParseUser, the user that hosted the run.
     * @return boolean, if the run was able to be closed.
     */
    public static boolean closeRun(ParseUser user) {
        //TODO
        return false;
    }

    /**
     * Returns a specific store instance from the database.
     * @param storeName String, the name of the store.
     * @return Store, the store object with the specified name.
     */
    public static Store getStore(String storeName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Store");
        query.whereEqualTo("name", storeName);

        try {
            List<ParseObject> parseObjects = query.find();
            ArrayList<Store> stores = new ArrayList<>();

            for (ParseObject parseObject : parseObjects) {
                Store store = new Store();
                store.setName(parseObject.getString("name"));
                store.setDrinks(getDrinks(parseObject));
                stores.add(store);
            }
            return stores.get(0);
        } catch (com.parse.ParseException e) {
            Log.e("Error fetching stores", e.getMessage());
            return null;
        }
    }

    /**
     * Gets a list of the group names that the current parse user belongs to.
     * @return List<String>, the list of all of the group names.
     */
    public static List<String> getGroups() {
        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        List<String> groups = new ArrayList<>();

        try {
            List<ParseObject> parseObjects = query.find();


            for (ParseObject parseObject : parseObjects) {
                if (((ArrayList) parseObject.get("users")).contains(user.getUsername())) {
                    groups.add(parseObject.getObjectId() + GROUP_NAME_DELMITER + parseObject.getString("name"));
                }
            }
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }
        return groups;
    }

    /**
     * Removes the current user from the group.
     * @param groupId String, the id of the group.
     */
    public static void removeCurrentUserFromGroup(String groupId) {
        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.whereEqualTo("objectId", groupId);
        List<String> groups = new ArrayList<>();

        try {
            List<ParseObject> parseObjects = query.find();
            for (ParseObject parseObject : parseObjects) {
                if (((ArrayList) parseObject.get("users")).contains(user.getUsername())) {
                    ((ArrayList) parseObject.get("users")).remove(user.getUsername());
                    parseObject.save();
                }
            }
        } catch (ParseException e) {
            //YOLO
        }

    }

    public static void addGroupToInstallation(String groupId, List<String> users, String groupName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.include("installation");
        System.err.println("users " + users.toString());

        try {
            List<ParseObject> parseObjects = query.find();
            for (ParseObject parseObject : parseObjects) {
                if (!users.contains(parseObject.get("username"))) {
                    Log.e("User not contained", parseObject.get("username").toString());
                    continue;
                }
                Log.e("User is contained", parseObject.get("username").toString());
                if (parseObject.getParseObject("installation") == null) {
                    Log.e("There is a null", "there is a null");
                    continue;
                }
                if (parseObject.get("username").equals(ParseUser.getCurrentUser().getUsername())) {
                    System.err.println("installation channels before : " + parseObject
                            .getParseObject("installation").get("channels").toString());
                    ((ArrayList) parseObject.getParseObject("installation").get("channels"))
                            .add(groupId + GROUP_NAME_DELMITER + groupName);
                    System.err.println("installation channels after : " + parseObject
                            .getParseObject("installation").get("channels").toString());
                    parseObject.saveInBackground();
                } else {
                    ParsePush push = new ParsePush();
                    String group = groupId + GROUP_NAME_DELMITER + groupName;
                    String message = ParseUser.getCurrentUser().getUsername() + " is adding you to group " + groupName + " click to accept.";
                    push.setChannel(parseObject.getParseObject("installation").getObjectId());

                    Log.e("GROUP NAME", group);

                    JSONObject data = new JSONObject();
                    try {
                        data.put("group", group);
                        data.put("hostid", ParseInstallation.getCurrentInstallation().getObjectId());
                        data.put("message", message);
                        data.put("groupId", groupId);
                        data.put("groupName", groupName);
                        data.put(PURPOSE, GROUP_INVITE);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }

                    push.setData(data);
                    push.sendInBackground();
                }
            }
        } catch (ParseException e) {
            //YOLO
        }

    }

    /**
     * Adds the list of users to the group
     * @param groupId String, the group id
     * @param users List<String>, list of usernames
     */
    public static void addUsersToGroup(String groupId, List<String> users, String groupName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.whereEqualTo("objectId", groupId);

        try {
            List<ParseObject> parseObjects = query.find();
            for (ParseObject parseObject : parseObjects) {
                parseObject.addAll("users", users);
                parseObject.save();
            }
            addGroupToInstallation(groupId, users, groupName);
        } catch (ParseException e) {
            //YOLO
        }

    }

    /**
     * Notifies the group (channel) with the following name and id.
     * @param groupId String, the id of the group to notify.
     * @param groupName String, the name of the group to send it to.
     */
    public static void notifyGroup(String groupId, String groupName) {
        ParsePush push = new ParsePush();
        String group = groupId + GROUP_NAME_DELMITER + groupName;
        String message = ParseUser.getCurrentUser().getUsername() + " is going on a coffee run. Touch to add your order.";
        push.setChannel(group);

        Log.e("GROUP NAME", group);

        JSONObject data = new JSONObject();
        try {
            data.put("group", group);
            data.put("hostid", ParseInstallation.getCurrentInstallation().getObjectId());
            data.put("message", message);
            data.put(PURPOSE, NOTIFY_GROUP);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        push.setData(data);
        push.sendInBackground();
    }

    /**
     * Sends the drink item to the host.
     * @param drink Drink, the drink to send.
     * @param hostid String, The id of the host to send the drink.
     */
    public static void sendDrink(Drink drink, String hostid) {
        ParsePush push = new ParsePush();
        push.setChannel(hostid);

        JSONObject data = new JSONObject();
        try {
            data.put("drink", Drink.serialize(drink));
            data.put("name", ParseUser.getCurrentUser().getUsername());
            data.put(PURPOSE, SEND_DRINK);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        Log.e("sending to", ParseInstallation.getCurrentInstallation().getObjectId());
        push.setData(data);
        push.sendInBackground();
    }

    /**
     * Gets the drink from the database with the specific name.
     * @param drinkName DrinkAttributes.Name, the name of the drink.
     * @return Drink, an inflated drink object.
     */
    public static Drink getDrink(DrinkAttributes.Name drinkName) {
        Drink drink = new Drink();
        String name = drinkName.toString().toLowerCase();
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Drink");
        query.whereEqualTo("name", name);

        //Includes all of its pointers
        query.include("blend");
        query.include("flavor");
        query.include("temperature");
        query.include("milk");
        query.include("size");

        try {
            List<ParseObject> drinkParseObjects = query.find();
            for (ParseObject d : drinkParseObjects) {
                drink.setName(drinkName);
                drink.setBlend(DrinkAttributes.Blend
                        .valueOf(d.getParseObject("blend").getString("blend").toUpperCase()));
                drink.setFlavor(DrinkAttributes.Flavor
                        .valueOf(d.getParseObject("flavor").getString("flavor").toUpperCase()));
                drink.setMilk(DrinkAttributes.Milk
                        .valueOf(d.getParseObject("milk").getString("type").toUpperCase()));
                drink.setNumShots(d.getInt("numberOfShots"));
                drink.setSize(DrinkAttributes.Size
                        .valueOf(d.getParseObject("size").getString("size").toUpperCase()));
                drink.setTemperature(DrinkAttributes.Temperature.valueOf(d.getParseObject("temperature").getString("temperature").toUpperCase()));
                List addons = d.getList("addons");
                /*for (int i = 0; i < addons.size(); ++i) {
                    drink.addAddon(DrinkAttributes.Addon.valueOf(((String)addons.get(i)).toUpperCase()));
                }*/
            }
            return drink;
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
            return drink;
        }
    }

    /**
     * Gets all of the users in parse.
     * @return List<String>, list of all of the usernames
     */
    public static List<String> getUsers() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereNotEqualTo("name", ParseUser.getCurrentUser().getUsername());

        try {
            List<ParseObject> parseObjects = query.find();
            ArrayList<String> users = new ArrayList<>();
            Log.e("parseObjects", parseObjects.toString());
            for (ParseObject parseObject : parseObjects) {
                users.add(parseObject.get("username").toString());
            }
            return users;
        } catch (com.parse.ParseException e) {
            Log.e("Error getting users", e.getMessage());
            return null;
        }
    }

    public static void addInstallationToCurrentUser() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        try {
            List<ParseObject> parseObjects = query.find();
            Log.e("the size is ", "" + parseObjects.size());
            for (ParseObject parseObject : parseObjects) {
                parseObject.put("installation", ParseObject.createWithoutData("_Installation",
                        ParseInstallation.getCurrentInstallation().getObjectId()));
                Log.e("the of installation", parseObject.get("installation").toString());
                parseObject.save();
            }
        } catch (ParseException e) {
            //YOLO
        }
    }

    public static void subscribeToInstallation() {
        ParsePush.subscribeInBackground(ParseInstallation.getCurrentInstallation().getObjectId());
    }
}
